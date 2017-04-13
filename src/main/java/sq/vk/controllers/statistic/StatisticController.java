package sq.vk.controllers.statistic;

import java.io.IOException;
import java.time.ZoneId;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import sq.vk.statistic.domain.PokerRoomType;
import sq.vk.statistic.dto.StatisticDto;
import sq.vk.statistic.service.StatisticService;

import static java.time.LocalDateTime.now;

/**
 * Created by Vadzim_Kavalkou on 4/7/2017.
 */
@RestController
@RequestMapping("/statistic")
public class StatisticController {

  private static final Logger LOG = LoggerFactory.getLogger(StatisticController.class);
  private static final ZoneId EUROPE_MOSCOW = ZoneId.of("Europe/Moscow");

  private static final String ROLE_ADMIN = "ROLE_ADMIN";
  private static final String ROLE_DEVELOPER = "ROLE_DEVELOPER";
  private static final String ROLE_USER = "ROLE_USER";

  @Autowired
  private StatisticService statisticService;

  @GetMapping(value = "/{room}/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  @Secured({ ROLE_ADMIN, ROLE_DEVELOPER, ROLE_USER })
  public ResponseEntity<StatisticDto> getStatisticOfPlayerInRoom(
    @Valid @PathVariable("room") final String room,
    @Valid @PathVariable("name") final String name) throws IOException {

    LOG.info("Get statistic by name: '{}'", name);
    final long currentTime = now().atZone(EUROPE_MOSCOW).toInstant().toEpochMilli();

    HttpStatus httpStatus = HttpStatus.OK;

    final PokerRoomType roomType = PokerRoomType.getRoomAsEnum(room.toUpperCase());

    StatisticDto dto = new StatisticDto.Builder().setName(name).setProfit(5155151).setPokerRoom(
      roomType).build();

    HttpHeaders responseHeaders = new HttpHeaders();
    responseHeaders.setLastModified(currentTime);

    return new ResponseEntity<>(dto, responseHeaders, httpStatus);
  }

}
