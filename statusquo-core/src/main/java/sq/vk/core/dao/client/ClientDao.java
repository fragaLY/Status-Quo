package sq.vk.core.dao.client;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sq.vk.core.domain.client.Client;

/**
 * Created by Vadzim Kavalkou on 22.03.2017.
 *
 * ClientDao interface.
 */
public interface ClientDao extends JpaRepository<Client, Integer> {

    List<Client> findAll();

    @Query("select c from Client c where c.email = :email")
    Optional<Client> findOneByEmail(@Param("email") String email);

    Client findOne(Integer id);

    @SuppressWarnings("unchecked")
    Client save(Client client);

    void delete(Client client);

    void delete(Integer id);

    @Query("delete from Client c where c.email = :email")
    void deleteByEmail(@Param("email") String email);
}
