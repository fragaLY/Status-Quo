package sq.vk.core.converter.gameinfo;

import org.junit.Before;
import org.junit.Test;
import sq.vk.core.domain.gameinfo.GameInfo;
import sq.vk.core.domain.gameinfo.PokerRoomType;
import sq.vk.core.domain.gameinfo.Speed;
import sq.vk.core.domain.gameinfo.TableSize;
import sq.vk.core.domain.gameinfo.Type;
import sq.vk.core.dto.gameinfo.GameInfoDto;
import sq.vk.core.exceptions.gameinfo.GameInfoNotFound;

import static org.junit.Assert.assertEquals;

/**
 * Created by Vadzim_Kavalkou on 4/18/2017.
 */
public class GameInfoConverterTest {

  private static final Integer ID = 276;
  private static final TableSize FULLRING = TableSize.FULLRING;
  private static final Speed HYPERTURBO = Speed.HYPERTURBO;
  private static final Type MTT = Type.MTT;
  private static final PokerRoomType EIGHTS = PokerRoomType.EIGHTS;

  private GameInfoConverter converter;

  @Before
  public void setUp(){

    converter = new GameInfoConverter();
  }

  @Test
  public void apply() {

    //given
    final GameInfo gameInfo = new GameInfo.Builder()
                                .setId(ID)
                                .setSize(FULLRING)
                                .setSpeed(HYPERTURBO)
                                .setType(MTT)
                                .setRoomType(EIGHTS)
                              .build();

    final GameInfoDto expectedDto = new GameInfoDto.Builder()
                                      .setId(ID)
                                      .setSize(FULLRING)
                                      .setSpeed(HYPERTURBO)
                                      .setType(MTT)
                                      .setRoomType(EIGHTS)
                                    .build();

    //when
    GameInfoDto actualDto = converter.apply(gameInfo);

    //then
    assertEquals(expectedDto, actualDto);

  }

  @Test(expected = GameInfoNotFound.class)
  public void apply_whenGameInfoIsNull() {

    //when
    converter.apply(null);

  }

  @Test
  public void transform() {

    //given
    final GameInfoDto dto = new GameInfoDto.Builder()
                              .setId(ID)
                              .setSize(FULLRING)
                              .setSpeed(HYPERTURBO)
                              .setType(MTT)
                              .setRoomType(EIGHTS)
                            .build();

    final GameInfo expectedGameInfo = new GameInfo.Builder()
                                        .setId(ID)
                                        .setSize(FULLRING)
                                        .setSpeed(HYPERTURBO)
                                        .setType(MTT)
                                        .setRoomType(EIGHTS)
                                      .build();

    //when
    GameInfo actualGameInfo = converter.transform(dto);

    //then
    assertEquals(expectedGameInfo,actualGameInfo);

  }

}