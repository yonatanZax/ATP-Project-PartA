package Server;

import IO.MyCompressorOutputStream;
import IO.MyDecompressorInputStream;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;
import algorithms.search.BestFirstSearch;
import algorithms.search.SearchableMaze;
import algorithms.search.Solution;

import java.io.*;

public class ServerStrategySolveSearchProblem implements IServerStrategy {

    @Override
    public void serverStrategy(InputStream inFromClient, OutputStream outToClient) {
        //TODO implement
        String tempDirectoryPath = System.getProperty("java.io.tmpdir");
        try {
            MyDecompressorInputStream fromClient = new MyDecompressorInputStream(inFromClient);
            BufferedWriter toClient = new BufferedWriter(new OutputStreamWriter(outToClient));
            toClient.flush();
            try {
                byte[] mazeByteArray = new byte[0];
                fromClient.read(mazeByteArray);
                Maze maze = new Maze(mazeByteArray);
                SearchableMaze searchableMaze = new SearchableMaze(maze);
                BestFirstSearch bfs = new BestFirstSearch();
                Solution solution = bfs.solve(searchableMaze);
                toClient.write(solution.toString());
                toClient.flush();
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
