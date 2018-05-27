package Server;

//import Server.Strategies.IServerStrategy;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;


public class Server {
    private int port;
    private int listeningInterval;
    private IServerStrategy serverStrategy;
    private volatile boolean stop;
    private Executor threadPool;
    //private static final Logger LOG = LogManager.getLogger(); //Log4j2



    public Server(int port, int listeningInterval, IServerStrategy serverStrategy) {
        this.port = port;
        this.listeningInterval = listeningInterval;
        this.serverStrategy = serverStrategy;
        threadPool = Executors.newCachedThreadPool();
    }

    public void start() {
        new Thread(() -> {
            runServer();
        }).start();

    }

    private void runServer() {
        try {
            System.out.println("Server: runServer");
            ServerSocket server = new ServerSocket(port);
            server.setSoTimeout(listeningInterval);
            //LOG.info(String.format("Server started! (port: %s)", port));
            while (!stop) {
                try {
                    Socket clientSocket = server.accept(); // blocking call
                    //LOG.info(String.format("Client excepted: %s", clientSocket.toString()));
                    System.out.println(String.format("Server: Client accepted: %s", clientSocket.toString()));
                    //threadPool.execute(()->{ handleClient(clientSocket);});
                    handleClient(clientSocket);

                } catch (SocketTimeoutException e) {
                    //LOG.debug("SocketTimeout - No clients pending!");
                }
            }
            server.close();
        } catch (IOException e) {
            //LOG.error("IOException", e);
        }
    }

    private void handleClient(Socket clientSocket) {
        try {
            //LOG.debug("Client excepted!");
            //LOG.debug(String.format("Handling client with socket: %s", clientSocket.toString()));
            System.out.println("Server - handleClient: Client accepted!");
            System.out.println(String.format("Server - handleClient: Handling client with socket: %s", clientSocket.toString()));
            serverStrategy.serverStrategy(clientSocket.getInputStream(), clientSocket.getOutputStream());
            clientSocket.getInputStream().close();
            clientSocket.getOutputStream().close();
            clientSocket.close();
        } catch (IOException e) {
            //LOG.error("IOException", e);
        }
    }

    public void stop() {
        //LOG.info("Stopping server..");
        stop = true;
    }

    public static void main(String[] args) {
        Server mazeGeneratingServer = new Server(5400, 1000, new ServerStrategyGenerateMaze());
        mazeGeneratingServer.start();
    }
}

