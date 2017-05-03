package sq.vk.web.controllers.client;

import java.net.URI;
import java.time.ZoneId;
import java.util.List;
import java.util.Set;
import javax.validation.Valid;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.hibernate.validator.constraints.Range;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import sq.vk.core.domain.statistic.Statistic;
import sq.vk.core.dto.client.ClientDto;
import sq.vk.core.dto.statistic.StatisticDto;
import sq.vk.service.client.ClientService;
import sq.vk.web.controllers.exceptionhandlers.errordetails.ErrorDetails;
import sq.vk.web.controllers.statistic.StatisticController;

import static java.time.LocalDateTime.now;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by Vadzim Kavalkou on 22.03.2017.
 *
 * Client's controllers.
 */
@RestController
@Api(value = "clients", description = "Client API")
@RequestMapping("/clients")
@Validated
@Secured({"ROLE_DEVELOPER", "ROLE_ADMIN", "ROLE_USER"})
public class ClientController {

  private static final Logger LOG = LoggerFactory.getLogger(ClientController.class);

  private static final ZoneId EUROPE_MOSCOW = ZoneId.of("Europe/Moscow");

  private static final String ROLE_ADMIN = "ROLE_ADMIN";
  private static final String ROLE_DEVELOPER = "ROLE_DEVELOPER";

  @Autowired
  private ClientService service;

