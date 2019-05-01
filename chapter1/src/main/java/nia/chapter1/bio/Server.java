package nia.chapter1.bio;

import com.sun.xml.internal.ws.addressing.WsaTubeHelperImpl;
import sun.net.util.IPAddressUtil;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;

/**
 * @author weruan
 * @date 2019-04-29
 */
public class Server {
    private ServerSocket serverSocket;
    private ThreadPoolExecutor threadPoolExecutor;

    public Server() throws IOException {
        int coreThread = 5;
        int maxThread = 10;
        threadPoolExecutor = new ThreadPoolExecutor(
            coreThread,
            maxThread,
            60,
            TimeUnit.SECONDS,
            new ArrayBlockingQueue<Runnable>(1000),
            new ClientThreadFactory());
    }

    public void startServer(int port) throws IOException {
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("服务启动，port = " + port);
            while (true) {
                Socket socket = serverSocket.accept();
                threadPoolExecutor.submit(new ClientRunnable(socket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != serverSocket) {
                serverSocket.close();
            }
        }
    }

    private class ClientRunnable implements Runnable {
        private Socket socket;

        public ClientRunnable(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                 PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true)) {
                String message;
                while (true) {
                    if (null == (message = bufferedReader.readLine())) {
                        continue;
                    }
                    System.out.println(Thread.currentThread().getName() + "服务端收到消息:" + message);
                    printWriter.println("SUCCESS");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
