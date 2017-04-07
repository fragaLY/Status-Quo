package sq.vk.controller;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import sq.vk.dto.item.ItemDto;
import sq.vk.service.item.ItemService;

import static java.time.LocalDateTime.now;

/**
 * Created by Vadzim_Kavalkou on 4/7/2017.
 */
@RestController
@RequestMapping("/lamoda")
public class ItemController {

  private static final Logger LOG = LoggerFactory.getLogger(ClientController.class);
  private static final ZoneId EUROPE_MOSCOW = ZoneId.of("Europe/Moscow");

  @Autowired
  private ItemService itemService;

  @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public ResponseEntity<ItemDto> getItemByItemId(@Valid @PathVariable("id") String id)
      throws IOException {
    LOG.info("Get item by id: '{}'", id);

    HttpStatus httpStatus = HttpStatus.OK;
  
    final long currentTime = now().atZone(EUROPE_MOSCOW).toInstant().toEpochMilli();

    ItemDto item = itemService.getItemByItemId(id);

    HttpHeaders responseHeaders = new HttpHeaders();
    responseHeaders.setLastModified(currentTime);
  
    return new ResponseEntity<>(item, responseHeaders, httpStatus);
  }
}
