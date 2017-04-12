package sq.vk.statistic.service;

import java.util.List;

import sq.vk.statistic.dto.StatisticDto;

/**
 * Created by Vadzim Kavalkou on 08.04.2017.
 */
public interface StatisticService {

    List<StatisticDto> getAllStatistics();

    StatisticDto getStatisticById(Integer id);

    StatisticDto getStatisticByName(String name);

    StatisticDto saveStatistic(StatisticDto statisticDto);

    StatisticDto deleteStatistic(StatisticDto statisticDto);

    Integer deleteStatistic(Integer id);

}
