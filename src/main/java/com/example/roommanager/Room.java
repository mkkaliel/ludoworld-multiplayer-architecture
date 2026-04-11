package com.example.roommanager;

import jakarta.websocket.Session;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Represents a room that contains connected players.
 */
public class Room {

    private final String roomId;
    private final Map<String, PlayerSession> playersBySessionId = new ConcurrentHashMap<>();

    public Room(String roomId) {
        if (roomId == null || roomId.isBlank()) {
            throw new IllegalArgumentException("roomId must not be null or blank");
        }
        this.roomId = roomId;
    }

    public String getRoomId() {
        return roomId;
    }

    public boolean addPlayer(PlayerSession playerSession) {
        if (playerSession == null) {
            return false;
        }
        return playersBySessionId.putIfAbsent(playerSession.getSessionId(), playerSession) == null;
    }

    public PlayerSession removePlayer(String sessionId) {
        if (sessionId == null) {
            return null;
        }
        return playersBySessionId.remove(sessionId);
    }

    public boolean containsSession(String sessionId) {
        return playersBySessionId.containsKey(sessionId);
    }

    public int size() {
        return playersBySessionId.size();
    }

    public boolean isEmpty() {
        return playersBySessionId.isEmpty();
    }

    public Collection<PlayerSession> getPlayers() {
        return Collections.unmodifiableCollection(playersBySessionId.values());
    }

    public void broadcast(String message) {
        for (PlayerSession player : playersBySessionId.values()) {
            Session ws = player.getWebSocketSession();
            if (ws == null) {
                continue;
            }
            if (!ws.isOpen()) {
                continue;
            }

            try {
                ws.getBasicRemote().sendText(message);
            } catch (IOException e) {
                System.err.println("Failed to send message to session " + player.getSessionId() + ": " + e.getMessage());
            }
        }
    }
}