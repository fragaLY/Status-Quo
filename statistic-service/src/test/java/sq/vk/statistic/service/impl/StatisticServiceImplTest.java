package sq.vk.statistic.service.impl;

import java.util.List;
import java.util.Optional;

import com.google.common.collect.ImmutableList;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import sq.vk.statistic.conveter.StatisticConverter;
import sq.vk.statistic.dao.StatisticDao;
import sq.vk.statistic.domain.PokerRoomType;
import sq.vk.statistic.domain.Statistic;
import sq.vk.statistic.dto.StatisticDto;
import sq.vk.statistic.exceptions.StatisticNotFoundException;
import sq.vk.statistic.service.StatisticService;

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

  @Mock
  private StatisticDao dao;

  @Mock
  private StatisticConverter converter;

  @InjectMocks
  private StatisticService service = new StatisticServiceImpl();

  @Test
  public void findAll() {

    //given
    final PokerRoomType role = PokerRoomType.POKERSTARS;
    final Integer firstId = 1;
    final Integer secondId = 1;

    final Statistic firstStatistic = new Statistic.Builder()
                                      .setId(firstId)
                                      .setName(NAME)
                                      .setPokerRoom(role)
                                      .setProfit(PROFIT)
                                    .build();

    final Statistic secondStatistic = new Statistic.Builder()
                                        .setId(secondId)
                                        .setName(NAME)
                                        .setPokerRoom(role)
                                        .setProfit(PROFIT)
                                      .build();

    final StatisticDto firstStatisticDto = new StatisticDto.Builder()
                                            .setId(firstId)
                                            .setName(NAME)
                                            .setPokerRoom(role)
                                            .setProfit(PROFIT)
                                          .build();

    final StatisticDto secondStatisticDto = new StatisticDto.Builder()
                                              .setId(secondId)
                                              .setName(NAME)
                                              .setPokerRoom(role)
                                              .setProfit(PROFIT)
                                            .build();

    final List<Statistic> statistics = ImmutableList.of(firstStatistic, secondStatistic);
    final List<StatisticDto> expectedStatisticDtos = ImmutableList.of(firstStatisticDto, secondStatisticDto);

    //when
    when(dao.findAll()).thenReturn(statistics);

    when(converter.apply(firstStatistic)).thenReturn(firstStatisticDto);
    when(converter.apply(secondStatistic)).thenReturn(secondStatisticDto);

    List<StatisticDto> actualStatisticDtos = service.findAll();

    //then
    assertEquals(expectedStatisticDtos,actualStatisticDtos);

  }


  @Test
  public void findOneById() {

    //given
    final PokerRoomType role = PokerRoomType.POKERSTARS;
    final Integer id = 1;

    final Statistic statistic = new Statistic.Builder()
                                  .setId(id)
                                  .setName(NAME)
                                  .setPokerRoom(role)
                                  .setProfit(PROFIT)
                                .build();

    final StatisticDto expectedStatisticDto = new StatisticDto.Builder()
                                                .setId(id)
                                                .setName(NAME)
                                                .setPokerRoom(role)
                                                .setProfit(PROFIT)
                                              .build();

    //when
    when(dao.findOne(id)).thenReturn(statistic);
    when(converter.apply(statistic)).thenReturn(expectedStatisticDto);

    StatisticDto actualStatistic = service.findOne(id);

    //then
    assertEquals(expectedStatisticDto,actualStatistic);

  }

  @Test(expected = StatisticNotFoundException.class)
  public void findOneByName_whenStatisticIsAbsent() {

    //given
    Statistic statistic = null;

    //when
    when(dao.findOneByName(NAME)).thenReturn(Optional.ofNullable(statistic));

    service.findOne(NAME);

  }


  @Test
  public void findOneByName() {

    //given
    final PokerRoomType role = PokerRoomType.POKERSTARS;
    final Integer id = 1;

    final Statistic statistic = new Statistic.Builder()
                                  .setId(id)
                                  .setName(NAME)
                                  .setPokerRoom(role)
                                  .setProfit(PROFIT)
                                .build();

    final StatisticDto expectedStatisticDto = new StatisticDto.Builder()
                                                .setId(id)
                                                .setName(NAME)
                                                .setPokerRoom(role)
                                                .setProfit(PROFIT)
                                              .build();

    //when
    when(dao.findOneByName(NAME)).thenReturn(Optional.of(statistic));
    when(converter.apply(statistic)).thenReturn(expectedStatisticDto);

    StatisticDto actualStatistic = service.findOne(NAME);

    //then
    assertEquals(expectedStatisticDto,actualStatistic);

  }


  @Test
  public void save() {

    //given
    final PokerRoomType role = PokerRoomType.POKERSTARS;
    final Integer id = 1;

    final Statistic statistic = new Statistic.Builder()
                                  .setId(id)
                                  .setName(NAME)
                                  .setPokerRoom(role)
                                  .setProfit(PROFIT)
                                .build();

    final StatisticDto expectedStatisticDto = new StatisticDto.Builder()
                                                .setId(id)
                                                .setName(NAME)
                                                .setPokerRoom(role)
                                                .setProfit(PROFIT)
                                              .build();

    //when
    when(converter.transform(expectedStatisticDto)).thenReturn(statistic);
    when(dao.save(statistic)).thenReturn(statistic);

    StatisticDto actualStatisticDto = service.save(expectedStatisticDto);

    //then
    assertEquals(expectedStatisticDto, actualStatisticDto);

  }

  @Test
  public void delete() {

    //given
    final PokerRoomType role = PokerRoomType.POKERSTARS;
    final Integer id = 1;

    final Statistic statistic = new Statistic.Builder()
                                  .setId(id)
                                  .setName(NAME)
                                  .setPokerRoom(role)
                                  .setProfit(PROFIT)
                                .build();

    final StatisticDto expectedStatisticDto = new StatisticDto.Builder()
                                                .setId(id)
                                                .setName(NAME)
                                                .setPokerRoom(role)
                                                .setProfit(PROFIT)
                                              .build();

    //when
    when(converter.transform(expectedStatisticDto)).thenReturn(statistic);
    doNothing().when(dao).delete(statistic);

    StatisticDto actualStatisticDto = service.delete(expectedStatisticDto);

    //then
    assertEquals(expectedStatisticDto, actualStatisticDto);

  }


  @Test
  public void deleteById() {

    //given
    final Integer id = 1;

    //when
    doNothing().when(dao).delete(id);

    Integer actualId = service.delete(id);

    //then
    assertEquals(id, actualId);

  }

}