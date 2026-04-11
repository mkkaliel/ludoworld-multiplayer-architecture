import com.example.roommanager.PlayerSession;
import com.example.roommanager.RoomManager;

public class Demo {
    public static void main(String[] args) {
        RoomManager roomManager = new RoomManager();

        PlayerSession alice = new PlayerSession("s1", "u1", "Alice", null);
        PlayerSession bob = new PlayerSession("s2", "u2", "Bob", null);

        roomManager.createRoom("room-1");
        roomManager.joinRoom("room-1", alice);
        roomManager.joinRoom("room-1", bob);

        System.out.println("Room count: " + roomManager.getRoomCount());
        roomManager.broadcastToRoom("room-1", "Hello everyone");

        roomManager.leaveRoom("room-1", "s1");
        roomManager.leaveRoom("room-1", "s2");

        System.out.println("Room count after cleanup: " + roomManager.getRoomCount());
    }
}