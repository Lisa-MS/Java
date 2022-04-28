import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class TestRunnableClientTester implements Runnable {

    static Socket socket;

    public TestRunnableClientTester() {
        try {

            // створюємо сокет спілкування на стороні клієгта в конструкторі об'єкту
            socket = new Socket("localhost", 3345);
            System.out.println("Client connected to socket");
            Thread.sleep(2000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {

        try (

                // створюємо об'єкт для запису рядків в створений сокет для читання рядків із сокету
                DataOutputStream oos = new DataOutputStream(socket.getOutputStream());
                DataInputStream ois = new DataInputStream(socket.getInputStream())) {
            System.out.println("Client oos & ois initialized");

            int i = 0;
            // створюємо цикл
            while (i < 5) {

                // пишемо авто  повідомлення, яке генерується циклом клієнта в канал сокету для сервера
                oos.writeUTF("clientCommand " + i);

                // передаємо повідомлення з буфера мережевих  повідомлень в канал
                oos.flush();

                // чекаємо,  щоб сервер прочитав повідомлення з сокету и відповів
                Thread.sleep(10);
                System.out.println("Client wrote & start waiting for data from server...");

                // забираємо відповідь з каналу сервера в сокет клієнта і зберігаємо її в ois змінну, друкуємо на консоль
                System.out.println("reading...");
                String in = ois.readUTF();
                System.out.println(in);
                i++;
                Thread.sleep(5000);

            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}