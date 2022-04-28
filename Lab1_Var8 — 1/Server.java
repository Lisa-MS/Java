import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {


    /**
     *
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
//	запускаємо сервер на порті 3345

        try	(ServerSocket server= new ServerSocket(3345);){
//  стаємо в очікування підключення до сокету під іменем "client" на серверному боці
            Socket client = server.accept();

// після хендщейкингу сервер асоціює клієнта, що підключився з цим сокет-підключенням
            System.out.print("Connection accepted.");

// ініціюємо канали (для спілкування) в сокеті для сервера

            // канал читання із сокету
            DataInputStream in = new DataInputStream(client.getInputStream());
            System.out.println("DataInputStream created");

            // канал запису в сокет
            DataOutputStream out = new DataOutputStream(client.getOutputStream());
            System.out.println("DataOutputStream  created");

// починаємо діалог з підключенним клієнтом в циклі, поки сокет не закритий
            while(!client.isClosed()){

                System.out.println("Server reading from channel");

// сервер очікує в каналі читання  (inputstream) отримання даних клієнта
                String entry = in.readUTF();
                try(FileWriter writer = new FileWriter("Server.txt", true))
                {
                    // запис всього рядка
                    String text = (entry + " ");
                    writer.write(text);

                }
                catch(IOException ex){

                    System.out.println(ex.getMessage());
                }
// після отримання даних зчитує їх
                System.out.println("READ from client message - "+entry);

// та виводить в консоль
                System.out.println("Server try writing to channel");

// ініціалізація перевірки умови продовження роботи з клієнтом по цьому сокету за кодовим словом
                if(entry.equalsIgnoreCase("quit")){
                    System.out.println("Client initialize connections suicide ...");
                    out.writeUTF("Server reply - "+entry + " - OK");
                    Thread.sleep(3000);
                    break;
                }

// якщо умова закінчення роботи не вірна, то продовжуємо роботу та відправляємо його назад клієнту
                out.writeUTF("Server reply - "+entry + " - OK");
                System.out.println("Server Wrote message to client.");

// звільнюємо буфер мережевих повідомлень
                out.flush();

            }

// якщо умова виходу вірна, то вимикаємо з'єднання
            System.out.println("Client disconnected");
            System.out.println("Closing connections & channels.");

            // закриваємо канали сокета
            in.close();
            out.close();

            // потім закриваємо сокет спілкування на стороні сервера
            client.close();

            // потім закриваємо сокет сервера який створює сокет спілкування
            // та ставимо цей серверний сокет в очікування нового підключення
            server.close();
            System.out.println("Closing connections & channels - DONE.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}