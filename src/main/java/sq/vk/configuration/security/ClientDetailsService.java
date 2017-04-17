package sq.vk.configuration.security;

import java.util.List;
import java.util.Optional;

import com.google.common.collect.ImmutableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import sq.vk.client.dto.ClientDto;
import sq.vk.client.service.ClientService;

/**
 * Created by Vadzim Kavalkou on 22.03.2017.
 */
@Service("ClientDetailsService")
public class ClientDetailsService implements UserDetailsService {

    private static final Logger LOG = LoggerFactory.getLogger(ClientDetailsService.class);

    @Autowired
    private ClientService clientService;

    public ClientDetailsService(){

    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Optional<ClientDto> optionalClientDto = Optional.ofNullable(clientService.findOneByEmail(email));

        if (optionalClientDto.isPresent()) {

            ClientDto clientDto = optionalClientDto.get();
            String clientRole = clientDto.getRole().getClientRole();

            List<GrantedAuthority> authorities = ImmutableList.of(new SimpleGrantedAuthority(clientRole));

            LOG.info("Client with email [ {} ] has [ {} ].", email, clientRole);
            return new User(email, clientDto.getPassword(), true, true, true, true, authorities);

        }

        LOG.info("Client with email [ {} ] has ROLE_ANONYMOUS.", email);
        String output = String.format("Client with email ' %s ' was not found. Grants ROLE_ANONYMOUS.", email);

        throw new UsernameNotFoundException(output);
    }
}