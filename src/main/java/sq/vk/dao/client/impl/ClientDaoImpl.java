package sq.vk.dao.client.impl;

import org.hibernate.query.Query;
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

    //TODO: add Optional

    @Override
    public List<Client> getAllClients() {
        return (List<Client>) getSession().createQuery("FROM Client").list();
    }

    @Override
    public Client getClientByEmail(final String email) {
        Query query = getSession()
                .createQuery("FROM Client c WHERE c.email=:email")
                .setParameter("email", email);
        return (Client) query.uniqueResult();
    }

    @Override
    public Client getClientById(final Integer id) {
        return getSession().get(Client.class, id);
    }

}
