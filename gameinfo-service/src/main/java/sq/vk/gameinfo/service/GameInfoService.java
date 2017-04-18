package sq.vk.gameinfo.service;

import java.util.List;

import sq.vk.gameinfo.dto.GameInfoDto;

/**
 * Created by Vadzim_Kavalkou on 4/18/2017.
 */
public interface GameInfoService {

  List<GameInfoDto> findAll();

  GameInfoDto findOne(Integer id);

  GameInfoDto save(GameInfoDto info);

  GameInfoDto delete(GameInfoDto info);

  Integer delete(Integer id);

}
