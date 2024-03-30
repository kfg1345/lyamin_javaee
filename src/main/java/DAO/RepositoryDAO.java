package DAO;

import domain.Order;

import java.util.List;

public interface RepositoryDAO<T> {
    public Long insert(T о) throws Exception;
    // Редактирование cущности
    public void update(T o);

    Long insert(Order order);

    void update(Order order);

    // Удаление сущности
    public void delete(Long Id);
    // Поиск сущности по Id
    public T findById(Long Id);
    // Получение списка сущностей
    public List<T> findAll();
}
