package sq.vk.client.service;

import java.util.List;

import sq.vk.client.dto.ClientDto;

/**
 * Created by Vadzim Kavalkou on 22.03.2017.
 */
public interface ClientService {

    List<ClientDto> getAllClients();

    ClientDto getClientByEmail(String email);

    ClientDto getClientById(Integer id);

    ClientDto saveClient(ClientDto client);

}
