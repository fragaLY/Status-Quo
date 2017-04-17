package sq.vk.statistic.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sq.vk.statistic.domain.Statistic;

/**
 * Created by Vadzim_Kavalkou on 4/7/2017.
 */
public interface StatisticDao extends JpaRepository<Statistic, Integer> {

    Statistic save(Statistic statistic);

    List<Statistic> findAll();

    Statistic findOne(Integer id);

    @Query("select s from Statistic s where s.name = :name")
    Optional<Statistic> findOneByName(@Param("name") String name);

    void delete(Statistic statistic);

    void delete(Integer id);

}
