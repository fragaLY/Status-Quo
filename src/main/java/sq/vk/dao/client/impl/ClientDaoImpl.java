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

    @Override
    public List<Client> getAllClients() {
        return (List<Client>) getSession().createQuery("from Client").list();
    }

    @Override
    public Client getClientByEmail(String email) {
        Query query = getSession()
                .createQuery("from Client c where c.email=:email")
                .setParameter("email", email);
        return (Client) query.uniqueResult();
    }
}
