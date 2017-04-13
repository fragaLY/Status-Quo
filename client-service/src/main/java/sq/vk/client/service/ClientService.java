package sq.vk.client.service;

import java.util.List;

import sq.vk.client.dto.ClientDto;

/**
 * Created by Vadzim Kavalkou on 22.03.2017.
 */
public interface ClientService {

    List<ClientDto> findAll();

    ClientDto findOneByEmail(String email);

    ClientDto findOne(Integer id);

    ClientDto save(ClientDto clientDto);

    ClientDto delete(ClientDto clientDto);

    Integer delete(Integer id);

    String delete(String email);

}
