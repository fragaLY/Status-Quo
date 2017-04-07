package sq.vk.service.client.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sq.vk.converter.client.ClientConverter;
import sq.vk.dao.client.ClientDao;
import sq.vk.dto.client.ClientDto;
import sq.vk.service.client.ClientService;

/**
 * Created by Vadzim Kavalkou on 22.03.2017.
 */
@Service("ClientService")
public class ClientServiceImpl implements ClientService {

    private static final Logger LOG = LoggerFactory.getLogger(ClientServiceImpl.class);

    @Autowired
    private ClientDao clientDao;

    @Autowired
    private ClientConverter clientConverter;

    @Override
    @Transactional(readOnly = true)
    public List<ClientDto> getAllClients() {

        LOG.info("Starting to get all clients.");

        return clientDao.getAllClients().stream().parallel().map(clientConverter).collect(Collectors.toList());

    }

    @Override
    @Transactional(readOnly = true)
    public ClientDto getClientByEmail(final String email) {

        LOG.info("Starting to get client by email = [{}].", email);

        return clientConverter.apply(clientDao.getClientByEmail(email));

    }

    @Override
    @Transactional(readOnly = true)
    public ClientDto getClientById(final Integer id) {

        LOG.info("Starting to get client by id = [{}].", id);

        return clientConverter.apply(clientDao.getClientById(id));

    }

    @Override public boolean saveClient(ClientDto client) {

        //TODO VK: implement logic
        return false;
    }
}
