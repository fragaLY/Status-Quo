package sq.vk.statistic.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sq.vk.statistic.conveter.StatisticConverter;
import sq.vk.statistic.dao.StatisticDao;
import sq.vk.statistic.dto.StatisticDto;
import sq.vk.statistic.service.StatisticService;

/**
 * Created by Vadzim Kavalkou on 08.04.2017.
 */
@Service("StatisticService")
public class StatisticServiceImpl implements StatisticService {

    private static final Logger LOG = LoggerFactory.getLogger(StatisticServiceImpl.class);
    private static final String TOTAL_PROFIT_SELECTOR = "#mainplayergrid-row-0 > td:nth-child(9) > div";

    @Autowired
    private StatisticDao dao;

    @Autowired
    private StatisticConverter converter;

    @Override
    @Transactional(readOnly = true)
    public List<StatisticDto> getAllStatistics() {

        LOG.info("Starting to get all statistics.");

        return dao.getAllStatistics().stream().parallel().map(converter).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public StatisticDto getStatisticById(final Integer id) {

        LOG.info("Starting to get statistic by id = [{}].", id);

        return converter.apply(dao.getStatisticById(id));
    }

    @Override
    @Transactional(readOnly = true)
    public StatisticDto getStatisticByName(final String name) {

        LOG.info("Starting to get statistic by name = [{}].", name);

        return converter.apply(dao.getStatisticByName(name));
    }


    @Override
    public StatisticDto saveStatistic(final StatisticDto statisticDto) {

        LOG.info("Starting to save statistic [{}].", statisticDto);

        dao.saveStatistic(converter.transform(statisticDto));

        return statisticDto;
    }

    @Override
    public StatisticDto deleteStatistic(final StatisticDto statisticDto) {

        LOG.info("Starting to delete statistic [{}].", statisticDto);

        dao.deleteStatistic(converter.transform(statisticDto));

        return statisticDto;
    }

    @Override public Integer deleteStatistic(final Integer id) {
        LOG.info("Starting to delete statistic with id [{}].", id);

        dao.deleteStatistic(id);

        return id;

    }

}
