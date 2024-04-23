import java.io.*;
import java.net.*;
import java.util.*;

public class Clients {
    private static BufferedReader reader;
    private static PrintWriter writer;
    private static Socket socket;

    public static void main(String[] args) {
        try {
            socket = new Socket("192.168.56.1", 8888);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream(), true);

            System.out.println("Підключено до сервера.");

            // Отримати нік від користувача
            System.out.print("Введіть ваш нік: ");
            String nickname = new Scanner(System.in).nextLine();
            writer.println(nickname);

            System.out.println("Введіть повідомлення:");

            Thread sendThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    String message;
                    Scanner scanner = new Scanner(System.in);
                    while ((message = scanner.nextLine()) != null) {
                        writer.println(message);
                    }
                }
            });

            Thread receiveThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    String message;
                    try {
                        while ((message = reader.readLine()) != null) {
                            System.out.println(message);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

            sendThread.start();
            receiveThread.start();

            sendThread.join();
            receiveThread.join();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            try {
                if (socket != null) {
                    socket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}