package sq.vk.dao.statistic.impl;

import java.util.List;

import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import sq.vk.dao.AbstractDao;
import sq.vk.dao.statistic.StatisticDao;
import sq.vk.domain.statistic.Statistic;
import sq.vk.exceptions.statistic.StatisticNotFoundException;

/**
 * Created by Vadzim_Kavalkou on 4/7/2017.
 */
@Repository("StatisticRepository")
public class StatisticDaoImpl extends AbstractDao implements StatisticDao {

  private static final Logger LOG = LoggerFactory.getLogger(StatisticDaoImpl.class);

  @Override
  public Statistic saveItem(Statistic statistic) {

    //TODO VK: implement logic
    return null;
  }

  @Override
  public List<Statistic> getAllStatistics() {

    LOG.info("Get all items");
    final List<Statistic> statistics = getSession().createQuery("FROM Item").list();

    if (statistics == null) {
      throw new StatisticNotFoundException("Statistics were not found");
    }

    return statistics;
  }

  @Override
  public Statistic getStatisticById(final Integer id) {

    LOG.info("Get statistic by id = [{}].", id);

    Query query = getSession().createQuery("FROM Item c WHERE c.id=:id").setParameter("id", id);
    final Statistic statistic = (Statistic)query.uniqueResult();

    if (statistic == null) {
      throw new StatisticNotFoundException("Statistic with id = '" + id + "' was not found");
    }

    return statistic;
  }

  @Override
  public Statistic getStatisticByName(final String name) {

    LOG.info("Get statistic by name = [{}].", name);

    Query query = getSession().createQuery("FROM Item c WHERE c.name=:name").setParameter("name", name);
    Statistic statistic = (Statistic)query.uniqueResult();

    if (statistic == null) {
      throw new StatisticNotFoundException("Statistic with name = '" + name + "' was not found");
    }

    return statistic;
  }

}
