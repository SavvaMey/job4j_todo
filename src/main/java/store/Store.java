package store;

import model.Category;
import model.Item;

import java.util.Collection;
import java.util.List;

public interface Store {
    Item create(Item element);

    void update(int id, Item element);

    Collection<Item> findAll();

    Item findById(int id);

    List<Category> findAllCategory();

    Category findByIdCategory(int id);

}
