package sq.vk.playerstatistic.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sq.vk.playerstatistic.domain.GameInfo;
import sq.vk.playerstatistic.service.PlayerStatisticService;
import sq.vk.statistic.dto.StatisticDto;

/**
 * Created by Vadzim_Kavalkou on 4/17/2017.
 */
@Service("gameInfo")
public class PlayerStatisticServiceImpl implements PlayerStatisticService {

  @Override
  @Transactional(rollbackFor = Throwable.class)
  public StatisticDto getUniqueStatistic(GameInfo gameInfo) {
    return null;//TODO VK: IMPLEMENT SOME LOGIC
  }
}
