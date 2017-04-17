package sq.vk.playerstatistic.domain;

import java.util.Arrays;
import java.util.Optional;

/**
 * Created by Vadzim_Kavalkou on 4/17/2017.
 */
public enum Speed {

  SLOW("SLOW"), NORMAL("NORMAL"), TURBO("TURBO"), HYPERTURBO("HYPER-TURBO"), ALL("ALL");

  private final String speed;

  Speed(String speed) {
    this.speed = speed;
  }

  public static Speed getClientRoleByHisRole(String _speed) {

    Optional<Speed> speed = Arrays.stream(values())
        .parallel()
        .filter(sp -> sp.getSpeed().equals(_speed))
        .findFirst();

    if (speed.isPresent()) {
      return speed.get();
    } else {
      throw new RuntimeException("Speed with '" + speed + "' was not found.");
    }

  }

  public String getSpeed() {
    return speed;
  }
}
