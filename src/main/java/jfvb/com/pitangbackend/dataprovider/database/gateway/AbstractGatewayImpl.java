package jfvb.com.pitangbackend.dataprovider.database.gateway;

import jfvb.com.pitangbackend.core.exception.NotFoundException;
import jfvb.com.pitangbackend.core.gateway.AbstractGateway;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public abstract class AbstractGatewayImpl<T, R, E extends JpaRepository<T, R>>
        implements AbstractGateway<T, R> {

    protected final E repository;
    private final Class<T> entityClass;

    protected AbstractGatewayImpl(E repository,
                                  Class<T> entityClass) {
        this.repository = repository;
        this.entityClass = entityClass;
    }

    public T getById(R id) {
        return this.repository.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        String.format("%s not found with id=%s", entityClass.getSimpleName(), id), 404)
                );
    }

    public T save(T entity) {
        return this.repository.saveAndFlush(entity);
    }

    public void delete(R id) {
        this.repository.deleteById(id);
        this.repository.flush();
    }

    public List<T> findAll() {
        return this.repository.findAll();
    }

}
