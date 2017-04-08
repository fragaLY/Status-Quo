package sq.vk.dao.statistic;

import sq.vk.domain.statistic.Statistic;

import java.util.List;

/**
 * Created by Vadzim_Kavalkou on 4/7/2017.
 */
public interface StatisticDao {

    Statistic saveItem(Statistic statistic);

    List<Statistic> getAllItems();

    Statistic getItemById(Integer id);

    Statistic getItemByName(String name);

}
