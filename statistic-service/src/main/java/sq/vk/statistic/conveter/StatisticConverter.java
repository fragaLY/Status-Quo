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
    public StatisticDto apply(Statistic statistic) {

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
}
