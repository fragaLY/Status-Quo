package sq.vk.client.dao;

import java.util.List;

import sq.vk.client.domain.Client;

/**
 * Created by Vadzim Kavalkou on 22.03.2017.
 */
public interface ClientDao {

    List<Client> getAllClients();

    Client getClientByEmail(String email);

    Client getClientById(Integer id);

    Client saveClient(Client client);

    Client deleteClient(Client client);

    Integer deleteClient(Integer id);

    String deleteClient(String email);
}
