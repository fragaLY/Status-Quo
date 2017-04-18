package sq.vk.client.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sq.vk.client.converter.ClientConverter;
import sq.vk.client.dao.ClientDao;
import sq.vk.client.domain.Client;
import sq.vk.client.dto.ClientDto;
import sq.vk.client.exceptions.ClientNotFoundException;
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
    @Transactional(readOnly = true, rollbackFor = Throwable.class)
    public List<ClientDto> findAll() {

        LOG.info("Starting to get all clients.");

        return dao.findAll().stream().parallel().map(converter).collect(Collectors.toList());

    }

    @Override
    @Transactional(readOnly = true, rollbackFor = Throwable.class)
    public ClientDto findOneByEmail(final String email) {

        LOG.info("Starting to get client by email = [{}].", email);

        final Optional<Client> client = dao.findOneByEmail(email);

        if(!client.isPresent()){

            throw new ClientNotFoundException("Client Was Not Found");
        }

        return converter.apply(client.get());

    }

    @Override
    @Transactional(readOnly = true, rollbackFor = Throwable.class)
    public ClientDto findOne(final Integer id) {

        LOG.info("Starting to get client by id = [{}].", id);

        return converter.apply(dao.findOne(id));

    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public ClientDto save(final ClientDto clientDto) {

        LOG.info("Starting to save clientDto [{}].", clientDto);

        dao.save(converter.transform(clientDto));

        return clientDto;

    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public ClientDto delete(final ClientDto clientDto) {

        LOG.info("Starting to delete clientDto [{}].", clientDto);

        dao.delete(converter.transform(clientDto));

        return clientDto;

    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public Integer delete(final Integer id) {

        LOG.info("Starting to delete client with id [{}].", id);

        dao.delete(id);

        return id;

    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public String delete(final String email) {

        LOG.info("Starting to delete client with email [{}].", email);

        dao.deleteByEmail(email);

        return email;

    }

}
