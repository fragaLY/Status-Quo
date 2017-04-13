package sq.vk.client.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sq.vk.client.domain.Client;

/**
 * Created by Vadzim Kavalkou on 22.03.2017.
 */
public interface ClientDao extends JpaRepository<Client, Integer> {

    List<Client> findAll();

    @Query("select c from Client c where c.email = :email")
    Client findOneByEmail(@Param("email") String email);

    Client findOne(Integer id);

    Client save(Client client);

    void delete(Client client);

    void delete(Integer id);

    @Query("delete from Client c where c.email = :email")
    void deleteByEmail(@Param("email")String email);
}
