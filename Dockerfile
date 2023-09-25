FROM openjdk:17-jdk-slim-buster AS builder

RUN apt-get update && apt-get install -y binutils zlib1g

WORKDIR /AppServer

COPY target/pitang-backend.jar /AppServer/pitang-backend.jar

RUN jlink \
    --module-path "$JAVA_HOME/jmods" \
    --add-modules java.compiler,java.sql,java.naming,java.management,java.instrument,java.rmi,java.desktop,jdk.internal.vm.compiler.management,java.xml.crypto,java.scripting,java.security.jgss,jdk.httpserver,java.net.http,jdk.naming.dns,jdk.crypto.cryptoki,jdk.unsupported \
    --verbose \
    --strip-debug \
    --compress 2 \
    --no-header-files \
    --no-man-pages \
    --output /opt/jre-minimal

FROM gcr.io/distroless/base-debian10

WORKDIR /AppServer

COPY --from=builder /opt/jre-minimal /opt/jre-minimal
COPY --from=builder /AppServer /AppServer
COPY --from=builder /lib/x86_64-linux-gnu/libz.so.1 /lib/x86_64-linux-gnu/libz.so.1

ENV JAVA_HOME=/opt/jre-minimal
ENV PATH="$PATH:$JAVA_HOME/bin"

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/AppServer/pitang-backend.jar"]