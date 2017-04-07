package sq.vk.service.item.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sq.vk.converter.client.ItemConverter;
import sq.vk.dao.item.ItemDao;
import sq.vk.dto.item.ItemDto;
import sq.vk.service.item.ItemService;
import sq.vk.service.util.LamodaItemHelper;

/**
 * Created by Vadzim_Kavalkou on 4/7/2017.
 */
@Service("ItemService")
public class ItemServiceImpl implements ItemService {

  private static final Logger LOG = LoggerFactory.getLogger(ItemServiceImpl.class);

  private static final String INFO_SELECTOR = "body > div.page > div:nth-child(4) > div > div > div.ii-product__aside > div > div.ii-product__buy.js-widget-buy > div > div.product__cart-add > script";
  private static final String NAME_SELECTOR = "body > div.page > div:nth-child(4) > div > div > div.ii-product__aside > div > h1";
  private static final String PRICE_SELECTOR = "body > div.page > div:nth-child(4) > div > div > div.ii-product__aside > div > div.ii-product__buy.js-widget-buy > div > div.ii-product__price > div.ii-product__price-current";

  @Autowired
  private ItemDao itemDao;

  @Autowired
  private ItemConverter itemConverter;

  @Override
  public boolean saveItem(ItemDto itemDto) {

    //TODO VK: implements logic
    return false;
  }

  @Override
  @Transactional(readOnly = true)
  public List<ItemDto> getAllItems() {

    LOG.info("Gets all clients.");

    return itemDao.getAllItems().stream().parallel().map(itemConverter).collect(Collectors.toList());
  }

  @Override
  @Transactional(readOnly = true)
  public ItemDto getItemById(String id) {

    LOG.info("Gets item by id = [{}].", id);

    return itemConverter.apply(itemDao.getItemById(id));
  }

  @Override
  @Transactional(readOnly = true)
  public ItemDto getItemByName(String name) {

    LOG.info("Gets item by name = [{}].", name);

    return itemConverter.apply(itemDao.getItemByName(name));
  }

  @Override
  @Transactional(readOnly = true)
  public ItemDto getItemByItemId(String id) throws IOException {
    List<Elements> elements = new ArrayList<>();

    String url = "http://www.lamoda.by/p/" + id;

    LOG.debug("The item with id = {} is located by URL = {}", id, url);

    Document doc = Jsoup.connect(url).get();

    Elements itemInfo = doc.select(INFO_SELECTOR);
    Elements itemName = doc.select(NAME_SELECTOR);
    Elements itemPrice = doc.select(PRICE_SELECTOR);

    Map<String, Elements> elementsMap = new HashMap<>();

    elementsMap.put("info", itemInfo);
    elementsMap.put("name", itemName);
    elementsMap.put("price", itemPrice);

    ItemDto itemDto = new LamodaItemHelper().apply(elementsMap);
    itemDto.setId(id);

    return itemDto;
  }

}
