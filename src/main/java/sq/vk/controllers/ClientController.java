package sq.vk.controllers;

import java.time.ZoneId;
import java.util.List;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import sq.vk.dto.client.ClientDto;
import sq.vk.service.client.ClientService;

import static java.time.LocalDateTime.now;

/**
 * Created by Vadzim Kavalkou on 22.03.2017.
 */
@RestController
@RequestMapping("/client")
public class ClientController {

    private static final Logger LOG = LoggerFactory.getLogger(ClientController.class);
    private static final ZoneId EUROPE_MOSCOW = ZoneId.of("Europe/Moscow");

    @Autowired
    private ClientService clientService;

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @Secured("ROLE_ADMIN")
    public ResponseEntity<List<ClientDto>> getAllClients() {
        LOG.info("Get all clients");

        final List<ClientDto> allClients = clientService.getAllClients();
        final long currentTime = now().atZone(EUROPE_MOSCOW).toInstant().toEpochMilli();

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setLastModified(currentTime);

        return new ResponseEntity<>(allClients, responseHeaders, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @Secured("ROLE_ADMIN")
    public ResponseEntity<ClientDto> getClientById(@Valid @PathVariable("id") int id) {
        LOG.info("Get client by id: '{}'", id);

        HttpStatus httpStatus = HttpStatus.OK;

        final ClientDto client = clientService.getClientById(id);
        final long currentTime = now().atZone(EUROPE_MOSCOW).toInstant().toEpochMilli();

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setLastModified(currentTime);

        return new ResponseEntity<>(client, responseHeaders, httpStatus);
    }

    @GetMapping(value = "/profile", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<ClientDto> getClientProfile() {
        LOG.info("Get current user info.");

        final String email = SecurityContextHolder.getContext().getAuthentication().getName();
        final ClientDto currentClient = clientService.getClientByEmail(email);

        final long currentTime = now().atZone(EUROPE_MOSCOW).toInstant().toEpochMilli();

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setLastModified(currentTime);

        return new ResponseEntity<>(currentClient, responseHeaders, HttpStatus.OK);
    }


}
