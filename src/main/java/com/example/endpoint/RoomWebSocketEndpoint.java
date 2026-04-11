package com.example.endpoint;

import com.example.roommanager.PlayerSession;
import com.example.roommanager.RoomEventListener;
import com.example.roommanager.RoomManager;
import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * A simple Jakarta WebSocket endpoint that uses the room manager module.
 *
 * Example connection URL:
 * ws://localhost:8080/yourapp/ws/room?roomId=room-1&playerId=u1&playerName=Alice
 */
@ServerEndpoint("/ws/room")
public class RoomWebSocketEndpoint {

    /**
     * Shared room manager for all endpoint instances.
     * In a larger app, this could be injected through a configurator or app context.
     */
    private static final RoomManager ROOM_MANAGER = new RoomManager(new LoggingRoomListener());

    public RoomWebSocketEndpoint() {
        // Jakarta WebSocket endpoints require a public no-arg constructor.
    }

    @OnOpen
    public void onOpen(Session session) throws IOException {
        Map<String, List<String>> params = session.getRequestParameterMap();

        String roomId = getFirst(params, "roomId");
        String playerId = getFirst(params, "playerId");
        String playerName = getFirst(params, "playerName");

        if (isBlank(roomId) || isBlank(playerId) || isBlank(playerName)) {
            session.close(new CloseReason(
                    CloseReason.CloseCodes.CANNOT_ACCEPT,
                    "Missing required query params: roomId, playerId, playerName"
            ));
            return;
        }

        PlayerSession playerSession = new PlayerSession(
                session.getId(),
                playerId,
                playerName,
                session
        );

        session.getUserProperties().put("roomId", roomId);
        session.getUserProperties().put("playerSession", playerSession);

        ROOM_MANAGER.createRoom(roomId);
        ROOM_MANAGER.joinRoom(roomId, playerSession);

        ROOM_MANAGER.broadcastToRoom(roomId,
                "[system] " + playerName + " joined room " + roomId);
    }

    @OnMessage
    public void onMessage(Session session, String message) {
        String roomId = (String) session.getUserProperties().get("roomId");
        PlayerSession playerSession =
                (PlayerSession) session.getUserProperties().get("playerSession");

        if (roomId == null || playerSession == null) {
            return;
        }

        String outbound = playerSession.getPlayerName() + ": " + message;
        ROOM_MANAGER.broadcastToRoom(roomId, outbound);
    }

    @OnClose
    public void onClose(Session session, CloseReason closeReason) {
        cleanupSession(session);
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        System.err.println("WebSocket error for session " +
                (session != null ? session.getId() : "unknown") +
                ": " + throwable.getMessage());

        if (session != null) {
            cleanupSession(session);
        }
    }

    private void cleanupSession(Session session) {
        String roomId = (String) session.getUserProperties().get("roomId");
        PlayerSession playerSession =
                (PlayerSession) session.getUserProperties().get("playerSession");

        if (roomId != null) {
            ROOM_MANAGER.leaveRoom(roomId, session.getId());

            if (playerSession != null) {
                ROOM_MANAGER.broadcastToRoom(roomId,
                        "[system] " + playerSession.getPlayerName() + " left the room");
            }
        }
    }

    private static String getFirst(Map<String, List<String>> params, String key) {
        List<String> values = params.get(key);
        return (values == null || values.isEmpty()) ? null : values.get(0);
    }

    private static boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

    /**
     * Example event listener for logging room actions.
     */
    private static class LoggingRoomListener implements RoomEventListener {

        @Override
        public void onRoomCreated(String roomId) {
            System.out.println("[room-created] " + roomId);
        }

        @Override
        public void onRoomRemoved(String roomId) {
            System.out.println("[room-removed] " + roomId);
        }

        @Override
        public void onPlayerJoined(String roomId, PlayerSession playerSession) {
            System.out.println("[player-joined] room=" + roomId +
                    ", player=" + playerSession.getPlayerName());
        }

        @Override
        public void onPlayerLeft(String roomId, PlayerSession playerSession) {
            System.out.println("[player-left] room=" + roomId +
                    ", player=" + playerSession.getPlayerName());
        }

        @Override
        public void onMessageBroadcast(String roomId, String message) {
            System.out.println("[broadcast] room=" + roomId + ", msg=" + message);
        }
    }
}