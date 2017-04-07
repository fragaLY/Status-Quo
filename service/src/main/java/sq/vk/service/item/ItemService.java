package sq.vk.service.item;

import java.io.IOException;
import java.util.List;

import sq.vk.dto.item.ItemDto;

/**
 * Created by Vadzim_Kavalkou on 4/7/2017.
 */
public interface ItemService {
  boolean saveItem(ItemDto itemDto);
  List<ItemDto> getAllItems();
  ItemDto getItemById(String id);
  ItemDto getItemByName(String name);
  ItemDto getItemByItemId(String url) throws IOException;
}
