package store;

import model.Item;

import java.util.Collection;

public interface Store {
    Item create(Item element);

    void update(int id, Item element);

    Collection<Item> findAll();

    Item findById(int id);

}
