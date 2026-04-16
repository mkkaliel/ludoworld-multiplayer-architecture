\# roommanager package



This package contains the \*\*core reusable room/session logic\*\* for the project.



Its purpose is to provide a small generic foundation for room-based real-time applications such as multiplayer games, chats, and collaborative tools.



\---



\## Package Goal



The goal of this package is to separate \*\*generic room logic\*\* from \*\*application-specific business logic\*\*.



That means this package is responsible for:



\- managing rooms

\- tracking room members

\- broadcasting room-scoped messages

\- removing disconnected sessions

\- notifying listeners about room lifecycle events



It is \*\*not\*\* responsible for:



\- game rules

\- authentication

\- persistence

\- payment systems

\- product-specific workflows



\---



\## Classes in this package



\### `PlayerSession`

Represents one connected participant.



Typical fields include:



\- session ID

\- player/user ID

\- display name

\- underlying WebSocket session reference



Use this class whenever you need a lightweight identity object tied to a live connection.



\---



\### `Room`

Represents a single room.



Responsibilities:



\- store room members

\- add/remove players

\- expose room membership

\- broadcast text messages to all connected members



A room should be thought of as a container for active participants.



Examples:



\- one multiplayer match

\- one chat room

\- one live classroom

\- one collaboration channel



\---



\### `RoomManager`

Central class for managing all rooms.



Responsibilities:



\- create rooms

\- return rooms by ID

\- join users to rooms

\- remove users from rooms

\- remove empty rooms

\- broadcast messages to a room



This class acts as the main coordination layer for the package.



In most projects, this will be the primary entry point used by WebSocket endpoints or service classes.



\---



\### `RoomEventListener`

Optional lifecycle hook interface.



Use this when you want to react to events such as:



\- room created

\- room removed

\- player joined

\- player left

\- message broadcast



This is useful for:



\- logging

\- metrics

\- auditing

\- analytics

\- custom application hooks



\---



\### `MessageRouter`

A lightweight helper for routing messages into a room.



In more advanced systems, this may later evolve into:



\- JSON message dispatch

\- command routing

\- event-based handlers

\- permission-aware routing



In this starter project it is intentionally simple.



\---



\## How the classes work together



Typical flow:



1\. A user connects through a WebSocket endpoint

2\. The endpoint creates a `PlayerSession`

3\. The endpoint calls `RoomManager.joinRoom(...)`

4\. Messages received from the user are routed to the room

5\. The room broadcasts to all active room members

6\. When the user disconnects, the endpoint removes the session

7\. Empty rooms are automatically cleaned up



\---



\## High-level design philosophy



This package is intentionally:



\- small

\- readable

\- generic

\- easy to extend



It is meant to be a \*\*foundation\*\*, not a complete framework.



\---



\## Extension points



A developer can customize this package in many ways.



Examples:



\- add room capacity limits

\- support room metadata

\- add user roles like host/admin/player

\- add private/public room visibility

\- add JSON serializers

\- add permission checks

\- integrate metrics

\- integrate distributed cache or Redis



\---



\## AI-assisted development notes



This markdown file exists partly so AI tools can understand the package structure and help generate bespoke Java variants.



Examples of custom variations a developer may request from an AI tool:



\- "Convert this room manager into a chat-room manager with moderator roles"

\- "Add room capacity and waitlist support"

\- "Add JSON-based message routing"

\- "Turn `PlayerSession` into `ParticipantSession` for a classroom product"

\- "Add team-based room metadata for multiplayer games"



Because the package structure is documented clearly, an AI system can more safely adapt the code with less guesswork.



\---



\## Suggested bespoke variants



This same package can be adapted into:



\- `chat-room-manager`

\- `game-match-room-manager`

\- `classroom-room-manager`

\- `live-collaboration-room-manager`

\- `quiz-room-manager`



\---



\## Key principle



Keep this package generic.



Push business-specific logic outward into higher-level application code.

