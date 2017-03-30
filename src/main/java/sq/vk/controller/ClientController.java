package sq.vk.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import sq.vk.dto.Client.ClientDto;
import sq.vk.service.client.ClientService;

import javax.validation.Valid;
import java.util.List;

import static java.time.LocalDateTime.now;
import static java.time.ZoneId.systemDefault;

/**
 * Created by Vadzim Kavalkou on 22.03.2017.
 */
@RestController
@RequestMapping("/client")
@Secured({"ROLE_ADMIN"})
public class ClientController {

    private static final Logger LOG = LoggerFactory.getLogger(ClientController.class);

    @Autowired
    private ClientService clientService;

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<List<ClientDto>> getAllClients() {
        LOG.info("Get all clients");

        final List<ClientDto> allClients = clientService.getAllClients();
        final long currentTime = now().atZone(systemDefault()).toInstant().toEpochMilli();

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setLastModified(currentTime);

        return new ResponseEntity<>(allClients, responseHeaders, HttpStatus.OK);
    }

    @GetMapping(value = "/{email}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ClientDto getClientByEmail(@Valid @PathVariable String email) {
        LOG.info("Get client by email: '{}'", email);

        return clientService.getClientByEmail(email);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<ClientDto> getClientById(@Valid @PathVariable Integer id) {
        LOG.info("Get client by id: '{}'", id);

        final ClientDto client = clientService.getClientById(id);
        final long currentTime = now().atZone(systemDefault()).toInstant().toEpochMilli();

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setLastModified(currentTime);

        return new ResponseEntity<>(client, responseHeaders, HttpStatus.OK);
    }
}
