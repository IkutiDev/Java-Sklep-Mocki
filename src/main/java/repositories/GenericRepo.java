package repositories;

import java.util.List;

public interface GenericRepo<T> {
    T getById(int id);
    List<T> getAll();
    boolean add(T entity);
    boolean update(T entity);
    boolean delete(T entity);
}
