package com.example.roommanager;

import jakarta.websocket.Session;
import java.util.Objects;

/**
 * Represents a connected user in the system.
 * This class is intentionally generic and does not include
 * application-specific fields beyond basic identity/session data.
 */
public class PlayerSession {

    private final String sessionId;
    private final String playerId;
    private final String playerName;
    private final Session webSocketSession;

    public PlayerSession(String sessionId, String playerId, String playerName, Session webSocketSession) {
        this.sessionId = Objects.requireNonNull(sessionId, "sessionId must not be null");
        this.playerId = Objects.requireNonNull(playerId, "playerId must not be null");
        this.playerName = Objects.requireNonNull(playerName, "playerName must not be null");
        this.webSocketSession = webSocketSession;
    }

    public String getSessionId() {
        return sessionId;
    }

    public String getPlayerId() {
        return playerId;
    }

    public String getPlayerName() {
        return playerName;
    }

    public Session getWebSocketSession() {
        return webSocketSession;
    }

    @Override
    public String toString() {
        return "PlayerSession{" +
                "sessionId='" + sessionId + '\'' +
                ", playerId='" + playerId + '\'' +
                ", playerName='" + playerName + '\'' +
                '}';
    }
}