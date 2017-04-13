package sq.vk.controllers;

import java.net.URI;
import java.time.ZoneId;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import sq.vk.client.dto.ClientDto;
import sq.vk.client.service.ClientService;

import static java.time.LocalDateTime.now;

/**
 * Created by Vadzim Kavalkou on 22.03.2017.
 */
@RestController
@RequestMapping("/client")
public class ClientController {

  private static final Logger LOG = LoggerFactory.getLogger(ClientController.class);
  private static final ZoneId EUROPE_MOSCOW = ZoneId.of("Europe/Moscow");

  private static final String ROLE_ADMIN = "ROLE_ADMIN";
  private static final String ROLE_DEVELOPER = "ROLE_DEVELOPER";
  private static final String ROLE_USER = "ROLE_USER";

  @Autowired
  private ClientService service;

  @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  @Secured(ROLE_ADMIN)
  public ResponseEntity<List<ClientDto>> getAllClients() {
    LOG.info("Get all clients");

    final List<ClientDto> allClients = service.findAll();
    final long currentTime = now().atZone(EUROPE_MOSCOW).toInstant().toEpochMilli();

    HttpHeaders responseHeaders = new HttpHeaders();
    responseHeaders.setLastModified(currentTime);

    return new ResponseEntity<>(allClients, responseHeaders, HttpStatus.OK);

  }

  @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public ResponseEntity<ClientDto> getClientById(@PathVariable("id") final Integer id) {
    LOG.info("Get client by id: '{}'", id);

    final HttpStatus httpStatus = HttpStatus.OK;

    final ClientDto client = service.findOne(id);
    final long currentTime = now().atZone(EUROPE_MOSCOW).toInstant().toEpochMilli();

    HttpHeaders responseHeaders = new HttpHeaders();
    responseHeaders.setLastModified(currentTime);

    return new ResponseEntity<>(client, responseHeaders, httpStatus);

  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public ResponseEntity<ClientDto> getClientById(@RequestParam(value="email") final String email) {
    LOG.info("Get client by email: '{}'", email);

    final HttpStatus httpStatus = HttpStatus.OK;

    final ClientDto client = service.findOneByEmail(email);
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
    final ClientDto currentClient = service.findOneByEmail(email);

    final long currentTime = now().atZone(EUROPE_MOSCOW).toInstant().toEpochMilli();

    HttpHeaders responseHeaders = new HttpHeaders();
    responseHeaders.setLastModified(currentTime);

    return new ResponseEntity<>(currentClient, responseHeaders, HttpStatus.OK);

  }

  @PostMapping(value = "/create")
  public ResponseEntity<?> createClient(@RequestBody final ClientDto clientDto) {

    LOG.info("Saves client [{}].", clientDto);

    final ClientDto client = service.save(clientDto);
    final long currentTime = now().atZone(EUROPE_MOSCOW).toInstant().toEpochMilli();

    final HttpStatus status = HttpStatus.CREATED;

    final URI createdClientUri = ServletUriComponentsBuilder.fromCurrentRequest().path(
      "/{id}").buildAndExpand(client.getId()).toUri();

    final HttpHeaders responseHeaders = new HttpHeaders();

    responseHeaders.setLastModified(currentTime);
    responseHeaders.setLocation(createdClientUri);

    return new ResponseEntity<>(null, responseHeaders, status);

  }

  @DeleteMapping(value = "/delete")
  public ResponseEntity<?> deleteClient(@RequestBody final ClientDto clientDto) {

    LOG.info("Saves client [{}].", clientDto);

    service.delete(clientDto);
    final long currentTime = now().atZone(EUROPE_MOSCOW).toInstant().toEpochMilli();

    final HttpStatus status = HttpStatus.ACCEPTED;

    final HttpHeaders responseHeaders = new HttpHeaders();
    responseHeaders.setLastModified(currentTime);

    return new ResponseEntity<>(null, responseHeaders, status);

  }

  @DeleteMapping(value = "/delete/{email}")
  public ResponseEntity<?> deleteClient(@PathVariable("email") final String email) {

    LOG.info("Saves client with email [{}].", email);

    service.delete(email);
    final long currentTime = now().atZone(EUROPE_MOSCOW).toInstant().toEpochMilli();

    final HttpStatus status = HttpStatus.ACCEPTED;

    final HttpHeaders responseHeaders = new HttpHeaders();
    responseHeaders.setLastModified(currentTime);

    return new ResponseEntity<>(null, responseHeaders, status);

  }

  @DeleteMapping(value = "/delete/{id}")
  public ResponseEntity<?> deleteClient(@PathVariable("id") final Integer id) {

    LOG.info("Saves client with id [{}].", id);

    service.delete(id);
    final long currentTime = now().atZone(EUROPE_MOSCOW).toInstant().toEpochMilli();

    final HttpStatus status = HttpStatus.ACCEPTED;

    final HttpHeaders responseHeaders = new HttpHeaders();
    responseHeaders.setLastModified(currentTime);

    return new ResponseEntity<>(null, responseHeaders, status);

  }

}
