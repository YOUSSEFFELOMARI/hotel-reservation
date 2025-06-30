package ma.skypay.model;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class Booking {
    @NotNull
    private final int userId;
    @NotNull
    private final String userName;
    @NotNull
    private final int roomNumber;
    @NotNull
    private final RoomType roomType;
    @NotNull
    private final int pricePerNightAtBooking;
    @NotNull
    private final LocalDate checkIn;
    @NotNull
    private final LocalDate checkOut;
    @NotNull
    private final int totalPrice;
}