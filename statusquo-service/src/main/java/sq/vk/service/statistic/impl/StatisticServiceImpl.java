package sq.vk.service.statistic.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sq.vk.core.converter.statistic.StatisticConverter;
import sq.vk.core.dao.statistic.StatisticDao;
import sq.vk.core.domain.statistic.Statistic;
import sq.vk.core.dto.statistic.StatisticDto;
import sq.vk.core.exceptions.statistic.StatisticNotFoundException;
import sq.vk.service.statistic.StatisticService;

/**
 * Created by Vadzim Kavalkou on 08.04.2017.
 */
@Service("StatisticService")
public class StatisticServiceImpl implements StatisticService {

    private static final Logger LOG = LoggerFactory.getLogger(StatisticServiceImpl.class);

    @Autowired
    private StatisticDao dao;

    @Autowired
    private StatisticConverter converter;

    @Override
    @Transactional(readOnly = true)
    public List<StatisticDto> findAll() {

        LOG.info("Starting to get all statistics.");

        return dao.findAll().stream().parallel().map(converter).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public StatisticDto findOne(final Integer id) {

        LOG.info("Starting to get statistic by id = [{}].", id);

        return converter.apply(dao.findOne(id));
    }

    @Override
    @Transactional(readOnly = true)
    public StatisticDto findOne(final String name) {

        LOG.info("Starting to get statistic by name = [{}].", name);

        Optional<Statistic> statistic = dao.findOneByName(name);

        if(!statistic.isPresent()){
            throw new StatisticNotFoundException("Statistic Was Not Found.");
        }

        return converter.apply(statistic.get());
    }


    @Override
    public StatisticDto save(final StatisticDto statisticDto) {

        LOG.info("Starting to save statistic [{}].", statisticDto);

        dao.save(converter.transform(statisticDto));

        return statisticDto;
    }

    @Override
    public StatisticDto delete(final StatisticDto statisticDto) {

        LOG.info("Starting to delete statistic [{}].", statisticDto);

        dao.delete(converter.transform(statisticDto));

        return statisticDto;
    }

    @Override public Integer delete(final Integer id) {

        LOG.info("Starting to delete statistic with id [{}].", id);

        dao.delete(id);

        return id;

    }

}
