package sq.vk.core.converter.client;

import java.time.LocalDate;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.util.collections.Sets;
import org.mockito.junit.MockitoJUnitRunner;
import sq.vk.core.converter.statistic.StatisticConverter;
import sq.vk.core.domain.client.Client;
import sq.vk.core.domain.client.ClientRole;
import sq.vk.core.domain.gameinfo.GameInfo;
import sq.vk.core.domain.statistic.Statistic;
import sq.vk.core.dto.client.ClientDto;
import sq.vk.core.dto.gameinfo.GameInfoDto;
import sq.vk.core.dto.statistic.StatisticDto;
import sq.vk.core.exceptions.client.ClientNotFoundException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

/**
 * Created by Vadzim_Kavalkou on 4/12/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class ClientConverterTest {

  private static final String EMAIL = "email@something.com";
  private static final String FIRSTNAME = "FirstName";
  private static final String SECONDNAME = "SecondName";
  private static final String PASSWORD = "Password";

  private Statistic statistic1;
  private Statistic statistic2;

  private StatisticDto statisticDto1;
  private StatisticDto statisticDto2;

  private Set<Statistic> statisticSet;
  private Set<StatisticDto> statisticDtoSet;

  @Mock
  private StatisticConverter statisticConverter;

  @InjectMocks
  private ClientConverter converter = new ClientConverter();

  @Before
  public void setUp() {

    final LocalDate from = LocalDate.of(2017, 5, 3);
    final LocalDate to = LocalDate.of(2017, 5, 31);

    statistic1 = new Statistic.Builder()
        .setId(1)
        .setName("fragaLY")
        .setProfit(45_000)
        .setGameInfo(new GameInfo())
        .setFrom(from)
        .setTo(to)
        .build();

    statistic2 = new Statistic.Builder()
        .setId(2)
        .setName("unzob")
        .setProfit(25_000)
        .setGameInfo(new GameInfo())
        .setFrom(from)
        .setTo(to)
        .build();

    statisticDto1 = new StatisticDto.Builder()
        .setId(1)
        .setName("fragaLY")
        .setProfit(45_000)
        .setGameInfo(new GameInfoDto())
        .setFrom(from)
        .setTo(to)
        .build();

    statisticDto2 = new StatisticDto.Builder()
        .setId(2)
        .setName("unzob")
        .setProfit(25_000)
        .setGameInfo(new GameInfoDto())
        .setFrom(from)
        .setTo(to)
        .build();

    statisticSet = Sets.newSet(statistic1,statistic2);
    statisticDtoSet = Sets.newSet(statisticDto1,statisticDto2);

  }

  @Test
  public void apply() {

    //given
    final ClientRole role = ClientRole.DEVELOPER;

    final Client client = new Client.Builder(EMAIL)
                            .setId(1)
                            .setFirstName(FIRSTNAME)
                            .setSecondName(SECONDNAME)
                            .setRole(role)
                            .setPassword(PASSWORD)
                            .setStatistics(statisticSet)
                          .build();

    final ClientDto expectedClientDto = new ClientDto.Builder(EMAIL)
                                          .setId(1)
                                          .setFirstName(FIRSTNAME)
                                          .setSecondName(SECONDNAME)
                                          .setRole(role)
                                          .setPassword(PASSWORD)
                                          .setStatistics(statisticDtoSet)
                                        .build();

    when(statisticConverter.apply(statistic1)).thenReturn(statisticDto1);
    when(statisticConverter.apply(statistic2)).thenReturn(statisticDto2);

    //when
    ClientDto actualClientDto = converter.apply(client);

    //then
    assertEquals(expectedClientDto,actualClientDto);

  }

  @Test(expected = ClientNotFoundException.class)
  public void apply_whenClientIsNull() {

    //when
    converter.apply(null);

  }

  @Test
  public void transform() {

    //given
    final ClientRole role = ClientRole.DEVELOPER;
    final Integer id = 1;

    final Client expectedClient = new Client.Builder(EMAIL)
                                    .setId(id)
                                    .setFirstName(FIRSTNAME)
                                    .setSecondName(SECONDNAME)
                                    .setRole(role)
                                    .setStatistics(statisticSet)
                                    .setPassword(PASSWORD)
                                  .build();

    final ClientDto clientDto = new ClientDto.Builder(EMAIL)
                                  .setId(id)
                                  .setFirstName(FIRSTNAME)
                                  .setSecondName(SECONDNAME)
                                  .setRole(role)
                                  .setStatistics(statisticDtoSet)
                                  .setPassword(PASSWORD)
                                .build();

    when(statisticConverter.transform(statisticDto1)).thenReturn(statistic1);
    when(statisticConverter.transform(statisticDto2)).thenReturn(statistic2);

    //when
    Client actualClient = converter.transform(clientDto);

    //then
    assertEquals(expectedClient,actualClient);

  }

}