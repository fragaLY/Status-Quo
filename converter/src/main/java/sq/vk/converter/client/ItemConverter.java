package sq.vk.converter.client;

import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import sq.vk.domain.item.Item;
import sq.vk.dto.item.ItemDto;

/**
 * Created by Vadzim_Kavalkou on 4/7/2017.
 */
@Component("ItemConverter")
public class ItemConverter implements Function<Item, ItemDto> {

  private static final Logger LOG = LoggerFactory.getLogger(ItemConverter.class);

  @Override
  public ItemDto apply(Item item) {

    LOG.info("Converts Item = [{}] into ItemDto.", item);

    ItemDto itemDto = new ItemDto.Builder()
                        .setId(item.getId())
                        .setName(item.getName())
                        .setSize(item.getSize())
                        .setPrice(item.getPrice())
                      .build();

    LOG.info("Item was successfully converted into itemDto = [{}].", itemDto);

    return itemDto;
  }
}
