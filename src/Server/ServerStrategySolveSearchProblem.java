package Server;

import algorithms.mazeGenerators.Maze;
import algorithms.search.*;
import java.io.*;
import java.util.concurrent.locks.ReentrantLock;

public class ServerStrategySolveSearchProblem implements IServerStrategy {

    private ReentrantLock mutex;

    public ServerStrategySolveSearchProblem(){
        mutex = new ReentrantLock();
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

                mutex.lock();
                File f = new File(tempPath);
                if(f.exists()){ // the file exists, we don't need to solve again. only take what exists.
                    FileInputStream fin = new FileInputStream(tempPath);
                    ObjectInputStream oin = new ObjectInputStream(fin);
                    solution = (Solution) oin.readObject();
                    fin.close();
                    oin.close();
                    mutex.unlock();
                }
                else{
                    mutex.unlock();
                    SearchableMaze searchableMaze = new SearchableMaze(maze);
                    solution = Configurations.getAlgorithms_solveAlgorithm().solve(searchableMaze);
                    mutex.lock();
                    if(!f.exists()) {
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
                }
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
