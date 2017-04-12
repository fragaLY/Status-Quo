package sq.vk.client.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sq.vk.client.converter.ClientConverter;
import sq.vk.client.dao.ClientDao;
import sq.vk.client.dto.ClientDto;
import sq.vk.client.service.ClientService;

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
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public List<ClientDto> getAllClients() {

        LOG.info("Starting to get all clients.");

        return dao.getAllClients().stream().parallel().map(converter).collect(Collectors.toList());

    }

    @Override
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public ClientDto getClientByEmail(final String email) {

        LOG.info("Starting to get client by email = [{}].", email);

        return converter.apply(dao.getClientByEmail(email));

    }

    @Override
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public ClientDto getClientById(final Integer id) {

        LOG.info("Starting to get client by id = [{}].", id);

        return converter.apply(dao.getClientById(id));

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ClientDto saveClient(final ClientDto clientDto) {

        LOG.info("Starting to save clientDto [{}].", clientDto);

        dao.saveClient(converter.transform(clientDto));

        return clientDto;

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ClientDto deleteClient(final ClientDto clientDto) {

        LOG.info("Starting to delete clientDto [{}].", clientDto);

        dao.deleteClient(converter.transform(clientDto));

        return clientDto;

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer deleteClient(final Integer id) {

        LOG.info("Starting to delete client with id [{}].", id);

        dao.deleteClient(id);

        return id;

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String deleteClient(final String email) {

        LOG.info("Starting to delete client with email [{}].", email);

        dao.deleteClient(email);

        return email;

    }

}
