package sq.vk.service.client;

import sq.vk.dto.client.ClientDto;

import java.util.List;

/**
 * Created by Vadzim Kavalkou on 22.03.2017.
 */
public interface ClientService {

    List<ClientDto> getAllClients();

    ClientDto getClientByEmail(String email);

    ClientDto getClientById(Integer id);

    ClientDto saveClient(ClientDto client);

}
