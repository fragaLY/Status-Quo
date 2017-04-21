package sq.vk.core.converter.statistic;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;
import sq.vk.core.domain.client.Client;
import sq.vk.core.domain.client.ClientRole;
import sq.vk.core.domain.gameinfo.GameInfo;
import sq.vk.core.domain.gameinfo.PokerRoomType;
import sq.vk.core.domain.gameinfo.Speed;
import sq.vk.core.domain.gameinfo.TableSize;
import sq.vk.core.domain.gameinfo.Type;
import sq.vk.core.domain.statistic.Statistic;
import sq.vk.core.dto.statistic.StatisticDto;
import sq.vk.core.exceptions.statistic.StatisticNotFoundException;

import static org.junit.Assert.assertEquals;

/**
 * Created by Vadzim_Kavalkou on 4/12/2017.
 */
public class StatisticConverterTest {

  private static final String NAME = "USER";
  private static final double PROFIT = 20_000D;

  private static final LocalDate from = LocalDate.now();
  private static final LocalDate to = from.plusMonths(3);

  private Client client;
  private GameInfo gameInfo;

  private StatisticConverter converter;

  @Before
  public void setUp(){

    converter = new StatisticConverter();

    client = new Client.Builder("email@email.com")
               .setRole(ClientRole.USER)
               .setPassword("PSW*&!A")
               .setId(15)
               .setFirstName("VAD")
               .setSecondName("KO")
             .build();

    gameInfo = new GameInfo.Builder()
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
                                  .setClient(client)
                                  .setGameInfo(gameInfo)
                                  .setFrom(from)
                                  .setTo(to)
                                .build();

    final StatisticDto expectedStatisticDto = new StatisticDto.Builder()
                                                .setId(id)
                                                .setName(NAME)
                                                .setProfit(PROFIT)
                                                .setClient(client)
                                                .setGameInfo(gameInfo)
                                                .setFrom(from)
                                                .setTo(to)
                                              .build();

    //when
    StatisticDto actualStatisticDto = converter.apply(statistic);

    //then
    assertEquals(expectedStatisticDto, actualStatisticDto);

  }

  @Test(expected = StatisticNotFoundException.class)
  public void apply_whenStatisticIsNull() {

    //when
    converter.apply(null);

  }

  @Test
  public void transform() {

    //given
    final Integer id = 1;

    final Statistic expectedStatistic = new Statistic.Builder()
                                          .setId(id)
                                          .setName(NAME)
                                          .setProfit(PROFIT)
                                          .setClient(client)
                                          .setGameInfo(gameInfo)
                                          .setFrom(from)
                                          .setTo(to)
                                        .build();

    final StatisticDto statisticDto = new StatisticDto.Builder()
                                        .setId(id)
                                        .setName(NAME)
                                        .setProfit(PROFIT)
                                        .setClient(client)
                                        .setGameInfo(gameInfo)
                                        .setFrom(from)
                                        .setTo(to)
                                      .build();

    //when
    Statistic actualStatistic = converter.transform(statisticDto);

    //then
    assertEquals(expectedStatistic, actualStatistic);

  }

}