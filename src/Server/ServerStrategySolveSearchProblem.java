package Server;

import IO.MyCompressorOutputStream;
import IO.MyDecompressorInputStream;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;
import algorithms.search.BestFirstSearch;
import algorithms.search.ISearchingAlgorithm;
import algorithms.search.SearchableMaze;
import algorithms.search.Solution;

import java.io.*;

public class ServerStrategySolveSearchProblem implements IServerStrategy {

    public static ISearchingAlgorithm searchAlgorithm;
    @Override
    public void serverStrategy(InputStream inFromClient, OutputStream outToClient) {
        //TODO implement
        String tempDirectoryPath = System.getProperty("java.io.tmpdir");
        try {
            MyDecompressorInputStream fromClient = new MyDecompressorInputStream(new ObjectInputStream(inFromClient));
            OutputStream ObjectoutToClient = new ObjectOutputStream(outToClient);
            ObjectOutputStream toClient = new ObjectOutputStream(ObjectoutToClient);
            toClient.flush();
            try {
                byte[] mazeByteArray = new byte[2512];
                fromClient.read(mazeByteArray);
                Maze maze = new Maze(mazeByteArray);
                SearchableMaze searchableMaze = new SearchableMaze(maze);
                BestFirstSearch bfs = new BestFirstSearch();
                Solution solution = bfs.solve(searchableMaze);
                toClient.writeObject(solution);
                toClient.flush();
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
