package sq.vk.dao.item;

import java.util.List;

import sq.vk.domain.item.Item;

/**
 * Created by Vadzim_Kavalkou on 4/7/2017.
 */
public interface ItemDao {
  boolean saveItem(Item item);
  List<Item> getAllItems();
  Item getItemById(String id);
  Item getItemByName(String name);
}
