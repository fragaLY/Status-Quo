package sq.vk.dao.item.impl;

import java.util.List;

import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import sq.vk.dao.AbstractDao;
import sq.vk.dao.item.ItemDao;
import sq.vk.domain.item.Item;

/**
 * Created by Vadzim_Kavalkou on 4/7/2017.
 */
@Repository("ItemRepository")
public class ItemDaoImpl extends AbstractDao implements ItemDao{

  private static final Logger LOG = LoggerFactory.getLogger(ItemDaoImpl.class);


  @Override public boolean saveItem(Item item) {

    //TODO VK: implement logic
    return false;
  }

  @Override public List<Item> getAllItems() {

    LOG.info("Get all items");

    return (List<Item>) getSession().createQuery("FROM Item").list();
  }

  @Override public Item getItemById(String id) {

    LOG.info("Get item by id = [{}].", id);

    Query query = getSession()
        .createQuery("FROM Item c WHERE c.id=:id")
        .setParameter("id", id);

    return (Item)query.uniqueResult();
  }

  @Override public Item getItemByName(String name) {

    LOG.info("Get item by name = [{}].", name);

    Query query = getSession()
        .createQuery("FROM Item c WHERE c.name=:name")
        .setParameter("name", name);

    return (Item)query.uniqueResult();  }
}
