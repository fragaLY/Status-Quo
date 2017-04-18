package sq.vk.gameinfo.converter;

import java.util.function.Function;
import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import sq.vk.gameinfo.domain.GameInfo;
import sq.vk.gameinfo.dto.GameInfoDto;
import sq.vk.gameinfo.exceptions.GameInfoNotFound;

/**
 * Created by Vadzim Kavalkou on 22.03.2017.
 */
@Component("GameInfoConverter")
public class GameInfoConverter implements Function<GameInfo, GameInfoDto> {

    private static final Logger LOG = LoggerFactory.getLogger(GameInfoConverter.class);

    @Override
    public GameInfoDto apply(final GameInfo gameInfo) throws EntityNotFoundException {

        LOG.info("Converts GameInfo = [{}] into GameInfoDto.", gameInfo);

        if (gameInfo == null){

            LOG.info("GameInfo is null.");

            throw new GameInfoNotFound("GameInfo was not found");
        }

        GameInfoDto dto = new GameInfoDto.Builder()
                            .setId(gameInfo.getId())
                            .setRoomType(gameInfo.getRoomType())
                            .setSize(gameInfo.getSize())
                            .setSpeed(gameInfo.getSpeed())
                            .setType(gameInfo.getType())
                          .build();

        LOG.info("GameInfo was successfully converted into GameInfoDto = [{}].", dto);

        return dto;
    }

    public GameInfo transform(final GameInfoDto dto) {


        LOG.info("Converts GameInfoDto = [{}] into GameInfo.", dto);

        GameInfo gameInfo = new GameInfo.Builder()
                            .setId(dto.getId())
                            .setRoomType(dto.getRoomType())
                            .setSize(dto.getSize())
                            .setSpeed(dto.getSpeed())
                            .setType(dto.getType())
                        .build();


        LOG.info("GameInfoDto was successfully converted into GameInfo = [{}].", gameInfo);

        return gameInfo;

    }

}
