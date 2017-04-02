package sq.vk.converter.client;

import org.springframework.stereotype.Component;
import sq.vk.domain.client.Client;
import sq.vk.domain.client.ClientRole;
import sq.vk.dto.client.ClientDto;

import java.util.function.Function;

/**
 * Created by Vadzim Kavalkou on 22.03.2017.
 */
@Component("ClientConverter")
public class ClientConverter implements Function<Client, ClientDto> {

    @Override
    public ClientDto apply(Client client) {
        return new ClientDto.Builder(client.getEmail())
                .setId(client.getId())
                .setFirstName(client.getFirstName())
                .setSecondName(client.getSecondName())
                .setPassword(client.getPassword())
                .setRole(ClientRole.getClientRoleByHisRole(client.getRole()))
                .build();
    }
}
