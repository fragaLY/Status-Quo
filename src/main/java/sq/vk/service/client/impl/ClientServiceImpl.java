package sq.vk.service.client.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sq.vk.converter.client.ClientConverter;
import sq.vk.dao.client.ClientDao;
import sq.vk.dto.Client.ClientDto;
import sq.vk.service.client.ClientService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Vadzim Kavalkou on 22.03.2017.
 */
@Service("ClientService")
public class ClientServiceImpl implements ClientService {

    @Autowired
    private ClientDao clientDao;

    @Autowired
    private ClientConverter clientConverter;

    @Override
    @Transactional(readOnly = true)
    public List<ClientDto> getAllClients() {
        return clientDao.getAllClients().stream().parallel().map(clientConverter).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ClientDto getClientByEmail(final String email) {
        return clientConverter.apply(clientDao.getClientByEmail(email));
    }

    @Override
    @Transactional(readOnly = true)
    public ClientDto getClientById(final Integer id) {
        return clientConverter.apply(clientDao.getClientById(id));
    }
}
