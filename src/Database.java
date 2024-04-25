import java.sql.*;

public class Database {
    private static final String DB_URL = "jdbc:mysql://localhost/testt";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    private static Connection conn;

    static {
        try {
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            System.out.println("З'єднання з базою даних встановлено");
        } catch (SQLException e) {
            System.err.println("Не вдалося встановити з'єднання з базою даних");
            e.printStackTrace();
        }
    }
    public static void logMessage(String nickname, String message, String ipAddress) {
        Thread dataThread = new Thread(() -> {
            try {
                String sql = "INSERT INTO messages (nickname, message, ip_address) VALUES (?, ?, ?)";
                PreparedStatement statement = conn.prepareStatement(sql);
                statement.setString(1, nickname);
                statement.setString(2, message);
                statement.setString(3, ipAddress);
                int rowsInserted = statement.executeUpdate();
                System.out.println(rowsInserted + " рядків вставлено");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        dataThread.start();
    }
    public static void main(String[] args) {
        while (true) {
        }
        }
        //logMessage("UserNick", "Hello, world!", "192.168.0.1");
    }