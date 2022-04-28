import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.io.*;


public class Client {

    /**
     *
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {

// запускаємо підключення сокету по відомому порту та ініціалізуємо прийом  повідомленнь з консолі клієта
try(Socket socket = new Socket("localhost", 3345);
            BufferedReader br =new BufferedReader(new InputStreamReader(System.in));
            DataOutputStream oos = new DataOutputStream(socket.getOutputStream());
            DataInputStream ois = new DataInputStream(socket.getInputStream());	)
        {

            System.out.println("Client connected to socket.");
            System.out.println();
            System.out.println("Client writing channel = oos & reading channel = ois initialized.");

// перевіряємо чи існує канал, якщо так,то працюємо з ним
            while(!socket.isOutputShutdown()){

// чекаємо на дані в консолі клієнта
                if(br.ready()){

// дані з'явились, починаємо роботу
                    System.out.println("Client start writing in channel...");
                    Thread.sleep(1000);
                    String clientCommand = br.readLine();
//записуємо дані в файл
                    try(FileWriter writer = new FileWriter("Client.txt", true))
                    {
                        // запись всей строки
                        String text = (clientCommand + " ");
                        writer.write(text);

                    }
                    catch(IOException ex){

                        System.out.println(ex.getMessage());
                    }
// записуємо дані з консолі в канал сокету для сервера
                    oos.writeUTF(clientCommand);
                    oos.flush();
                    System.out.println("Clien sent message " + clientCommand + " to server.");
                    Thread.sleep(1000);

// чекаємо, щоб сервер прочитав повідомлення із сокета та відповів на нього

// перевіряємо умову виходу із з'єднання
                    if(clientCommand.equalsIgnoreCase("quit")){


// якщо умова виходу досягнуто та здійснюємо роз'єднання
                        System.out.println("Client kill connections");
                        Thread.sleep(2000);

//  дивимось, що нам відповів сервер перед закриттям ресурсу
                        if(ois.available()!=0)		{
                            System.out.println("reading...");
                            String in = ois.readUTF();
                            System.out.println(in);
                        }


// далі виходимо з циклу записі читання
                        break;
                    }


// якщо умова роз'єднання не досягнута, то продовжуємо роботу
                    System.out.println("Client wrote & start waiting for data from server...");
                    Thread.sleep(2000);

// перевіряємо, що нам відповів сервер на наше повідомлення
                    if(ois.available()!=0)		{

// забираємо відповідь з каналу сервера в сокеті і зберігаємо її в зміну ois , друкуємо на клієнтську консоль
                        System.out.println("reading...");
                        String in = ois.readUTF();
                        System.out.println(in);
                    }
                }
            }
// перед виходом із циклу закриваємо свої ресурси
            System.out.println("Closing connections & channels on clentSide - DONE.");

        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}