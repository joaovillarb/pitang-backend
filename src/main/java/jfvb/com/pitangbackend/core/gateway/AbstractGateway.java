package jfvb.com.pitangbackend.core.gateway;

import java.util.List;

public interface AbstractGateway<T, R> {

    T getById(R id);

    T save(T entity);

    void delete(R id);

    List<T> findAll();
}
