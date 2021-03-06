package sq.vk.core.exceptions.statistic;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Vadzim_Kavalkou on 4/10/2017.
 *
 * StatisticNotFoundException.class
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class StatisticNotFoundException extends RuntimeException {

  public StatisticNotFoundException(String message) {
    super(message);
  }

}
