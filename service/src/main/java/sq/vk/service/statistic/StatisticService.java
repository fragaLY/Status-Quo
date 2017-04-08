package sq.vk.service.statistic;

import sq.vk.domain.statistic.PokerRoomType;
import sq.vk.domain.statistic.Statistic;
import sq.vk.dto.statistic.StatisticDto;

import java.util.List;

/**
 * Created by Vadzim Kavalkou on 08.04.2017.
 */
public interface StatisticService {

    StatisticDto saveItem(Statistic statistic);

    List<StatisticDto> getAllItems();

    StatisticDto getItemById(Integer id);

    StatisticDto getItemByName(String name);

    Double getTotalProfit(String name, PokerRoomType roomType);

}
