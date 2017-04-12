package sq.vk.statistic.service;

import java.io.IOException;
import java.util.List;

import sq.vk.statistic.domain.PokerRoomType;
import sq.vk.statistic.domain.Statistic;
import sq.vk.statistic.dto.StatisticDto;

/**
 * Created by Vadzim Kavalkou on 08.04.2017.
 */
public interface StatisticService {

    StatisticDto saveItem(Statistic statistic);

    List<StatisticDto> getAllItems();

    StatisticDto getItemById(Integer id);

    StatisticDto getItemByName(String name);

    Double getTotalProfit(String name, PokerRoomType roomType) throws IOException;

}
