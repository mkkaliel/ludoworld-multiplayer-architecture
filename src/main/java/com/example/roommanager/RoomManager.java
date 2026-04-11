package com.example.roommanager;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Central manager for room lifecycle and room membership.
 */
public class RoomManager {

    private final Map<String, Room> rooms = new ConcurrentHashMap<>();
    private RoomEventListener eventListener;

    public RoomManager() {
    }

    public RoomManager(RoomEventListener eventListener) {
        this.eventListener = eventListener;
    }

    public void setEventListener(RoomEventListener eventListener) {
        this.eventListener = eventListener;
    }

    public Room createRoom(String roomId) {
        Room room = rooms.computeIfAbsent(roomId, Room::new);
        if (eventListener != null) {
            eventListener.onRoomCreated(roomId);
        }
        return room;
    }

    public Optional<Room> getRoom(String roomId) {
        return Optional.ofNullable(rooms.get(roomId));
    }

    public Collection<Room> getAllRooms() {
        return Collections.unmodifiableCollection(rooms.values());
    }

    public boolean roomExists(String roomId) {
        return rooms.containsKey(roomId);
    }

    public boolean joinRoom(String roomId, PlayerSession playerSession) {
        Room room = rooms.computeIfAbsent(roomId, Room::new);
        boolean added = room.addPlayer(playerSession);

        if (added && eventListener != null) {
            eventListener.onPlayerJoined(roomId, playerSession);
        }

        return added;
    }

    public boolean leaveRoom(String roomId, String sessionId) {
        Room room = rooms.get(roomId);
        if (room == null) {
            return false;
        }

        PlayerSession removed = room.removePlayer(sessionId);
        if (removed == null) {
            return false;
        }

        if (eventListener != null) {
            eventListener.onPlayerLeft(roomId, removed);
        }

        if (room.isEmpty()) {
            rooms.remove(roomId);
            if (eventListener != null) {
                eventListener.onRoomRemoved(roomId);
            }
        }

        return true;
    }

    public void removeSessionFromAllRooms(String sessionId) {
        for (Room room : rooms.values()) {
            leaveRoom(room.getRoomId(), sessionId);
        }
    }

    public void broadcastToRoom(String roomId, String message) {
        Room room = rooms.get(roomId);
        if (room == null) {
            return;
        }

        room.broadcast(message);

        if (eventListener != null) {
            eventListener.onMessageBroadcast(roomId, message);
        }
    }

    public int getRoomCount() {
        return rooms.size();
    }
}