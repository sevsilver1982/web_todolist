package dao;

import model.Item;

import java.util.List;
import java.util.UUID;

public interface ItemDao {

    Item add(Item item);

    List<Item> findAll();

    Item findById(UUID id);

    Boolean delete(Item item);

}
