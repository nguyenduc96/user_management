package service;

import java.sql.SQLException;
import java.util.List;

public interface IGeneralService<T> {
    List<T> findAll();

    boolean save(T t) throws SQLException;

    boolean update(T t) throws SQLException;

    boolean delete(int id) throws SQLException;

    T findById(int id);
}
