package eu.senla.myfirstapp.app.repository;


import java.sql.SQLException;

public interface GeneralTransaction<T> {

    void commitSingle(T t) throws SQLException;

    void commitMany(T t) throws SQLException;

    void rollBack(T t);

    void remove();

    void close(T t);

    T getObject();
}
