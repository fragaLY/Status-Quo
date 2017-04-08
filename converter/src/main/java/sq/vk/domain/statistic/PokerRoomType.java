package sq.vk.domain.statistic;

import java.util.Arrays;
import java.util.Optional;

/**
 * Created by Vadzim Kavalkou on 08.04.2017.
 */
public enum PokerRoomType {

    POKERSTARS("POKERSTARS");

    private String pokerRoom;

    PokerRoomType(String pokerRoom) {
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
