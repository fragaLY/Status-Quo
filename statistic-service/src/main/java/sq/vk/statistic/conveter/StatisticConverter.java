package sq.vk.statistic.conveter;

import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import sq.vk.statistic.domain.Statistic;
import sq.vk.statistic.dto.StatisticDto;

/**
 * Created by Vadzim_Kavalkou on 4/7/2017.
 */
@Component("StatisticConverter")
public class StatisticConverter implements Function<Statistic, StatisticDto> {

    private static final Logger LOG = LoggerFactory.getLogger(StatisticConverter.class);

    @Override
    public StatisticDto apply(final Statistic statistic) {

        LOG.info("Converts Statistic = [{}] into StatisticDto.", statistic);

        StatisticDto statisticDto = new StatisticDto.Builder()
                                        .setId(statistic.getId())
                                        .setName(statistic.getName())
                                        .setPokerRoom(statistic.getPokerRoom())
                                        .setProfit(statistic.getProfit())
                                    .build();

        LOG.info("Statistic was successfully converted into statisticDto = [{}].", statisticDto);

        return statisticDto;
    }

    public Statistic transform(final StatisticDto statisticDto){

        LOG.info("Converts StatisticDto = [{}] into Statistic.", statisticDto);

        Statistic statistic = new Statistic.Builder()
                                 .setId(statisticDto.getId())
                                 .setName(statisticDto.getName())
                                 .setPokerRoom(statisticDto.getPokerRoom())
                                 .setProfit(statisticDto.getProfit())
                             .build();

        LOG.info("StatisticDto was successfully converted into Statistic = [{}].", statistic);

        return statistic;

    }

}
