package sq.vk.dao.statistic.impl;

import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import sq.vk.dao.AbstractDao;
import sq.vk.dao.statistic.StatisticDao;
import sq.vk.domain.statistic.Statistic;

import java.util.List;

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
    public List<Statistic> getAllItems() {

        LOG.info("Get all items");

        return (List<Statistic>) getSession().createQuery("FROM Item").list();
    }

    @Override
    public Statistic getItemById(Integer id) {

        LOG.info("Get statistic by id = [{}].", id);

        Query query = getSession()
                .createQuery("FROM Item c WHERE c.id=:id")
                .setParameter("id", id);

        return (Statistic) query.uniqueResult();
    }

    @Override
    public Statistic getItemByName(String name) {

        LOG.info("Get statistic by name = [{}].", name);

        Query query = getSession()
                .createQuery("FROM Item c WHERE c.name=:name")
                .setParameter("name", name);

        return (Statistic) query.uniqueResult();
    }

}
