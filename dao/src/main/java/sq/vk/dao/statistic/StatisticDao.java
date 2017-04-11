package sq.vk.dao.statistic;

import java.util.List;

import sq.vk.domain.statistic.Statistic;

/**
 * Created by Vadzim_Kavalkou on 4/7/2017.
 */
public interface StatisticDao {

    Statistic saveItem(Statistic statistic);

    List<Statistic> getAllStatistics();

    Statistic getStatisticById(Integer id);

    Statistic getStatisticByName(String name);

}
