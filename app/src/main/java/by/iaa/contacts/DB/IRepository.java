package by.iaa.contacts.DB;

import java.util.List;

public interface IRepository<T> {
    void close();
    List<T> getAll();
    long getCount();
    T get(long id);
    long insert(T item);
    long update(T item);
    long remove(long id);
    void open();

    List<T> getAllByType(int type);
    List<T> getAllByName(String search);
}
