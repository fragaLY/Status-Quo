package sq.vk.dao.client.impl;

import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import sq.vk.dao.AbstractDao;
import sq.vk.dao.client.ClientDao;
import sq.vk.domain.client.Client;

import java.util.List;

/**
 * Created by Vadzim Kavalkou on 22.03.2017.
 */
@Repository("ClientRepository")
public class ClientDaoImpl extends AbstractDao implements ClientDao {

    private static final Logger LOG = LoggerFactory.getLogger(ClientDaoImpl.class);

    @Override
    public List<Client> getAllClients() {

        LOG.info("Get all clients.");

        return (List<Client>) getSession().createQuery("FROM Client").list();

    }

    @Override
    public Client getClientByEmail(final String email) {

        LOG.info("Get client by email = [{}].", email);

        Query query = getSession()
                .createQuery("FROM Client c WHERE c.email=:email")
                .setParameter("email", email);

        return (Client) query.uniqueResult();

    }

    @Override
    public Client getClientById(final Integer id) {

        LOG.info("Get client by id = [ {} ].", id);

        return getSession().get(Client.class, id);

    }

}
