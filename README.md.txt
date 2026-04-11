# Java WebSocket Room Manager

A lightweight Java room management module for WebSocket-based real-time applications.

This project provides reusable room and session management components for:

- multiplayer turn-based games
- chat rooms
- collaborative real-time applications
- live event systems

It is framework-agnostic at the room-management level and can be integrated into a Java WebSocket server implementation.

## Features

- Create and manage rooms
- Join and leave rooms
- Track player sessions
- Broadcast messages to all players in a room
- Remove disconnected sessions
- Auto-cleanup for empty rooms
- Event hooks for room lifecycle actions

## Why this project exists

Many real-time applications need a simple way to manage users inside rooms without rewriting the same room lifecycle logic.

This module separates:

- player session tracking
- room membership
- room broadcasting
- room cleanup

from the application-specific game or chat rules.

## Example Use Cases

- turn-based multiplayer games
- WebSocket chat servers
- collaborative editing tools
- quiz / trivia rooms
- live classroom groups

## Package Structure

- `PlayerSession` – represents a connected user
- `Room` – stores room members and room-level operations
- `RoomManager` – creates, joins, leaves, and removes rooms
- `RoomEventListener` – optional event hook interface
- `MessageRouter` – helper for routing room-scoped messages

## Example

```java
RoomManager roomManager = new RoomManager();

PlayerSession alice = new PlayerSession("s1", "u1", "Alice", null);
PlayerSession bob = new PlayerSession("s2", "u2", "Bob", null);

roomManager.createRoom("room-1");
roomManager.joinRoom("room-1", alice);
roomManager.joinRoom("room-1", bob);

roomManager.broadcastToRoom("room-1", "Hello room!");
```

## Notes

This project intentionally does not include:

- game rules
- monetization logic
- persistence/database code
- authentication
- product-specific business logic

It is intended to be used as a reusable building block inside a larger real-time application.

## Future Improvements

Planned ideas:

- room capacity limits
- room metadata support
- private/public room visibility
- pluggable serializers
- heartbeat / idle timeout helpers
- distributed room coordination adapters

## License

MIT