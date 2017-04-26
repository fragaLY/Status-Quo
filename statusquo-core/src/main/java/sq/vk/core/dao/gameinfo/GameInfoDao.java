package sq.vk.core.dao.gameinfo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import sq.vk.core.domain.gameinfo.GameInfo;

/**
 * Created by Vadzim_Kavalkou on 4/18/2017.
 *
 * GameInfoDao interface.
 */
public interface GameInfoDao extends JpaRepository<GameInfo, Integer> {

  List<GameInfo> findAll();

  GameInfo findOne(Integer id);

  @SuppressWarnings("unchecked")
  GameInfo save(GameInfo info);

  void delete(GameInfo info);

  void delete(Integer id);

}
