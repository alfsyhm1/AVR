package com.csc3402.lab.avr.service;

import com.csc3402.lab.avr.model.Room;

import java.util.List;
import java.util.Optional;

public interface RoomService {
    List<Room> getAllRooms();
    Optional<Room> getRoomById(Integer id);
    Room saveRoom(Room room);
    void deleteRoom(Integer id);
}
