import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * @author mercenery
 *
 */
public class MultiThreadServer {

    static ExecutorService executeIt = Executors.newFixedThreadPool(2);

    /**
     * @param args
     */
    public static void main(String[] args) {

        // запускаємо сервер на порті 3345 та ініціалізуємо зміну для обробки консольних команд з самого сервера
        try (ServerSocket server = new ServerSocket(3345);) {
            System.out.println("Server socket created, command console reader for listen to server commands");

            // запускаємо цикл при умові, що серверний сокет не закритий
            while (!server.isClosed()) {

                // стаємо в очікування
                // підключаємо до сокету (спілкування) під іменем  - "clientDialog" на серверній стороні
                Socket client = server.accept();

                // після отримання запиту на підключення сервер створює сокет для спілкування з клієнтом та відправляє його окремо в Runnable
                // і він продовжує роботу від імені сервера

                executeIt.execute(new MonoThreadClientHandler(client));
                System.out.print("Connection accepted.");
            }

            // закриття пісял завершення роботи
            executeIt.shutdown();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}