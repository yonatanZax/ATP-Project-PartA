package Server;

import algorithms.mazeGenerators.IMazeGenerator;
import algorithms.mazeGenerators.MyMazeGenerator;
import algorithms.mazeGenerators.SimpleMazeGenerator;
import algorithms.search.BestFirstSearch;
import algorithms.search.BreadthFirstSearch;
import algorithms.search.DepthFirstSearch;
import algorithms.search.ISearchingAlgorithm;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Configurations {

    private static Properties properties = new Properties();

    public static void run(){

        InputStream input = null;

        try {

            String filename = "./Resources/config.properties";
            input = new FileInputStream(filename);


            //load a properties file from class path, inside static method
            properties.load(input);

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally{
            if(input!=null){
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static boolean isNumeric(String str)
    {
        try
        {
            double d = Double.parseDouble(str);
        }
        catch(NumberFormatException nfe)
        {
            return false;
        }
        return true;
    }

    public static ISearchingAlgorithm getAlgorithms_solveAlgorithm() {
        switch (properties.getProperty("algorithms_solveAlgorithm")){
            case "BFS":
                return new BreadthFirstSearch();
            case "BestFirstSearch":
                return new BestFirstSearch();
            case "DFS":
                return new DepthFirstSearch();

        }
        return new BreadthFirstSearch();
    }

    public static IMazeGenerator getGenerators_mazeGenerator() {
        switch (properties.getProperty("algorithms_mazeGenerateAlgorithm")){
            case "simpleMazeGenerator":
                return new SimpleMazeGenerator();
            case "myMazeGenerator":
                return new MyMazeGenerator();
        }
        return new MyMazeGenerator();
    }

    public static int getServer_threadPoolSize() {
        if (isNumeric(properties.getProperty("server_threadPoolSize"))) {
            int temp = Integer.parseInt(properties.getProperty("server_threadPoolSize"));
            if (temp > 0)
                return temp;
        }
        return 3;
    }
}
