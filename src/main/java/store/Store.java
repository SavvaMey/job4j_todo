package store;

import model.Item;

import java.util.Collection;

public interface Store {
    Item create(Item element);

    boolean update(int id, Item element);

    boolean delete(int id);

    Collection<Item> findAll();

    Item findById(int id);

}
