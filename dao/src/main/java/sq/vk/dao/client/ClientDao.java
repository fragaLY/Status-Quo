package sq.vk.dao.client;

import org.springframework.cache.annotation.Cacheable;
import sq.vk.domain.client.Client;

import java.util.List;

/**
 * Created by Vadzim Kavalkou on 22.03.2017.
 */
public interface ClientDao {

    @Cacheable(value = "clientCache")
    List<Client> getAllClients();

    @Cacheable(value = "clientCache")
    Client getClientByEmail(String email);

    @Cacheable(value = "clientCache")
    Client getClientById(Integer id);

}
