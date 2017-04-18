package sq.vk.statistic.conveter;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import sq.vk.statistic.domain.Statistic;
import sq.vk.statistic.dto.StatisticDto;
import sq.vk.statistic.exceptions.StatisticNotFoundException;

import static org.junit.Assert.assertEquals;

/**
 * Created by Vadzim_Kavalkou on 4/12/2017.
 */
public class StatisticConverterTest {

  //TODO VK: implement logic
  private static final String NAME = "USER";
  private static final double PROFIT = 20_000D;

  private StatisticConverter converter;

  @Before
  public void setUp(){

    converter = new StatisticConverter();
  }

  @Test
  @Ignore
  public void apply() {

    //given
    final Integer id = 1;

    final Statistic statistic = new Statistic.Builder()
                                  .setId(id)
                                  .setName(NAME)
                                  .setProfit(PROFIT)
                                .build();

    final StatisticDto expectedStatisticDto = new StatisticDto.Builder()
                                                .setId(id)
                                                .setName(NAME)
                                                .setProfit(PROFIT)
                                              .build();

    //when
    StatisticDto actualStatisticDto = converter.apply(statistic);

    //then
    assertEquals(expectedStatisticDto, actualStatisticDto);

  }

  @Test(expected = StatisticNotFoundException.class)
  @Ignore
  public void apply_whenStatisticIsNull() {

    //when
    converter.apply(null);

  }

  @Test
  @Ignore
  public void transform() {

    //given
    final Integer id = 1;

    final Statistic expectedStatistic = new Statistic.Builder()
                                          .setId(id)
                                          .setName(NAME)
                                          .setProfit(PROFIT)
                                        .build();

    final StatisticDto statisticDto = new StatisticDto.Builder()
                                        .setId(id)
                                        .setName(NAME)
                                        .setProfit(PROFIT)
                                      .build();

    //when
    Statistic actualStatistic = converter.transform(statisticDto);

    //then
    assertEquals(expectedStatistic, actualStatistic);

  }

}