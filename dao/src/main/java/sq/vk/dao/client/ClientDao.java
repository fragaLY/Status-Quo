package sq.vk.dao.client;

import java.util.List;

import sq.vk.domain.client.Client;

/**
 * Created by Vadzim Kavalkou on 22.03.2017.
 */
public interface ClientDao {

    List<Client> getAllClients();

    Client getClientByEmail(String email);

    Client getClientById(Integer id);

    boolean saveClient(Client client);
}
