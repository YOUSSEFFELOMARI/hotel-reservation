package ma.skypay.service;

import java.time.LocalDate;

public interface ReservationService {
    void setUser(int userId, String fullName, int balance);
    void setRoom(int roomNumber, String type, int pricePerNight);
    void bookRoom(int userId, int roomNumber, LocalDate checkIn, LocalDate checkOut);
    void printAll();
    void printAllUsers();
}