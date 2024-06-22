package com.csc3402.lab.avr.repository;

import com.csc3402.lab.avr.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Integer> {
    Room findByRoomType(String roomType);
    // Additional custom query methods can be defined here if needed
}
