package ma.skypay.model;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Room {
    @NotNull
    private final int roomNumber;
    @NotNull
    private RoomType type;
    @NotNull
    private int pricePerNight;

    private final Object lock = new Object();

    public Room(int roomNumber, RoomType type, int pricePerNight) {
        this.roomNumber = roomNumber;
        this.type = type;
        this.pricePerNight = pricePerNight;
    }
}