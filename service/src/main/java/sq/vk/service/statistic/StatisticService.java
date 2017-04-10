package sq.vk.service.statistic;

import java.io.IOException;
import java.util.List;

import sq.vk.domain.statistic.PokerRoomType;
import sq.vk.domain.statistic.Statistic;
import sq.vk.dto.statistic.StatisticDto;

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
