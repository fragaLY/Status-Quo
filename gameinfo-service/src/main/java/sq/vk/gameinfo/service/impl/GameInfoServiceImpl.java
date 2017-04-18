package sq.vk.gameinfo.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sq.vk.gameinfo.converter.GameInfoConverter;
import sq.vk.gameinfo.dao.GameInfoDao;
import sq.vk.gameinfo.dto.GameInfoDto;
import sq.vk.gameinfo.service.GameInfoService;

/**
 * Created by Vadzim_Kavalkou on 4/18/2017.
 */
@Service("GameInfoService")
public class GameInfoServiceImpl implements GameInfoService {

  private static final Logger LOG = LoggerFactory.getLogger(GameInfoServiceImpl.class);

  @Autowired
  private GameInfoDao dao;

  @Autowired
  private GameInfoConverter converter;

  @Override
  @Transactional(readOnly = true, rollbackFor = Throwable.class)
  public List<GameInfoDto> findAll() {

    LOG.info("Starting to get all gameinfos.");

    return dao.findAll().stream().parallel().map(converter).collect(Collectors.toList());

  }

  @Override
  @Transactional(readOnly = true, rollbackFor = Throwable.class)
  public GameInfoDto findOne(Integer id) {

    LOG.info("Starting to get GameInfoDto by id = [{}].", id);

    return converter.apply(dao.findOne(id));

  }

  @Override
  @Transactional(rollbackFor = Throwable.class)
  public GameInfoDto save(GameInfoDto info) {

    LOG.info("Starting to save gameInfo [{}].", info);

    dao.save(converter.transform(info));

    return info;

  }

  @Override
  @Transactional(rollbackFor = Throwable.class)
  public GameInfoDto delete(GameInfoDto info) {

    LOG.info("Starting to delete gameInfo [{}].", info);

    dao.delete(converter.transform(info));

    return info;

  }

  @Override
  @Transactional(rollbackFor = Throwable.class)
  public Integer delete(Integer id) {

    LOG.info("Starting to delete gameInfo with id [{}].", id);

    dao.delete(id);

    return id;

  }

}
