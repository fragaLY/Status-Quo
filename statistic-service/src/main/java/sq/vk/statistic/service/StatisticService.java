package sq.vk.statistic.service;

import java.util.List;

import sq.vk.statistic.dto.StatisticDto;

/**
 * Created by Vadzim Kavalkou on 08.04.2017.
 */
public interface StatisticService {

    List<StatisticDto> findAll();

    StatisticDto findOne(Integer id);

    StatisticDto findOne(String name);

    StatisticDto save(StatisticDto statisticDto);

    StatisticDto delete(StatisticDto statisticDto);

    Integer delete(Integer id);

}
