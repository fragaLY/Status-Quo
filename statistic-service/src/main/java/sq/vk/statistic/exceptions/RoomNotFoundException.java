package sq.vk.statistic.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Vadzim_Kavalkou on 4/10/2017.
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class RoomNotFoundException extends RuntimeException {

  public RoomNotFoundException(String message) {
    super(message);
  }

}
