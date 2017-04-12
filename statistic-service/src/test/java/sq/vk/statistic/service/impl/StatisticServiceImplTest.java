package sq.vk.statistic.service.impl;

import java.util.List;

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
import sq.vk.statistic.service.StatisticService;

import static org.junit.Assert.assertEquals;
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
  public void getAllStatistics() {

    //given
    final PokerRoomType role = PokerRoomType.POKERSTARS;
    final Integer firstId = 1;
    final Integer secondId = 1;

    final Statistic firstStatistic = new Statistic.Builder().setId(firstId).setName(NAME).setPokerRoom(
      role).setProfit(PROFIT).build();

    final Statistic secondStatistic = new Statistic.Builder().setId(secondId).setName(NAME).setPokerRoom(
      role).setProfit(PROFIT).build();

    final StatisticDto firstStatisticDto = new StatisticDto.Builder().setId(firstId).setName(
      NAME).setPokerRoom(role).setProfit(PROFIT).build();

    final StatisticDto secondStatisticDto = new StatisticDto.Builder().setId(secondId).setName(
      NAME).setPokerRoom(role).setProfit(PROFIT).build();

    final List<Statistic> statistics = ImmutableList.of(firstStatistic, secondStatistic);
    final List<StatisticDto> expectedStatisticDtos = ImmutableList.of(firstStatisticDto, secondStatisticDto);

    //when
    when(dao.getAllStatistics()).thenReturn(statistics);

    when(converter.apply(firstStatistic)).thenReturn(firstStatisticDto);
    when(converter.apply(secondStatistic)).thenReturn(secondStatisticDto);

    List<StatisticDto> actualStatisticDtos = service.getAllStatistics();

    //then
    assertEquals(expectedStatisticDtos,actualStatisticDtos);

  }


  @Test
  public void getStatisticById() {

    //given
    final PokerRoomType role = PokerRoomType.POKERSTARS;
    final Integer id = 1;

    final Statistic statistic = new Statistic.Builder().setId(id).setName(NAME).setPokerRoom(role).setProfit(
        PROFIT).build();

    final StatisticDto expectedStatisticDto = new StatisticDto.Builder().setId(id).setName(NAME).setPokerRoom(
        role).setProfit(PROFIT).build();

    //when
    when(dao.getStatisticById(id)).thenReturn(statistic);
    when(converter.apply(statistic)).thenReturn(expectedStatisticDto);

    StatisticDto actualStatistic = service.getStatisticById(id);

    //then
    assertEquals(expectedStatisticDto,actualStatistic);

  }


  @Test
  public void getStatisticByName() {

    //given
    final PokerRoomType role = PokerRoomType.POKERSTARS;
    final Integer id = 1;

    final Statistic statistic = new Statistic.Builder().setId(id).setName(NAME).setPokerRoom(role).setProfit(
        PROFIT).build();

    final StatisticDto expectedStatisticDto = new StatisticDto.Builder().setId(id).setName(NAME).setPokerRoom(
        role).setProfit(PROFIT).build();

    //when
    when(dao.getStatisticByName(NAME)).thenReturn(statistic);
    when(converter.apply(statistic)).thenReturn(expectedStatisticDto);

    StatisticDto actualStatistic = service.getStatisticByName(NAME);

    //then
    assertEquals(expectedStatisticDto,actualStatistic);

  }

  @Test
  public void saveStatistic() {

    //given
    final PokerRoomType role = PokerRoomType.POKERSTARS;
    final Integer id = 1;

    final Statistic statistic = new Statistic.Builder().setId(id).setName(NAME).setPokerRoom(role).setProfit(
      PROFIT).build();

    final StatisticDto expectedStatisticDto = new StatisticDto.Builder().setId(id).setName(NAME).setPokerRoom(
      role).setProfit(PROFIT).build();

    //when
    when(converter.transform(expectedStatisticDto)).thenReturn(statistic);
    when(dao.saveStatistic(statistic)).thenReturn(statistic);

    StatisticDto actualStatisticDto = service.saveStatistic(expectedStatisticDto);

    //then
    assertEquals(expectedStatisticDto, actualStatisticDto);

  }

  @Test
  public void deleteStatistic() {

    //given
    final PokerRoomType role = PokerRoomType.POKERSTARS;
    final Integer id = 1;

    final Statistic statistic = new Statistic.Builder().setId(id).setName(NAME).setPokerRoom(role).setProfit(
      PROFIT).build();

    final StatisticDto expectedStatisticDto = new StatisticDto.Builder().setId(id).setName(NAME).setPokerRoom(
      role).setProfit(PROFIT).build();

    //when
    when(converter.transform(expectedStatisticDto)).thenReturn(statistic);
    when(dao.deleteStatistic(statistic)).thenReturn(statistic);

    StatisticDto actualStatisticDto = service.deleteStatistic(expectedStatisticDto);

    //then
    assertEquals(expectedStatisticDto, actualStatisticDto);

  }


  @Test
  public void deleteStatisticById() {

    //given
    final Integer id = 1;

    //when
    when(dao.deleteStatistic(id)).thenReturn(id);

    Integer actualId = service.deleteStatistic(id);

    //then
    assertEquals(id, actualId);

  }

}