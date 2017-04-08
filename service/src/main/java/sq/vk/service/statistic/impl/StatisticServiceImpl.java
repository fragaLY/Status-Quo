package sq.vk.service.statistic.impl;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sq.vk.converter.statistic.StatisticConverter;
import sq.vk.dao.statistic.StatisticDao;
import sq.vk.domain.statistic.PokerRoomType;
import sq.vk.domain.statistic.Statistic;
import sq.vk.dto.statistic.StatisticDto;
import sq.vk.service.statistic.StatisticService;

import java.util.List;
import java.util.stream.Collectors;

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
    public StatisticDto saveItem(final Statistic statistic) {

        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public List<StatisticDto> getAllItems() {

        LOG.info("Starting to get all statistics.");

        return dao.getAllItems().stream().parallel().map(converter).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public StatisticDto getItemById(final Integer id) {

        LOG.info("Starting to get statistic by id = [{}].", id);

        return converter.apply(dao.getItemById(id));
    }

    @Override
    @Transactional(readOnly = true)
    public StatisticDto getItemByName(final String name) {

        LOG.info("Starting to get statistic by name = [{}].", name);

        return converter.apply(dao.getItemByName(name));
    }

    @Override
    @Transactional(readOnly = false)
    public Double getTotalProfit(final String name, final PokerRoomType roomType) {

        final String uri = new StringBuilder("https://www.sharkscope.com/#Player-Statistics//networks/")
                .append("PokerStars")
                .append("/players/")
                .append(name)
                .toString();

        Document document = new Document(uri);

        Elements select = document.select(TOTAL_PROFIT_SELECTOR);

        return Double.valueOf(select.toString().substring(1));
    }
}
