package sq.vk.dao.client;

import sq.vk.domain.client.Client;

import java.util.List;

/**
 * Created by Vadzim Kavalkou on 22.03.2017.
 */
public interface ClientDao {

    List<Client> getAllClients();

    Client getClientByEmail(String email);

    Client getClientById(Integer id);

    Client saveClient(Client client);
}
