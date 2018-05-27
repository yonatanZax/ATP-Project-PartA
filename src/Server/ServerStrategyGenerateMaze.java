package Server;

import IO.MyCompressorOutputStream;
import IO.MyDecompressorInputStream;
import algorithms.mazeGenerators.IMazeGenerator;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;
import algorithms.search.ISearchingAlgorithm;

import java.io.*;

public class ServerStrategyGenerateMaze implements IServerStrategy{

    @Override
    public void serverStrategy(InputStream inFromClient, OutputStream outToClient) {

        //System.out.println("ServerStrategy: GenerateMaze");
        try {
            ObjectInputStream fromClient = new ObjectInputStream(inFromClient);
            ObjectOutputStream toClientObject = new ObjectOutputStream(outToClient);
            ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
            MyCompressorOutputStream toClient = new MyCompressorOutputStream(byteOut);

            int[] mazeDimensions;
            try {
                mazeDimensions = (int[]) fromClient.readObject();
                //MyMazeGenerator mg = new MyMazeGenerator();
                IMazeGenerator mg = Configurations.getGenerators_mazeGenerator();
                Maze maze = mg.generate(mazeDimensions[0], mazeDimensions[1]);
                byte[] mazeByteArray = maze.toByteArray();
                //toclientObject.writeObject(mazeByteArray);
                toClient.write(mazeByteArray);
                toClientObject.writeObject(byteOut.toByteArray());
                toClientObject.flush();
            }
            catch (ClassNotFoundException e){
                System.out.println("Class Not found exception: ServerStrategyGenerateMaze");
            }
            catch (ArrayIndexOutOfBoundsException e){
                System.out.println("Client should send 2 parameters of maze size: ServerStrategyGenerateMaze");
            }
            catch (IOException e){
                System.out.println("IOException in ServerStrategyGenerateMaze");
                System.out.println(e.getMessage());
                e.printStackTrace();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
