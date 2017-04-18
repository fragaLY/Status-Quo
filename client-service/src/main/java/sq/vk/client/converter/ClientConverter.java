package sq.vk.client.converter;

import java.util.function.Function;
import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import sq.vk.client.domain.Client;
import sq.vk.client.dto.ClientDto;
import sq.vk.client.exceptions.ClientNotFoundException;

/**
 * Created by Vadzim Kavalkou on 22.03.2017.
 */
@Component("ClientConverter")
public class ClientConverter implements Function<Client, ClientDto> {

    private static final Logger LOG = LoggerFactory.getLogger(ClientConverter.class);

    @Override
    public ClientDto apply(final Client client) throws EntityNotFoundException {

        LOG.info("Converts Client = [{}] into ClientDto.", client);

        if (client == null){

            LOG.info("Client is null.");

            throw new ClientNotFoundException("Client was not found");
        }

        ClientDto clientDto = new ClientDto.Builder(client.getEmail())
                                .setId(client.getId())
                                .setFirstName(client.getFirstName())
                                .setSecondName(client.getSecondName())
                                .setRole(client.getRole())
                                .setPassword(client.getPassword())
                                .setStatistics(client.getStatistics())
                             .build();

        LOG.info("Client was successfully converted into ClientDto = [{}].", clientDto);

        return clientDto;
    }

    public Client transform(final ClientDto clientDto) {


        LOG.info("Converts ClientDto = [{}] into Client.", clientDto);

        Client client = new Client.Builder(clientDto.getEmail())
                            .setId(clientDto.getId())
                            .setFirstName(clientDto.getFirstName())
                            .setSecondName(clientDto.getSecondName())
                            .setRole(clientDto.getRole())
                            .setPassword(clientDto.getPassword())
                            .setStatistics(clientDto.getStatistics())
                        .build();


        LOG.info("ClientDto was successfully converted into Client = [{}].", client);

        return client;

    }

}
