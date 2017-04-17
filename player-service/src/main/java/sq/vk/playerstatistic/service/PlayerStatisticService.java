package sq.vk.playerstatistic.service;

import sq.vk.playerstatistic.domain.GameInfo;
import sq.vk.statistic.dto.StatisticDto;

/**
 * Created by Vadzim_Kavalkou on 4/17/2017.
 */
public interface PlayerStatisticService {

  StatisticDto getUniqueStatistic(GameInfo gameInfo);

}
