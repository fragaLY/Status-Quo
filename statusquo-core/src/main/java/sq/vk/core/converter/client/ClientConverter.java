package sq.vk.core.converter.client;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sq.vk.core.converter.statistic.StatisticConverter;
import sq.vk.core.domain.client.Client;
import sq.vk.core.domain.statistic.Statistic;
import sq.vk.core.dto.client.ClientDto;
import sq.vk.core.dto.statistic.StatisticDto;
import sq.vk.core.exceptions.client.ClientNotFoundException;

/**
 * Created by Vadzim Kavalkou on 22.03.2017.
 *
 * ClientConverter.class
 */
@Component("ClientConverter")
public class ClientConverter implements Function<Client, ClientDto> {

    private static final Logger LOG = LoggerFactory.getLogger(ClientConverter.class);

    @Autowired
    private StatisticConverter statisticConverter;

    @Override
    public ClientDto apply(final Client client) throws EntityNotFoundException {

        LOG.info("Converts Client = [{}] into ClientDto.", client);

        if (client == null){

            LOG.info("Client is null.");

            throw new ClientNotFoundException("Client was not found");
        }

        final Set<Statistic> statistics = client.getStatistics();

        final Set<StatisticDto> statisticDtos = statistics.stream().map(statisticConverter).collect(
            Collectors.toSet());

        final ClientDto dto = new ClientDto.Builder(client.getEmail())
                                .setId(client.getId())
                                .setFirstName(client.getFirstName())
                                .setSecondName(client.getSecondName())
                                .setRole(client.getRole())
                                .setPassword(client.getPassword())
                                .setStatistics(statisticDtos)
                             .build();

        LOG.info("Client was successfully converted into ClientDto = [{}].", dto);

        return dto;
    }

    public Client transform(final ClientDto clientDto) {


        LOG.info("Converts ClientDto = [{}] into Client.", clientDto);
        final Set<Statistic> statistics = new HashSet<>();

        clientDto.getStatistics().forEach(dto -> statistics.add(statisticConverter.transform(dto)));

        final Client client = new Client.Builder(clientDto.getEmail())
                            .setId(clientDto.getClientId())
                            .setFirstName(clientDto.getFirstName())
                            .setSecondName(clientDto.getSecondName())
                            .setRole(clientDto.getRole())
                            .setPassword(clientDto.getPassword())
                            .setStatistics(statistics)
                        .build();


        LOG.info("ClientDto was successfully converted into Client = [{}].", client);

        return client;

    }

}
