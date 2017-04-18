package sq.vk.statistic.conveter;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import sq.vk.client.domain.Client;
import sq.vk.client.domain.ClientRole;
import sq.vk.gameinfo.domain.GameInfo;
import sq.vk.gameinfo.domain.PokerRoomType;
import sq.vk.gameinfo.domain.Speed;
import sq.vk.gameinfo.domain.TableSize;
import sq.vk.gameinfo.domain.Type;
import sq.vk.statistic.domain.Statistic;
import sq.vk.statistic.dto.StatisticDto;
import sq.vk.statistic.exceptions.StatisticNotFoundException;

import static org.junit.Assert.assertEquals;

/**
 * Created by Vadzim_Kavalkou on 4/12/2017.
 */
public class StatisticConverterTest {

  private static final String NAME = "USER";
  private static final double PROFIT = 20_000D;

  private static final Date from = new Date(2017,4,18);
  private static final Date to = new Date(2017,4,19);

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