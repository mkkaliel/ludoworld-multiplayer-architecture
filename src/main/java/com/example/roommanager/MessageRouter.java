package com.example.roommanager;

/**
 * Simple helper for room-scoped messaging.
 * In a real WebSocket application, the endpoint would call this
 * after parsing an incoming message and determining the target room.
 */
public class MessageRouter {

    private final RoomManager roomManager;

    public MessageRouter(RoomManager roomManager) {
        if (roomManager == null) {
            throw new IllegalArgumentException("roomManager must not be null");
        }
        this.roomManager = roomManager;
    }

    public void routeToRoom(String roomId, String message) {
        if (roomId == null || roomId.isBlank()) {
            throw new IllegalArgumentException("roomId must not be null or blank");
        }
        if (message == null) {
            throw new IllegalArgumentException("message must not be null");
        }

        roomManager.broadcastToRoom(roomId, message);
    }
}