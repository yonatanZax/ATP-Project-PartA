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

            ObjectInputStream fromClient = new ObjectInputStream(inFromClient);
            ObjectOutputStream toClient = new ObjectOutputStream(outToClient);
            toClient.flush();
            try {
                Maze maze = (Maze) fromClient.readObject();
                SearchableMaze searchableMaze = new SearchableMaze(maze);
                BestFirstSearch bfs = new BestFirstSearch();
                Solution solution = bfs.solve(searchableMaze);
                toClient.writeObject(solution);
                toClient.flush();
            }
            catch (IOException e){
                System.out.println("IOException in ServerStrategySolveSearchProblem");
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
            catch (Exception e){
                System.out.println("Exception in ServerStrategySolveSearchProblem");
                System.out.println(e.getMessage());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