  @Secured({ROLE_DEVELOPER, ROLE_ADMIN})
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  @ApiOperation(value = "Retrieves all clients",
                notes = "Clients will be sent in the location response",
                response = ClientDto.class)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "All clients were retrieved", response = ClientDto.class),
      @ApiResponse(code = 401, message = "Unauthorized client", response = ErrorDetails.class),
      @ApiResponse(code = 403, message = "Access denied", response = ErrorDetails.class),
      @ApiResponse(code = 404, message = "Clients were not found", response = ErrorDetails.class),
      @ApiResponse(code = 500, message = "Error getting all clients", response = ErrorDetails.class)} )
  @ResponseBody
  public ResponseEntity<List<ClientDto>> getAllClients() {

    LOG.info("Get all clients");
    final long now = now().atZone(EUROPE_MOSCOW).toInstant().toEpochMilli();

    final List<ClientDto> clients = service.findAll();

    //TODO VK: split this logic
    clients.forEach(dto -> {
      dto.add(linkTo(methodOn(ClientController.class).getAllClients()).slash(dto.getClientId()).withSelfRel());
      dto.getStatistics().forEach(statisticDto -> {
        statisticDto.add(linkTo(methodOn(StatisticController.class).getAllStatistic()).slash(statisticDto.getStatisticId()).withSelfRel());
        //statisticDto.getGameInfo().add(linkTo(methodOn()));
      });
    });

    final HttpHeaders responseHeader = new HttpHeaders();
    responseHeader.setLastModified(now);

    return new ResponseEntity<>(clients, responseHeader, HttpStatus.OK);

  }

  @Secured({ROLE_DEVELOPER, ROLE_ADMIN})
  @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  @ApiOperation(value = "Retrieves client by id",
                notes = "Client will be sent in the location response",
                response = ClientDto.class)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Client was retrieved by id", response = ClientDto.class),
      @ApiResponse(code = 401, message = "Unauthorized client", response = ErrorDetails.class),
      @ApiResponse(code = 403, message = "Access denied", response = ErrorDetails.class),
      @ApiResponse(code = 404, message = "Client was not found", response = ErrorDetails.class),
      @ApiResponse(code = 500, message = "Error getting client", response = ErrorDetails.class)} )
  @ResponseBody
  public ResponseEntity<ClientDto> getClientById(@Range(min=1) @PathVariable("id") final Integer id) {

    LOG.info("Get client by id: '{}'", id);
    final long now = now().atZone(EUROPE_MOSCOW).toInstant().toEpochMilli();

    final HttpStatus httpStatus = HttpStatus.OK;

    final ClientDto dto = service.findOne(id);

    dto.add(linkTo(methodOn(ClientController.class).getAllClients()).slash(dto.getClientId()).withSelfRel());

    dto.getStatistics().forEach(statisticDto -> {
      statisticDto.add(linkTo(methodOn(ClientController.class).getAllClients()).slash(statisticDto.getStatisticId()).withSelfRel());
      //statisticDto.getGameInfo().add(linkTo(methodOn()));
    });

    final HttpHeaders responseHeader = new HttpHeaders();
    responseHeader.setLastModified(now);

    return new ResponseEntity<>(dto, responseHeader, httpStatus);

  }

  @GetMapping(value = "/profile", produces = MediaType.APPLICATION_JSON_VALUE)
  @ApiOperation(value = "Retrieves client who was authorized",
                notes = "Client will be sent in the location response",
                response = ClientDto.class)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Current client was retrieved", response = ClientDto.class),
      @ApiResponse(code = 401, message = "Unauthorized client", response = ErrorDetails.class),
      @ApiResponse(code = 403, message = "Access denied", response = ErrorDetails.class),
      @ApiResponse(code = 404, message = "Client was not found", response = ErrorDetails.class),
      @ApiResponse(code = 500, message = "Error getting client", response = ErrorDetails.class)} )
  @ResponseBody
  public ResponseEntity<ClientDto> getClientProfile() {

    LOG.info("Get current user info.");
    final long now = now().atZone(EUROPE_MOSCOW).toInstant().toEpochMilli();

    final String email = SecurityContextHolder.getContext().getAuthentication().getName();

    final ClientDto dto = service.findOneByEmail(email);

    dto.add(linkTo(methodOn(ClientController.class).getAllClients()).slash(dto.getClientId()).withSelfRel());

    dto.getStatistics().forEach(statisticDto -> {
      statisticDto.add(linkTo(methodOn(ClientController.class).getAllClients()).slash(statisticDto.getStatisticId()).withSelfRel());
      //statisticDto.getGameInfo().add(linkTo(methodOn()));
    });

    final HttpHeaders responseHeaders = new HttpHeaders();
    responseHeaders.setLastModified(now);

    return new ResponseEntity<>(dto, responseHeaders, HttpStatus.OK);

  }

  @Secured({ROLE_DEVELOPER, ROLE_ADMIN})
  @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
  @ApiOperation(value = "Saves client",
                notes = "Client will be saved",
                response = Void.class)
  @ApiResponses(value = {
      @ApiResponse(code = 201, message = "Client was created", response = Void.class),
      @ApiResponse(code = 401, message = "Unauthorized client", response = ErrorDetails.class),
      @ApiResponse(code = 403, message = "Access denied", response = ErrorDetails.class),
      @ApiResponse(code = 500, message = "Error saving client", response = ErrorDetails.class)} )
  public ResponseEntity<?> createClient(@Valid @RequestBody final ClientDto clientDto) {

    LOG.info("Saves client [{}].", clientDto);
    final long now = now().atZone(EUROPE_MOSCOW).toInstant().toEpochMilli();

    final ClientDto client = service.save(clientDto);

    final URI createdClientUri = ServletUriComponentsBuilder.fromCurrentRequest()
                                 .path("/{id}").buildAndExpand(client.getClientId()).toUri();

    final HttpHeaders responseHeaders = new HttpHeaders();
    responseHeaders.setLocation(createdClientUri);
    responseHeaders.setLastModified(now);

    return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);

  }

  @Secured({ROLE_DEVELOPER, ROLE_ADMIN})
  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
  @ApiOperation(value = "Updates client",
      notes = "Client will be updated",
      response = Void.class)
  @ApiResponses(value = {
      @ApiResponse(code = 202, message = "Client was updated", response = Void.class),
      @ApiResponse(code = 401, message = "Unauthorized client", response = ErrorDetails.class),
      @ApiResponse(code = 403, message = "Access denied", response = ErrorDetails.class),
      @ApiResponse(code = 500, message = "Error updating client", response = ErrorDetails.class)} )
  public ResponseEntity<?> editClient(@Valid @RequestBody final ClientDto clientDto) {

    LOG.info("Saves client [{}].", clientDto);
    final long now = now().atZone(EUROPE_MOSCOW).toInstant().toEpochMilli();

    final ClientDto client = service.save(clientDto);

    final URI editedClientUri = ServletUriComponentsBuilder.fromCurrentRequest()
                                 .path("/{id}").buildAndExpand(client.getClientId()).toUri();

    final HttpHeaders responseHeaders = new HttpHeaders();
    responseHeaders.setLocation(editedClientUri);
    responseHeaders.setLastModified(now);

    return new ResponseEntity<>(null, responseHeaders, HttpStatus.OK);

  }

  @Secured({ROLE_DEVELOPER, ROLE_ADMIN})
  @DeleteMapping
  @ApiOperation(value = "Deletes client",
      notes = "Client will be deleted",
      response = Void.class)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Client was deleted", response = Void.class),
      @ApiResponse(code = 204, message = "No Content", response = ErrorDetails.class),
      @ApiResponse(code = 401, message = "Unauthorized client", response = ErrorDetails.class),
      @ApiResponse(code = 403, message = "Access denied", response = ErrorDetails.class),
      @ApiResponse(code = 500, message = "Error deleting client", response = ErrorDetails.class)} )
  public ResponseEntity<ClientDto> deleteClient(@Valid @RequestBody final ClientDto clientDto) {

    LOG.info("Deletes client [{}].", clientDto);
    final long now = now().atZone(EUROPE_MOSCOW).toInstant().toEpochMilli();

    service.delete(clientDto);

    final HttpHeaders responseHeaders = new HttpHeaders();
    responseHeaders.setLastModified(now);

    return new ResponseEntity<>(null, responseHeaders, HttpStatus.OK);

  }

  @Secured({ROLE_DEVELOPER, ROLE_ADMIN})
  @DeleteMapping(value = "/{id}")
  @ApiOperation(value = "Deletes client by id",
      notes = "Client will be deleted by id",
      response = Void.class)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Client was deleted", response = Void.class),
      @ApiResponse(code = 204, message = "No Content", response = ErrorDetails.class),
      @ApiResponse(code = 401, message = "Unauthorized client", response = ErrorDetails.class),
      @ApiResponse(code = 403, message = "Access denied", response = ErrorDetails.class),
      @ApiResponse(code = 500, message = "Error deleting client", response = ErrorDetails.class)} )
  public ResponseEntity<?> deleteClientById(@Range(min=1) @PathVariable("id") final Integer id) {

    LOG.info("Deletes client with id [{}].", id);
    final long now = now().atZone(EUROPE_MOSCOW).toInstant().toEpochMilli();

    service.delete(id);

    final HttpHeaders responseHeaders = new HttpHeaders();
    responseHeaders.setLastModified(now);

    return new ResponseEntity<>(null, responseHeaders, HttpStatus.OK);

  }

  @GetMapping(value = "profile/statistics", produces = MediaType.APPLICATION_JSON_VALUE)
  @ApiOperation(value = "Retrieves statistics who was authorized",
      notes = "Statistics will be sent in the location response",
      response = StatisticDto.class)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Current statistic was retrieved", response = Statistic.class),
      @ApiResponse(code = 401, message = "Unauthorized client", response = ErrorDetails.class),
      @ApiResponse(code = 403, message = "Access denied", response = ErrorDetails.class),
      @ApiResponse(code = 404, message = "Client was not found", response = ErrorDetails.class),
      @ApiResponse(code = 500, message = "Error getting statistics", response = ErrorDetails.class)} )
  @ResponseBody
  public ResponseEntity<Set<StatisticDto>> getClientStatistics() {

    LOG.info("Get current user's statistics.");
    final long now = now().atZone(EUROPE_MOSCOW).toInstant().toEpochMilli();

    final String email = SecurityContextHolder.getContext().getAuthentication().getName();

    final Set<StatisticDto> statistics = service.findOneByEmail(email).getStatistics();

    final HttpHeaders responseHeaders = new HttpHeaders();
    responseHeaders.setLastModified(now);

    return new ResponseEntity<>(statistics, responseHeaders, HttpStatus.OK);

  }

}
