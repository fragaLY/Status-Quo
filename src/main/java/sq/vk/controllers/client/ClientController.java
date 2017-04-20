package sq.vk.controllers.client;

import java.net.URI;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Pattern;

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
import sq.vk.controllers.exceptionhandlers.errordetails.ErrorDetails;
import sq.vk.core.dto.client.ClientDto;
import sq.vk.service.client.ClientService;

/**
 * Created by Vadzim Kavalkou on 22.03.2017.
 */
@RestController
@Api(value = "clients", description = "Client API")
@RequestMapping("/clients")
@Validated
public class ClientController {

  private static final Logger LOG = LoggerFactory.getLogger(ClientController.class);

  private static final String ROLE_ADMIN = "ROLE_ADMIN";
  private static final String ROLE_DEVELOPER = "ROLE_DEVELOPER";
  private static final String ROLE_USER = "ROLE_USER";

  @Autowired
  private ClientService service;

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

    final List<ClientDto> allClients = service.findAll();

    return new ResponseEntity<>(allClients, new HttpHeaders(), HttpStatus.OK);

  }

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

    final HttpStatus httpStatus = HttpStatus.OK;

    final ClientDto client = service.findOne(id);

    return new ResponseEntity<>(client, new HttpHeaders(), httpStatus);

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

    final String email = SecurityContextHolder.getContext().getAuthentication().getName();

    final ClientDto currentClient = service.findOneByEmail(email);

    return new ResponseEntity<>(currentClient, new HttpHeaders(), HttpStatus.OK);

  }

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

    final ClientDto client = service.save(clientDto);

    final URI createdClientUri = ServletUriComponentsBuilder.fromCurrentRequest()
                                 .path("/{id}").buildAndExpand(client.getId()).toUri();

    final HttpHeaders responseHeaders = new HttpHeaders();
    responseHeaders.setLocation(createdClientUri);

    return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);

  }

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

    final ClientDto client = service.save(clientDto);

    final URI createdClientUri = ServletUriComponentsBuilder.fromCurrentRequest()
                                 .path("/{id}").buildAndExpand(client.getId()).toUri();

    final HttpHeaders responseHeaders = new HttpHeaders();
    responseHeaders.setLocation(createdClientUri);

    return new ResponseEntity<>(null, responseHeaders, HttpStatus.OK);

  }

  @DeleteMapping
  @ApiOperation(value = "Deletes client",
      notes = "Client will be deleted",
      response = Void.class)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Client was deleted", response = Void.class),
      @ApiResponse(code = 204, message = "No Content", response = ErrorDetails.class),
      @ApiResponse(code = 401, message = "Unauthorized client", response = ErrorDetails.class),
      @ApiResponse(code = 403, message = "Access denied", response = ErrorDetails.class),
      @ApiResponse(code = 500, message = "Error updating client", response = ErrorDetails.class)} )
  public ResponseEntity<ClientDto> deleteClient(@Valid @RequestBody final ClientDto clientDto) {

    LOG.info("Deletes client [{}].", clientDto);

    service.delete(clientDto);

    return new ResponseEntity<>(null, new HttpHeaders(), HttpStatus.OK);

  }

  @DeleteMapping(value = "/{id}")
  @ApiOperation(value = "Deletes client by id",
      notes = "Client will be deleted by id",
      response = Void.class)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Client was deleted", response = Void.class),
      @ApiResponse(code = 204, message = "No Content", response = ErrorDetails.class),
      @ApiResponse(code = 401, message = "Unauthorized client", response = ErrorDetails.class),
      @ApiResponse(code = 403, message = "Access denied", response = ErrorDetails.class),
      @ApiResponse(code = 500, message = "Error updating client", response = ErrorDetails.class)} )
  public ResponseEntity<?> deleteClientById(
    @Pattern(regexp = "[1-9]+") @PathVariable("id") final Integer id) {

    LOG.info("Deletes client with id [{}].", id);

    service.delete(id);

    return new ResponseEntity<>(null, new HttpHeaders(), HttpStatus.OK);

  }

}
