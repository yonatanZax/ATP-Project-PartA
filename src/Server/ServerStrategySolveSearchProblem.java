package Server;

import algorithms.mazeGenerators.IMazeGenerator;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.Position;
import algorithms.search.*;
import sun.awt.Mutex;

import java.io.*;

public class ServerStrategySolveSearchProblem implements IServerStrategy {

    private Mutex mutex;

    public ServerStrategySolveSearchProblem(){
        mutex = new Mutex();
    }

    private static String tempDirectoryPath = System.getProperty("java.io.tmpdir");
    @Override
    public void serverStrategy(InputStream inFromClient, OutputStream outToClient) {

        Solution solution;
        try {
            ObjectInputStream fromClient = new ObjectInputStream(inFromClient);
            ObjectOutputStream toClient = new ObjectOutputStream(outToClient);
            toClient.flush();
            try {
                Maze maze = (Maze) fromClient.readObject();
                String tempPath = tempDirectoryPath + "maze" + maze.toString().hashCode();
                System.out.println(tempPath);
                mutex.lock();
                File f = new File(tempPath);
                if(f.exists()){ // the file exists, we don't need to solve again. only take what exists.
                    System.out.println("####### File Exists ######## ");
                    FileInputStream fin = new FileInputStream(tempPath);
                    ObjectInputStream oin = new ObjectInputStream(fin);
                    solution = (Solution) oin.readObject();
                    System.out.println("#######solution is:#######\n" + solution+ "\n\n####################");
                    fin.close();
                    oin.close();
                }
                else{
                    System.out.println("####### File NOT Exists ######## ");

                    SearchableMaze searchableMaze = new SearchableMaze(maze);
                    solution = Configurations.getAlgorithms_solveAlgorithm().solve(searchableMaze);
                    System.out.println("#######solution is:#######\n" + solution+ "\n\n####################");
                    f.createNewFile();
                    FileOutputStream fout = new FileOutputStream(tempPath);
                    fout.flush();
                    ObjectOutputStream oout = new ObjectOutputStream(fout);
                    oout.flush();
                    oout.writeObject(solution);
                    oout.flush();
                    fout.close();
                    oout.close();
                }
                mutex.unlock();
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
            toClient.close();
            fromClient.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
