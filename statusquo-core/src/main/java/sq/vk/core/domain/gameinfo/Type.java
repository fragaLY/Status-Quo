package sq.vk.core.domain.gameinfo;

import java.util.Arrays;
import java.util.Optional;

/**
 * Created by Vadzim_Kavalkou on 4/17/2017.
 */
public enum Type {

  MTT("MTT"), SNG("SNG"), ALL("ALL");

  private final String type;

  Type(String type) {
    this.type = type;
  }

  public String getType() {
    return type;
  }

  public static Type getClientRoleByHisRole(String _type) {

    Optional<Type> type = Arrays.stream(values())
        .parallel()
        .filter(t -> t.getType().equals(_type))
        .findFirst();

    if (type.isPresent()) {
      return type.get();
    } else {
      throw new RuntimeException("Table size with '" + type + "' was not found.");
    }

  }

}
