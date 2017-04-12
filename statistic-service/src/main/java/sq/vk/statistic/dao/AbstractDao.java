package sq.vk.statistic.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Vadzim Kavalkou on 22.03.2017.
 */
public abstract class AbstractDao {

    private final static Logger LOG = LoggerFactory.getLogger(AbstractDao.class);

    @Autowired
    private SessionFactory sessionFactory;

    protected Session getSession() {

        final Session currentSession = sessionFactory.getCurrentSession();

        LOG.info("Get current sessionFactory [ {} ]", currentSession.toString());

        return currentSession;
    }
}