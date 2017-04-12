package sq.vk.statistic.conveter;

import org.junit.Before;
import org.junit.Test;
import sq.vk.statistic.domain.PokerRoomType;
import sq.vk.statistic.domain.Statistic;
import sq.vk.statistic.dto.StatisticDto;

import static org.junit.Assert.assertEquals;

/**
 * Created by Vadzim_Kavalkou on 4/12/2017.
 */
public class StatisticConverterTest {

  private static final String NAME = "USER";
  private static final double PROFIT = 20_000D;

  private StatisticConverter converter;

  @Before
  public void setUp(){

    converter = new StatisticConverter();
  }

  @Test
  public void apply() {

    //given
    final PokerRoomType role = PokerRoomType.POKERSTARS;
    final Integer id = 1;

    final Statistic statistic = new Statistic.Builder().setId(id).setName(NAME).setPokerRoom(role).setProfit(
      PROFIT).build();

    final StatisticDto expectedStatisticDto = new StatisticDto.Builder().setId(id).setName(NAME).setPokerRoom(
      role).setProfit(PROFIT).build();

    //when
    StatisticDto actualStatisticDto = converter.apply(statistic);

    //then
    assertEquals(expectedStatisticDto, actualStatisticDto);

  }

  @Test
  public void transform() {

    //given
    final PokerRoomType role = PokerRoomType.POKERSTARS;
    final Integer id = 1;

    final Statistic expectedStatistic = new Statistic.Builder().setId(id).setName(NAME).setPokerRoom(role).setProfit(
        PROFIT).build();

    final StatisticDto statisticDto = new StatisticDto.Builder().setId(id).setName(NAME).setPokerRoom(
        role).setProfit(PROFIT).build();

    //when
    Statistic actualStatistic = converter.transform(statisticDto);

    //then
    assertEquals(expectedStatistic, actualStatistic);

  }

}