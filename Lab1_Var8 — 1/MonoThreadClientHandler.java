import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class MonoThreadClientHandler implements Runnable {

    private static Socket clientDialog;

    public MonoThreadClientHandler(Socket client) {
        MonoThreadClientHandler.clientDialog = client;
    }

    @Override
    public void run() {

        try {
            // ініціюємо канали для спілкування в сокеті для сервера

            DataOutputStream out = new DataOutputStream(clientDialog.getOutputStream());

// канал читання із сокету
            DataInputStream in = new DataInputStream(clientDialog.getInputStream());
            System.out.println("DataInputStream created");

            System.out.println("DataOutputStream  created");


            // починаємо діалог з підключеним клієнтом в циклі, поки сокет не буде закритим клієнтом
            while (!clientDialog.isClosed()) {
                System.out.println("Server reading from channel");

                // сервер очікує в каналі читання  (inputstream) отримання даних клієнта після отоимання даних зчитує їх

                String entry = in.readUTF();

                // та виводить в консоль
                System.out.println("READ from clientDialog message - " + entry);

                // ініціалізація перевірки умови продовження роботи з клієнтом по цьому сокету за кодовим словом  - quit
                if (entry.equalsIgnoreCase("quit")) {

                    // якщо кодове слово отримано, то ініціалізується закриття
                    System.out.println("Client initialize connections suicide ...");
                    out.writeUTF("Server reply - " + entry + " - OK");
                    Thread.sleep(3000);
                    break;
                }

                //  якщо умова закінчення роботи - не вірна, то продовжуємо роботи і відправляємо його назад клієнту

                System.out.println("Server try writing to channel");
                out.writeUTF("Server reply - " + entry + " - OK");
                System.out.println("Server Wrote message to clientDialog.");

                // звільнюємо буфер мережевих повідомлень
                out.flush();

                // повертаємось на початок для зчитування нового повідомлення
            }



            // якщо умова - вірна, то вимикаємо підключення
            System.out.println("Client disconnected");
            System.out.println("Closing connections & channels.");

            // закриваємо каналаи сокету
            in.close();
            out.close();

            // потім закриваємо сокет спілкування з клієнтом
            clientDialog.close();

            System.out.println("Closing connections & channels - DONE.");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}