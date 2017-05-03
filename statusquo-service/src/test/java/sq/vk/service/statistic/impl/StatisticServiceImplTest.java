package sq.vk.service.statistic.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.google.common.collect.ImmutableList;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import sq.vk.core.converter.statistic.StatisticConverter;
import sq.vk.core.dao.statistic.StatisticDao;
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
import sq.vk.service.statistic.StatisticService;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

/**
 * Created by Vadzim_Kavalkou on 4/12/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class StatisticServiceImplTest {

  private static final String NAME = "USER";
  private static final double PROFIT = 20_000D;

  private static final LocalDate from = LocalDate.now();
  private static final LocalDate to = from.plusMonths(3);

  private Client client;
  private GameInfo gameInfo;

  @Mock
  private StatisticDao dao;

  @Mock
  private StatisticConverter converter;

  @InjectMocks
  private StatisticService service = new StatisticServiceImpl();

  @Before
  public void setUp(){

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
  public void findAll() {

    //given
    final Integer firstId = 1;
    final Integer secondId = 1;

    final Statistic firstStatistic = new Statistic.Builder()
                                       .setId(firstId)
                                       .setName(NAME)
                                       .setProfit(PROFIT)
                                       .setClient(client)
                                       .setGameInfo(gameInfo)
                                       .setFrom(from)
                                       .setTo(to)
                                     .build();

    final Statistic secondStatistic = new Statistic.Builder()
                                        .setId(secondId)
                                        .setName(NAME)
                                        .setProfit(PROFIT)
                                        .setClient(client)
                                        .setGameInfo(gameInfo)
                                        .setFrom(from)
                                        .setTo(to)
                                      .build();

    final StatisticDto firstStatisticDto = new StatisticDto.Builder()
                                             .setId(firstId)
                                             .setName(NAME)
                                             .setProfit(PROFIT)
                                             .setGameInfo(null)//TODO: FIX POSSIBLE BUG
                                             .setFrom(from)
                                             .setTo(to)
                                           .build();

    final StatisticDto secondStatisticDto = new StatisticDto.Builder()
                                              .setId(secondId)
                                              .setName(NAME)
                                              .setProfit(PROFIT)
                                              .setGameInfo(null)
                                              .setFrom(from)
                                              .setTo(to)
                                            .build();

    final List<Statistic> statistics = ImmutableList.of(firstStatistic, secondStatistic);
    final List<StatisticDto> expectedStatisticDtos = ImmutableList.of(firstStatisticDto, secondStatisticDto);

    when(dao.findAll()).thenReturn(statistics);

    when(converter.apply(firstStatistic)).thenReturn(firstStatisticDto);
    when(converter.apply(secondStatistic)).thenReturn(secondStatisticDto);

    //when
    List<StatisticDto> actualStatisticDtos = service.findAll();

    //then
    assertEquals(expectedStatisticDtos,actualStatisticDtos);//TODO: FIX POSSIBLE BUG

  }

  @Test
  public void findOneById() {

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
                                                .setGameInfo(null)
                                                .setFrom(from)
                                                .setTo(to)
                                              .build();

    when(dao.findOne(id)).thenReturn(statistic);
    when(converter.apply(statistic)).thenReturn(expectedStatisticDto);

    //when
    StatisticDto actualStatistic = service.findOne(id);

    //then
    assertEquals(expectedStatisticDto,actualStatistic);

  }

  @Test(expected = StatisticNotFoundException.class)
  public void findOneByName_whenStatisticIsAbsent() {

    //given
    Statistic statistic = null;

    when(dao.findOneByName(NAME)).thenReturn(Optional.ofNullable(statistic));

    //when
    service.findOne(NAME);

  }

  @Test
  public void findOneByName() {

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
                                                .setGameInfo(null)
                                                .setFrom(from)
                                                .setTo(to)
                                              .build();

    when(dao.findOneByName(NAME)).thenReturn(Optional.of(statistic));
    when(converter.apply(statistic)).thenReturn(expectedStatisticDto);

    //when
    StatisticDto actualStatistic = service.findOne(NAME);

    //then
    assertEquals(expectedStatisticDto,actualStatistic);

  }

  @Test
  public void save() {

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
                                                .setGameInfo(null)
                                                .setFrom(from)
                                                .setTo(to)
                                              .build();

    when(converter.transform(expectedStatisticDto)).thenReturn(statistic);
    when(dao.save(statistic)).thenReturn(statistic);

    //when
    StatisticDto actualStatisticDto = service.save(expectedStatisticDto);

    //then
    assertEquals(expectedStatisticDto, actualStatisticDto);

  }

  @Test
  public void delete() {

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
                                                .setGameInfo(null)
                                                .setFrom(from)
                                                .setTo(to)
                                              .build();

    when(converter.transform(expectedStatisticDto)).thenReturn(statistic);
    doNothing().when(dao).delete(statistic);

    //when
    StatisticDto actualStatisticDto = service.delete(expectedStatisticDto);

    //then
    assertEquals(expectedStatisticDto, actualStatisticDto);

  }

  @Test
  public void deleteById() {

    //given
    final Integer id = 1;

    doNothing().when(dao).delete(id);

    //when
    Integer actualId = service.delete(id);

    //then
    assertEquals(id, actualId);

  }

}