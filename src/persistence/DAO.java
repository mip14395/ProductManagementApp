package persistence;

import java.util.List;

public interface DAO<T> {

    int insert(T t);

    int update(T t);

    int delete(T t);

    List<T> selectAll();
}
