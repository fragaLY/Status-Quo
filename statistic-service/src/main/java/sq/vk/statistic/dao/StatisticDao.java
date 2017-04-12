package sq.vk.statistic.dao;

import java.util.List;

import sq.vk.statistic.domain.Statistic;

/**
 * Created by Vadzim_Kavalkou on 4/7/2017.
 */
public interface StatisticDao {

    Statistic saveItem(Statistic statistic);

    List<Statistic> getAllStatistics();

    Statistic getStatisticById(Integer id);

    Statistic getStatisticByName(String name);

}
