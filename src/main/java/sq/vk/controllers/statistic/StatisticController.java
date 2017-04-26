package sq.vk.controllers.statistic;

import java.net.URI;
import java.time.ZoneId;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Pattern;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import sq.vk.controllers.exceptionhandlers.errordetails.ErrorDetails;
import sq.vk.core.dto.client.ClientDto;
import sq.vk.core.dto.statistic.StatisticDto;
import sq.vk.service.statistic.StatisticService;

/**
 * Created by Vadzim_Kavalkou on 4/7/2017.
 */
@RestController
@Api(value = "statistics", description = "Statistic API")
@RequestMapping("/statistics")
@Validated
public class StatisticController {

  private static final Logger LOG = LoggerFactory.getLogger(StatisticController.class);
  private static final ZoneId EUROPE_MOSCOW = ZoneId.of("Europe/Moscow");

  private static final String ROLE_ADMIN = "ROLE_ADMIN";
  private static final String ROLE_DEVELOPER = "ROLE_DEVELOPER";
  private static final String ROLE_USER = "ROLE_USER";

  @Autowired
  private StatisticService service;

  @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  @ApiOperation(value = "Retrieves statistic by id",
      notes = "Statistic will be sent in the location response",
      response = ClientDto.class)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Statistic was retrieved by id", response = StatisticDto.class),
      @ApiResponse(code = 401, message = "Unauthorized client", response = ErrorDetails.class),
      @ApiResponse(code = 403, message = "Access denied", response = ErrorDetails.class),
      @ApiResponse(code = 404, message = "Statistic was not found", response = ErrorDetails.class),
      @ApiResponse(code = 500, message = "Error getting statistic", response = ErrorDetails.class)} )
  @ResponseBody
  public ResponseEntity<StatisticDto> getStatisticById(@Pattern(regexp = "[1-9]+") @PathVariable("id") final Integer id) {

    LOG.info("Get statistic by id {}", id);

    StatisticDto statistic = service.findOne(id);

    return new ResponseEntity<>(statistic, new HttpHeaders(), HttpStatus.OK);

  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  @ApiOperation(value = "Retrieves all statistics",
      notes = "Clients will be sent in the location response",
      response = StatisticDto.class)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "All statistics were retrieved", response = StatisticDto.class),
      @ApiResponse(code = 401, message = "Unauthorized client", response = ErrorDetails.class),
      @ApiResponse(code = 403, message = "Access denied", response = ErrorDetails.class),
      @ApiResponse(code = 404, message = "Statistics were not found", response = ErrorDetails.class),
      @ApiResponse(code = 500, message = "Error getting all statistics", response = ErrorDetails.class)} )
  @ResponseBody
  public ResponseEntity<List<StatisticDto>> getAllStatistic() {

    LOG.info("Get all statistic");

    List<StatisticDto> statistics = service.findAll();

    return new ResponseEntity<>(statistics, new HttpHeaders(), HttpStatus.OK);

  }

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
  @ApiOperation(value = "Updates statistic",
      notes = "Statistic will be updated",
      response = Void.class)
  @ApiResponses(value = {
      @ApiResponse(code = 202, message = "Statistic was updated", response = Void.class),
      @ApiResponse(code = 401, message = "Unauthorized client", response = ErrorDetails.class),
      @ApiResponse(code = 403, message = "Access denied", response = ErrorDetails.class),
      @ApiResponse(code = 500, message = "Error updating statistic", response = ErrorDetails.class)} )
  public ResponseEntity<?> editStatistic(@Valid @RequestBody final StatisticDto statistic) {

    LOG.info("Update statistic {}", statistic);

    service.save(statistic);

    final URI createdClientUri = ServletUriComponentsBuilder.fromCurrentRequest().path(
      "/{id}").buildAndExpand(statistic.getStatisticId()).toUri();

    final HttpHeaders responseHeaders = new HttpHeaders();
    responseHeaders.setLocation(createdClientUri);

    return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);

  }

  @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
  @ApiOperation(value = "Saves statistics",
      notes = "Statistic will be saved",
      response = Void.class)
  @ApiResponses(value = {
      @ApiResponse(code = 201, message = "Statistic was created", response = Void.class),
      @ApiResponse(code = 401, message = "Unauthorized client", response = ErrorDetails.class),
      @ApiResponse(code = 403, message = "Access denied", response = ErrorDetails.class),
      @ApiResponse(code = 500, message = "Error saving statistic", response = ErrorDetails.class)} )
  public ResponseEntity<?> createStatistic(@Valid @RequestBody final StatisticDto statistic) {

    LOG.info("Create statistic '{}'", statistic);

    service.save(statistic);

    final URI createdClientUri = ServletUriComponentsBuilder.fromCurrentRequest().path(
      "/{id}").buildAndExpand(statistic.getStatisticId()).toUri();

    final HttpHeaders responseHeaders = new HttpHeaders();
    responseHeaders.setLocation(createdClientUri);

    return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);

  }

  @DeleteMapping
  @ApiOperation(value = "Deletes statistic",
      notes = "Statistic will be deleted",
      response = Void.class)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Statistic was deleted", response = Void.class),
      @ApiResponse(code = 204, message = "No Content", response = ErrorDetails.class),
      @ApiResponse(code = 401, message = "Unauthorized client", response = ErrorDetails.class),
      @ApiResponse(code = 403, message = "Access denied", response = ErrorDetails.class),
      @ApiResponse(code = 500, message = "Error deleting statistic", response = ErrorDetails.class)} )
  public ResponseEntity<?> deleteStatistic(@Valid @RequestBody final StatisticDto statistic) {

    LOG.info("Delete statistic '{}'", statistic);

    service.delete(statistic);

    return new ResponseEntity<>(null, new HttpHeaders(), HttpStatus.OK);

  }

  @DeleteMapping(value = "/{id}")
  @ApiOperation(value = "Deletes statistic by id",
      notes = "Statistic will be deleted by id",
      response = Void.class)
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Statistic was deleted", response = Void.class),
      @ApiResponse(code = 204, message = "No Content", response = ErrorDetails.class),
      @ApiResponse(code = 401, message = "Unauthorized client", response = ErrorDetails.class),
      @ApiResponse(code = 403, message = "Access denied", response = ErrorDetails.class),
      @ApiResponse(code = 500, message = "Error deleting statistic", response = ErrorDetails.class)} )
  public ResponseEntity<?> deleteStatisticById(@Pattern(regexp = "[1-9]+") @RequestParam("id") final Integer id) {

    LOG.info("Delete statistic with id '{}'", id);

    service.delete(id);

    return new ResponseEntity<>(null, new HttpHeaders(), HttpStatus.OK);

  }

}
