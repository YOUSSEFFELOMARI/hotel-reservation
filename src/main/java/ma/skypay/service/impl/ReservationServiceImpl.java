package ma.skypay.service.impl;

import ma.skypay.model.*;
import ma.skypay.service.ReservationService;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class ReservationServiceImpl implements ReservationService {

    private final Map<Integer, User> users = new ConcurrentHashMap<>();
    private final Map<Integer, Room> rooms = new ConcurrentHashMap<>();
    private final List<Booking> bookings = Collections.synchronizedList(new ArrayList<>());

    @Override
    public void setUser(int userId, String fullName, int balance) {
        users.putIfAbsent(userId, new User(userId, fullName, balance));
    }

    @Override
    public void setRoom(int roomNumber, String type, int pricePerNight) {
        RoomType roomType = RoomType.valueOf(type.toUpperCase());
        rooms.compute(roomNumber, (key, existingRoom) -> {
            if (existingRoom == null) {
                return new Room(roomNumber, roomType, pricePerNight);
            } else {
                existingRoom.setPricePerNight(pricePerNight);
                existingRoom.setType(roomType);
                return existingRoom;
            }
        });
    }

    @Override
    public void bookRoom(int userId, int roomNumber, LocalDate checkIn, LocalDate checkOut) {
        if (checkIn == null || checkOut == null || !checkIn.isBefore(checkOut)) {
            System.out.println("Invalid dates for booking.");
            return;
        }

        User user = users.get(userId);
        Room room = rooms.get(roomNumber);

        if (user == null || room == null) {
            System.out.println("User or room not found.");
            return;
        }

        synchronized (room.getLock()) {
            boolean isAvailable = bookings.stream().noneMatch(booking ->
                    booking.getRoomNumber() == roomNumber &&
                            !(checkOut.isBefore(booking.getCheckIn()) || checkIn.isAfter(booking.getCheckOut()))
            );

            if (!isAvailable) {
                System.out.println("Room " + roomNumber + " is not available for the selected dates.");
                return;
            }

            int totalDays = (int) (checkOut.toEpochDay() - checkIn.toEpochDay());
            int totalCost = totalDays * room.getPricePerNight();

            if (user.getBalance() < totalCost) {
                System.out.println("User " + user.getFullName() + " does not have enough balance.");
                return;
            }

            user.setBalance(user.getBalance() - totalCost);

            Booking booking = new Booking(
                    userId,
                    user.getFullName(),
                    roomNumber,
                    room.getType(),
                    room.getPricePerNight(),
                    checkIn,
                    checkOut,
                    totalCost
            );

            bookings.add(booking);
            System.out.println("Booking confirmed for user " + user.getFullName() +
                    " in room " + roomNumber + " from " + checkIn + " to " + checkOut);
        }
    }

    @Override
    public void printAll() {
        System.out.println("All Rooms and Bookings :");

        bookings.stream()
                .sorted(Comparator.comparing(Booking::getCheckIn).reversed())
                .forEach(b -> {
                    System.out.printf("Room %d [%s]: Booked by %s from %s to %s — %d MAD\n",
                            b.getRoomNumber(),
                            b.getRoomType(),
                            b.getUserName(),
                            b.getCheckIn(),
                            b.getCheckOut(),
                            b.getTotalPrice());
                });
    }

    @Override
    public void printAllUsers() {
        System.out.println("All Users :");

        users.values().stream()
                .sorted(Comparator.comparing(User::getId).reversed())
                .forEach(u -> System.out.printf("ID: %d, Name: %s, Balance: %d MAD\n",
                        u.getId(), u.getFullName(), u.getBalance()));
    }
}