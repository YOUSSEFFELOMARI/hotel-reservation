package ma.skypay;

import ma.skypay.service.ReservationService;
import ma.skypay.service.impl.ReservationServiceImpl;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        ReservationService service = new ReservationServiceImpl();
        System.out.println("\n\n");
        service.setRoom(1, "standard", 1000);
        service.setRoom(2, "junior", 2000);
        service.setRoom(3, "master", 3000);

        service.setUser(1, "Youssef", 5000);
        service.setUser(2, "Ahmed", 10000);

        service.bookRoom(1, 2,
                LocalDate.of(2026, 6, 30),
                LocalDate.of(2026, 7, 7)
        );

        service.bookRoom(1, 2,
                LocalDate.of(2026, 7, 7),
                LocalDate.of(2026, 6, 30)
        );

        service.bookRoom(1, 1,
                LocalDate.of(2026, 7, 7),
                LocalDate.of(2026, 7, 8)
        );

        service.bookRoom(2, 1,
                LocalDate.of(2026, 7, 7),
                LocalDate.of(2026, 7, 9)
        );

        service.bookRoom(2, 3,
                LocalDate.of(2026, 7, 7),
                LocalDate.of(2026, 7, 8)
        );

        service.setRoom(1, "master", 10000);

        service.printAll();
        service.printAllUsers();
    }
}