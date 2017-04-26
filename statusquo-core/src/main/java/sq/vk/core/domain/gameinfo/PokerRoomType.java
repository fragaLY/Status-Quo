package sq.vk.core.domain.gameinfo;

import java.util.Arrays;
import java.util.Optional;

/**
 * Created by Vadzim Kavalkou on 08.04.2017.
 *
 * All sites we can choose.
 */
public enum PokerRoomType {

    POKERSTARS("POKERSTARS"),EIGHTS("888");

    private final String pokerRoom;

    PokerRoomType(final String pokerRoom) {
        this.pokerRoom = pokerRoom;
    }

    public static PokerRoomType getRoomAsEnum(String roomType_) {

        Optional<PokerRoomType> room = Arrays.stream(values())
                                       .parallel()
                                       .filter(clientRole -> clientRole.getValue().equals(roomType_))
                                       .findFirst();

        if (room.isPresent()) {
            return room.get();
        } else {
            throw new RuntimeException("PokerRoom: '" + roomType_ + "' was not found.");
        }

    }

    public String getValue() {
        return pokerRoom;
    }

}
