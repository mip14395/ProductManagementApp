package domain;

import java.util.List;

public interface Service<T> {
    int insert(T t);

    int update(T t);

    int delete(T t);

    List<T> selectAll();
}
