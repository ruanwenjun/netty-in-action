package nia.chapter1.bio;

import java.io.IOException;

/**
 * @author weruan
 * @date 2019-04-29
 */
public class Test {

    public static void main(String[] args) throws InterruptedException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("start server....");
                try {
                    new Server().startServer(40003);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        Thread.sleep(3000);
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("start client...");
                Client client = new Client("127.0.0.1", 40003);
                client.sendMessage("hello world");
            }
        }).start();
    }
}
