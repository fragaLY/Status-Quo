package sq.vk.service.client;

import java.util.List;

import sq.vk.dto.client.ClientDto;

/**
 * Created by Vadzim Kavalkou on 22.03.2017.
 */
public interface ClientService {
    List<ClientDto> getAllClients();
    ClientDto getClientByEmail(String email);
    ClientDto getClientById(Integer id);
    boolean saveClient(ClientDto client);
}
