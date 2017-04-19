package sq.vk.service.gameinfo.impl;

import java.util.List;

import com.google.common.collect.ImmutableList;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import sq.vk.core.converter.gameinfo.GameInfoConverter;
import sq.vk.core.dao.gameinfo.GameInfoDao;
import sq.vk.core.domain.gameinfo.GameInfo;
import sq.vk.core.domain.gameinfo.PokerRoomType;
import sq.vk.core.domain.gameinfo.Speed;
import sq.vk.core.domain.gameinfo.TableSize;
import sq.vk.core.domain.gameinfo.Type;
import sq.vk.core.dto.gameinfo.GameInfoDto;
import sq.vk.service.gameinfo.GameInfoService;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

/**
 * Created by Vadzim_Kavalkou on 4/18/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class GameInfoServiceImplTest {

  private static final Integer FIRST_ID = 276;
  private static final Integer SECOND_ID = 12;
  private static final TableSize FULLRING = TableSize.FULLRING;
  private static final Speed HYPERTURBO = Speed.HYPERTURBO;
  private static final Type MTT = Type.MTT;
  private static final PokerRoomType EIGHTS = PokerRoomType.EIGHTS;

  @Mock
  private GameInfoDao dao;

  @Mock
  private GameInfoConverter converter;

  @InjectMocks
  private GameInfoService service = new GameInfoServiceImpl();

  @Test
  public void findAll() {

    //given
    final GameInfo firstGameInfo = new GameInfo.Builder()
                                     .setId(FIRST_ID)
                                     .setSize(FULLRING)
                                     .setSpeed(HYPERTURBO)
                                     .setType(MTT)
                                     .setRoomType(EIGHTS)
                                   .build();

    final GameInfo secondGameInfo = new GameInfo.Builder()
                                      .setId(SECOND_ID)
                                      .setSize(FULLRING)
                                      .setSpeed(HYPERTURBO)
                                      .setType(MTT)
                                      .setRoomType(EIGHTS)
                                    .build();

    final GameInfoDto firstDto = new GameInfoDto.Builder()
                                   .setId(FIRST_ID)
                                   .setSize(FULLRING)
                                   .setSpeed(HYPERTURBO)
                                   .setType(MTT)
                                   .setRoomType(EIGHTS)
                                 .build();

    final GameInfoDto secondDto = new GameInfoDto.Builder()
                                    .setId(SECOND_ID)
                                    .setSize(FULLRING)
                                    .setSpeed(HYPERTURBO)
                                    .setType(MTT)
                                    .setRoomType(EIGHTS)
                                  .build();

    final List<GameInfo> gameInfos = ImmutableList.of(firstGameInfo,secondGameInfo);
    final List<GameInfoDto> expectedDtos = ImmutableList.of(firstDto,secondDto);

    //when
    when(dao.findAll()).thenReturn(gameInfos);

    when(converter.apply(firstGameInfo)).thenReturn(firstDto);
    when(converter.apply(secondGameInfo)).thenReturn(secondDto);

    List<GameInfoDto> actualDtos = service.findAll();

    //then
    assertEquals(expectedDtos,actualDtos);

  }

  @Test
  public void findOne() {

    //given
    final GameInfo gameInfo = new GameInfo.Builder()
                                .setId(FIRST_ID)
                                .setSize(FULLRING)
                                .setSpeed(HYPERTURBO)
                                .setType(MTT)
                                .setRoomType(EIGHTS)
                              .build();

    final GameInfoDto expectedDto = new GameInfoDto.Builder()
                                      .setId(FIRST_ID)
                                      .setSize(FULLRING)
                                      .setSpeed(HYPERTURBO)
                                      .setType(MTT)
                                      .setRoomType(EIGHTS)
                                    .build();

    //when
    when(dao.findOne(FIRST_ID)).thenReturn(gameInfo);
    when(converter.apply(gameInfo)).thenReturn(expectedDto);

    GameInfoDto actualDto = service.findOne(FIRST_ID);

    //then
    assertEquals(expectedDto,actualDto);

  }

  @Test
  public void save() {

    //given
    final GameInfo gameInfo = new GameInfo.Builder()
                                .setId(FIRST_ID)
                                .setSize(FULLRING)
                                .setSpeed(HYPERTURBO)
                                .setType(MTT)
                                .setRoomType(EIGHTS)
                              .build();

    final GameInfoDto expectedDto = new GameInfoDto.Builder()
                                      .setId(FIRST_ID)
                                      .setSize(FULLRING)
                                      .setSpeed(HYPERTURBO)
                                      .setType(MTT)
                                      .setRoomType(EIGHTS)
                                    .build();

    //when
    when(converter.transform(expectedDto)).thenReturn(gameInfo);
    when(dao.save(gameInfo)).thenReturn(gameInfo);

    GameInfoDto actualGameInfo = service.save(expectedDto);

    //then
    assertEquals(expectedDto, actualGameInfo);

  }

  @Test
  public void delete() {

    //given
    final GameInfo gameInfo = new GameInfo.Builder()
                                .setId(FIRST_ID)
                                .setSize(FULLRING)
                                .setSpeed(HYPERTURBO)
                                .setType(MTT)
                                .setRoomType(EIGHTS)
                              .build();

    final GameInfoDto expectedDto = new GameInfoDto.Builder()
                                      .setId(FIRST_ID)
                                      .setSize(FULLRING)
                                      .setSpeed(HYPERTURBO)
                                      .setType(MTT)
                                      .setRoomType(EIGHTS)
                                    .build();

    //when
    when(converter.transform(expectedDto)).thenReturn(gameInfo);
    doNothing().when(dao).delete(gameInfo);

    GameInfoDto actualDto = service.delete(expectedDto);

    //then
    assertEquals(expectedDto,actualDto);

  }

  @Test
  public void deleteById() {

    //given
    final Integer id = 51;

    //when
    doNothing().when(dao).delete(id);

    Integer actualId = service.delete(id);

    //then
    assertEquals(id,actualId);

  }

}