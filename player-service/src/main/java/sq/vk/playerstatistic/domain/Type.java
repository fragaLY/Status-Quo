package sq.vk.playerstatistic.domain;

import java.util.Arrays;
import java.util.Optional;

/**
 * Created by Vadzim_Kavalkou on 4/17/2017.
 */
public enum Type {

  MTT("MTT"), SNG("SNG");

  private final String type;

  Type(String type) {
    this.type = type;
  }

  public String getType() {
    return type;
  }

  public static Type getClientRoleByHisRole(String _type) {

    Optional<Type> tType = Arrays.stream(values())
        .parallel()
        .filter(type -> type.getType().equals(_type))
        .findFirst();

    if (tType.isPresent()) {
      return tType.get();
    } else {
      throw new RuntimeException("Table size with '" + tType + "' was not found.");
    }

  }

}
