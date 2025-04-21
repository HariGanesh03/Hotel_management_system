import java.util.*;

class Room {
    String guest1, guest2;
    boolean isDouble;
    int roomNo;
    int baseRate;
    List<String> foodOrders = new ArrayList<>();

    Room(int roomNo, boolean isDouble, int baseRate) {
        this.roomNo = roomNo;
        this.isDouble = isDouble;
        this.baseRate = baseRate;
    }

    void addGuests(String g1, String g2) {
        guest1 = g1;
        if (isDouble) guest2 = g2;
    }

    void orderFood(String item) {
        foodOrders.add(item);
    }

    void printBill() {
        System.out.println("\n--- Bill for Room " + roomNo + " ---");
        System.out.println("Guests: " + guest1 + (guest2 != null ? ", " + guest2 : ""));
        System.out.println("Room Charge: Rs." + baseRate);
        if (!foodOrders.isEmpty()) {
            System.out.println("Food Ordered: " + String.join(", ", foodOrders));
        }
        System.out.println("--------------------------\nTotal: Rs." + (baseRate + foodOrders.size() * 50));
    }
}

public class HotelApp {
    static Scanner sc = new Scanner(System.in);
    static Map<Integer, Room> bookings = new HashMap<>();

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n========== Hotel Menu ==========");
            System.out.println("1. Book Room\n2. Order Food\n3. Checkout\n4. Exit");
            System.out.print("Choose an option: ");
            int choice = sc.nextInt(); sc.nextLine();

            switch (choice) {
                case 1 -> bookRoom();
                case 2 -> orderFood();
                case 3 -> checkout();
                case 4 -> {
                    System.out.println("Thank you for using HotelApp!");
                    return;
                }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
    }

    static void showAvailableRooms() {
        System.out.println("Available Rooms:");
        for (int i = 101; i <= 110; i++) {
            if (!bookings.containsKey(i)) {
                System.out.println("Room " + i);
            }
        }
    }

    static void showRoomTypes() {
        System.out.println("\nRoom Types:");
        System.out.println("Single Room - Rs.1500");
        System.out.println("Double Room - Rs.3000");
    }

    static void showMenu() {
        System.out.println("\nFood Menu:");
        System.out.println("1. Sandwich\n2. Pasta\n3. Noodles\n4. Coke");
        System.out.println("Each item costs Rs.50\n");
    }

    static void bookRoom() {
        showAvailableRooms();
        showRoomTypes();
        System.out.print("Enter room number to book (101-110): ");
        int num = sc.nextInt(); sc.nextLine();
        if (bookings.containsKey(num)) {
            System.out.println("Room already booked!");
            return;
        }

        System.out.print("Is it a double room? (yes/no): ");
        boolean isDouble = sc.nextLine().equalsIgnoreCase("yes");
        System.out.print("Enter guest name: ");
        String guest1 = sc.nextLine();
        String guest2 = null;
        if (isDouble) {
            System.out.print("Enter second guest name: ");
            guest2 = sc.nextLine();
        }

        int baseRate = isDouble ? 3000 : 1500;
        Room room = new Room(num, isDouble, baseRate);
        room.addGuests(guest1, guest2);
        bookings.put(num, room);
        System.out.println("Room booked successfully!");
    }

    static void orderFood() {
        System.out.print("Enter room number: ");
        int num = sc.nextInt(); sc.nextLine();
        Room room = bookings.get(num);
        if (room == null) {
            System.out.println("Room not found.");
            return;
        }

        showMenu();
        System.out.print("Enter food item to order: ");
        String item = sc.nextLine();
        room.orderFood(item);
        System.out.println("Food added!");
    }

    static void checkout() {
        System.out.print("Enter room number: ");
        int num = sc.nextInt(); sc.nextLine();
        Room room = bookings.remove(num);
        if (room == null) {
            System.out.println("Room not found.");
            return;
        }
        room.printBill();
        System.out.println("Checkout complete. Room deallocated.");
    }
}
