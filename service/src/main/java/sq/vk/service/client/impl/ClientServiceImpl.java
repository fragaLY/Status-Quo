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
    private ClientDao dao;

    @Autowired
    private ClientConverter converter;

    @Override
    @Transactional(readOnly = true)
    public List<ClientDto> getAllClients() {

        LOG.info("Starting to get all clients.");

        return dao.getAllClients().stream().parallel().map(converter).collect(Collectors.toList());

    }

    @Override
    @Transactional(readOnly = true)
    public ClientDto getClientByEmail(final String email) {

        LOG.info("Starting to get client by email = [{}].", email);

        return converter.apply(dao.getClientByEmail(email));

    }

    @Override
    @Transactional(readOnly = true)
    public ClientDto getClientById(final Integer id) {

        LOG.info("Starting to get client by id = [{}].", id);

        return converter.apply(dao.getClientById(id));

    }

    @Override
    public ClientDto saveClient(ClientDto client) {

        //TODO VK: implement logic
        return null;
    }
}
