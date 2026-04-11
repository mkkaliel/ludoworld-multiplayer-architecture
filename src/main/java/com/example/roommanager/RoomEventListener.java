package com.example.roommanager;

/**
 * Optional listener for room lifecycle events.
 */
public interface RoomEventListener {

    default void onRoomCreated(String roomId) {
    }

    default void onRoomRemoved(String roomId) {
    }

    default void onPlayerJoined(String roomId, PlayerSession playerSession) {
    }

    default void onPlayerLeft(String roomId, PlayerSession playerSession) {
    }

    default void onMessageBroadcast(String roomId, String message) {
    }
}