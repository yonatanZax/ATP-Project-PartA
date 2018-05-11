package Client;

import IO.MyDecompressorInputStream;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;
import algorithms.search.AState;
import algorithms.search.Solution;

import java.io.*;
import java.util.ArrayList;

public class MyClientStrategy implements IClientStrategy {
    @Override
    public void clientStrategy(InputStream inFromServer, OutputStream outToServer) {
        try {
            ObjectOutputStream toServer = new ObjectOutputStream(outToServer);
            ObjectInputStream fromServer = new ObjectInputStream(new MyDecompressorInputStream(inFromServer));
            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));

            toServer.flush();

            System.out.println("Plese enter number of rows: ");
            int row = Integer.valueOf(userInput.readLine());
            System.out.println("Plese enter number of columns: ");
            int col = Integer.valueOf(userInput.readLine());

            int[] mazeDimensions = new int[]{row, col};
            toServer.writeObject(mazeDimensions); //send maze dimensions to server
            toServer.flush();
            byte[] compressedMaze = (byte[]) fromServer.readObject(); //read generated maze (compressed with MyCompressor) from server
            InputStream is = new MyDecompressorInputStream(new ByteArrayInputStream(compressedMaze));
            byte[] decompressedMaze = new byte[1000 /*CHANGE SIZE ACCORDING TO YOU MAZE SIZE*/]; //allocating byte[] for the decompressed maze -
            is.read(decompressedMaze); //Fill decompressedMaze with bytes
            Maze maze = new Maze(decompressedMaze);
            maze.print();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
