package Server;

import algorithms.mazeGenerators.IMazeGenerator;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.Position;
import algorithms.search.*;
//import sun.awt.Mutex;

import java.io.*;
import java.util.concurrent.locks.ReentrantLock;

public class ServerStrategySolveSearchProblem implements IServerStrategy {

    //private Mutex mutex;
    //private ReentrantLock rLock;

    public ServerStrategySolveSearchProblem(){
        //mutex = new Mutex();
        //rLock = new ReentrantLock();
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
                //rLock.lock();
                //mutex.lock();
                File f = new File(tempPath);
                if(f.exists()){ // the file exists, we don't need to solve again. only take what exists.
                    FileInputStream fin = new FileInputStream(tempPath);
                    ObjectInputStream oin = new ObjectInputStream(fin);
                    solution = (Solution) oin.readObject();
                    fin.close();
                    oin.close();
                }
                else{
                    SearchableMaze searchableMaze = new SearchableMaze(maze);
                    solution = Configurations.getAlgorithms_solveAlgorithm().solve(searchableMaze);
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
                //mutex.unlock();
                //rLock.unlock();
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
