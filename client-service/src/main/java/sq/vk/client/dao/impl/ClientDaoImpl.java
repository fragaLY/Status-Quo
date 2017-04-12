package sq.vk.client.dao.impl;

import java.util.List;

import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import sq.vk.client.dao.AbstractDao;
import sq.vk.client.dao.ClientDao;
import sq.vk.client.domain.Client;
import sq.vk.client.exceptions.ClientNotFoundException;

/**
 * Created by Vadzim Kavalkou on 22.03.2017.
 */
@Repository("ClientRepository")
public class ClientDaoImpl extends AbstractDao implements ClientDao {

  private static final Logger LOG = LoggerFactory.getLogger(ClientDaoImpl.class);

  @Override
  public List<Client> getAllClients() {

    LOG.info("Get all clients.");
    final List<Client> clients = getSession().createQuery("FROM Client").list();

    if (clients.isEmpty()) {
      throw new ClientNotFoundException("Clients were not found");
    }

    return clients;

  }

  @Override
  public Client getClientByEmail(final String email) {

    LOG.info("Get client by email = [{}].", email);

    Query query = getSession().createQuery("FROM Client c WHERE c.email=:email").setParameter("email", email);

    final Client client = (Client)query.uniqueResult();

    if (client == null) {
      throw new ClientNotFoundException("Client with email '" + email + "' was not found.");
    }

    return client;

  }

  @Override
  public Client getClientById(final Integer id) {

    LOG.info("Get client by id = [ {} ].", id);

    final Client client = getSession().get(Client.class, id);

    if (client == null) {
      throw new ClientNotFoundException("Client with id='" + id + "' was not found.");
    }

    return client;

  }

  @Override
  public Client saveClient(Client client) {

    LOG.info("Save client = [ {} ].", client);
    //TODO VK : implement save logic
    return null;
  }

}
