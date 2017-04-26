package sq.vk.core.domain.gameinfo;

import java.util.Arrays;
import java.util.Optional;

/**
 * Created by Vadzim_Kavalkou on 4/17/2017.
 *
 * All sizes of table.
 */
public enum TableSize {

  HEADSUP("HEADS-UP"), SHORTHANDED("SHORT-HANDED"), FULLRING("FULL-RING"), ALL("ALL");

  private final String size;

  TableSize(String tableSize) {
    this.size = tableSize;
  }

  public String getSize() {
    return size;
  }

  public static TableSize getClientRoleByHisRole(String tableSize) {

    Optional<TableSize> tSize = Arrays.stream(values())
        .parallel()
        .filter(size -> size.getSize().equals(tableSize))
        .findFirst();

    if (tSize.isPresent()) {
      return tSize.get();
    } else {
      throw new RuntimeException("Table size with '" + tSize + "' was not found.");
    }

  }

}
