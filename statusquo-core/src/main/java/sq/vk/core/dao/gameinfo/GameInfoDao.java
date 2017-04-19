package sq.vk.core.dao.gameinfo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import sq.vk.core.domain.gameinfo.GameInfo;

/**
 * Created by Vadzim_Kavalkou on 4/18/2017.
 */
public interface GameInfoDao extends JpaRepository<GameInfo, Integer> {

  List<GameInfo> findAll();

  GameInfo findOne(Integer id);

  GameInfo save(GameInfo info);

  void delete(GameInfo info);

  void delete(Integer id);

}
