package sq.vk.core.converter.statistic;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import sq.vk.core.converter.gameinfo.GameInfoConverter;
import sq.vk.core.domain.gameinfo.GameInfo;
import sq.vk.core.domain.gameinfo.PokerRoomType;
import sq.vk.core.domain.gameinfo.Speed;
import sq.vk.core.domain.gameinfo.TableSize;
import sq.vk.core.domain.gameinfo.Type;
import sq.vk.core.domain.statistic.Statistic;
import sq.vk.core.dto.gameinfo.GameInfoDto;
import sq.vk.core.dto.statistic.StatisticDto;
import sq.vk.core.exceptions.statistic.StatisticNotFoundException;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * Created by Vadzim_Kavalkou on 4/12/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class StatisticConverterTest {

  private static final String NAME = "USER";
  private static final double PROFIT = 20_000D;

  private static final LocalDate from = LocalDate.now();
  private static final LocalDate to = from.plusMonths(3);

  private GameInfo gameInfo;
  private GameInfoDto gameInfoDto;

  @Mock
  private GameInfoConverter gameInfoConverter;

  @InjectMocks
  private StatisticConverter statisticConverter = new StatisticConverter();

  @Before
  public void setUp(){

    gameInfo = new GameInfo.Builder()
                 .setType(Type.ALL)
                 .setSpeed(Speed.NORMAL)
                 .setRoomType(PokerRoomType.POKERSTARS)
                 .setSize(TableSize.HEADSUP)
                 .setId(154)
               .build();

    gameInfoDto = new GameInfoDto.Builder()
        .setType(Type.ALL)
        .setSpeed(Speed.NORMAL)
        .setRoomType(PokerRoomType.POKERSTARS)
        .setSize(TableSize.HEADSUP)
        .setId(154)
        .build();
  }

  @Test
  public void apply() {

    //given
    final Integer id = 1;

    final Statistic statistic = new Statistic.Builder()
                                  .setId(id)
                                  .setName(NAME)
                                  .setProfit(PROFIT)
                                  .setGameInfo(gameInfo)
                                  .setFrom(from)
                                  .setTo(to)
                                .build();

    final StatisticDto expectedStatisticDto = new StatisticDto.Builder()
                                                .setId(id)
                                                .setName(NAME)
                                                .setProfit(PROFIT)
                                                .setGameInfo(gameInfoDto)
                                                .setFrom(from)
                                                .setTo(to)
                                              .build();

    when(gameInfoConverter.apply(any(GameInfo.class))).thenReturn(gameInfoDto);

    //when
    StatisticDto actualStatisticDto = statisticConverter.apply(statistic);

    //then
    assertEquals(expectedStatisticDto, actualStatisticDto);

  }

  @Test(expected = StatisticNotFoundException.class)
  public void apply_whenStatisticIsNull() {

    //when
    statisticConverter.apply(null);

  }

  @Test
  public void transform() {

    //given
    final Integer id = 1;

    final Statistic expectedStatistic = new Statistic.Builder()
                                          .setId(id)
                                          .setName(NAME)
                                          .setProfit(PROFIT)
                                          .setGameInfo(gameInfo)
                                          .setFrom(from)
                                          .setTo(to)
                                        .build();

    final StatisticDto statisticDto = new StatisticDto.Builder()
                                        .setId(id)
                                        .setName(NAME)
                                        .setProfit(PROFIT)
                                        .setGameInfo(gameInfoDto)
                                        .setFrom(from)
                                        .setTo(to)
                                      .build();

    when(gameInfoConverter.transform(any(GameInfoDto.class))).thenReturn(gameInfo);

    //when
    Statistic actualStatistic = statisticConverter.transform(statisticDto);

    //then
    assertEquals(expectedStatistic, actualStatistic);

  }

}