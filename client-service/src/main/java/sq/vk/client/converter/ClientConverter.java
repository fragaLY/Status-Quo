package sq.vk.client.converter;

import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import sq.vk.client.domain.Client;
import sq.vk.client.dto.ClientDto;

/**
 * Created by Vadzim Kavalkou on 22.03.2017.
 */
@Component("ClientConverter")
public class ClientConverter implements Function<Client, ClientDto> {

    private static final Logger LOG = LoggerFactory.getLogger(ClientConverter.class);

    @Override
    public ClientDto apply(Client client) {

        LOG.info("Converts Client = [{}] into ClientDto.", client);

        ClientDto clientDto = new ClientDto.Builder(client.getEmail())
                                .setId(client.getId())
                                .setFirstName(client.getFirstName())
                                .setSecondName(client.getSecondName())
                                .setPassword(client.getPassword())
                .setRole(client.getRole())
                             .build();

        LOG.info("Client was successfully converted into ClientDto = [{}].", clientDto);

        return clientDto;
    }
}
