package sq.vk.core.converter.statistic;

import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import sq.vk.core.domain.statistic.Statistic;
import sq.vk.core.dto.statistic.StatisticDto;
import sq.vk.core.exceptions.statistic.StatisticNotFoundException;

/**
 * Created by Vadzim_Kavalkou on 4/7/2017.
 *
 * StatisticConverter.class
 */
@Component("StatisticConverter")
public class StatisticConverter implements Function<Statistic, StatisticDto> {

    private static final Logger LOG = LoggerFactory.getLogger(StatisticConverter.class);

    @Override
    public StatisticDto apply(final Statistic statistic) {

        LOG.info("Converts Statistic = [{}] into StatisticDto.", statistic);

        if (statistic == null){

            LOG.info("Statistic is null.");

            throw new StatisticNotFoundException("Statistic was not found");
        }

        StatisticDto statisticDto = new StatisticDto.Builder()
                                        .setId(statistic.getId())
                                        .setName(statistic.getName())
                                        .setProfit(statistic.getProfit())
                                        .setGameInfo(statistic.getGameInfo())
                                        .setClient(statistic.getClient())
                                        .setFrom(statistic.getFrom())
                                        .setTo(statistic.getTo())
                                    .build();

        LOG.info("Statistic was successfully converted into statisticDto = [{}].", statisticDto);

        return statisticDto;
    }

    public Statistic transform(final StatisticDto statisticDto){

        LOG.info("Converts StatisticDto = [{}] into Statistic.", statisticDto);

        Statistic statistic = new Statistic.Builder()
                                 .setId(statisticDto.getStatisticId())
                                 .setName(statisticDto.getName())
                                 .setProfit(statisticDto.getProfit())
                                 .setGameInfo(statisticDto.getGameInfo())
                                 .setClient(statisticDto.getClient())
                                 .setFrom(statisticDto.getFrom())
                                 .setTo(statisticDto.getTo())
                             .build();

        LOG.info("StatisticDto was successfully converted into Statistic = [{}].", statistic);

        return statistic;

    }

}
