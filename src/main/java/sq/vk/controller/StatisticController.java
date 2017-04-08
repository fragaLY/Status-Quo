package sq.vk.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sq.vk.domain.statistic.PokerRoomType;
import sq.vk.dto.statistic.StatisticDto;
import sq.vk.service.statistic.StatisticService;

import javax.validation.Valid;
import java.io.IOException;
import java.time.ZoneId;

import static java.time.LocalDateTime.now;

/**
 * Created by Vadzim_Kavalkou on 4/7/2017.
 */
@RestController
@RequestMapping("/statistic")
public class StatisticController {

    private static final Logger LOG = LoggerFactory.getLogger(StatisticController.class);
    private static final ZoneId EUROPE_MOSCOW = ZoneId.of("Europe/Moscow");

    @Autowired
    private StatisticService statisticService;

    @GetMapping(value = "/{room}/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<StatisticDto> getStatisticOfPlayerInRoom(@Valid @PathVariable("room") String room, @Valid @PathVariable("name") String name)
            throws IOException {

        LOG.info("Get statistic by name: '{}'", name);
        final long currentTime = now().atZone(EUROPE_MOSCOW).toInstant().toEpochMilli();

        HttpStatus httpStatus = HttpStatus.OK;

        final PokerRoomType roomType = PokerRoomType.getRoomAsEnum(room.toUpperCase());

        double profit = statisticService.getTotalProfit(name, roomType);

        StatisticDto dto = new StatisticDto.Builder().setName(name).setProfit(profit).setPokerRoom(roomType).build();

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setLastModified(currentTime);

        return new ResponseEntity<>(dto, responseHeaders, httpStatus);
    }

}
