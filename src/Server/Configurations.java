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

    private static ISearchingAlgorithm algorithms_solveAlgorithm;
    private static IMazeGenerator generators_mazeGenerator;
    private static int server_threadPoolSize = 3;


    public static void run(){
        Properties properties = new Properties();
        InputStream input = null;

        try {

            String filename = "./Resources/config.properties";
            input = new FileInputStream(filename);
            if(input==null){
                System.out.println("Sorry, unable to find " + filename);
                return;
            }

            //load a properties file from class path, inside static method
            properties.load(input);


            switch (properties.getProperty("algorithms_solveAlgorithm")){
                case "BFS":
                    algorithms_solveAlgorithm = new BreadthFirstSearch();
                case "BestFirstSearch":
                    algorithms_solveAlgorithm = new BestFirstSearch();
                case "DFS":
                    algorithms_solveAlgorithm = new DepthFirstSearch();
                default:
                    algorithms_solveAlgorithm = new BreadthFirstSearch();
            }

            switch (properties.getProperty("algorithms_mazeGenerateAlgorithm")){
                case "simpleMazeGenerator":
                    generators_mazeGenerator = new SimpleMazeGenerator();
                case "myMazeGenerator":
                    generators_mazeGenerator = new MyMazeGenerator();
                default:
                    generators_mazeGenerator = new MyMazeGenerator();

            }

            if (isNumeric(properties.getProperty("server_threadPoolSize"))) {
                int temp = Integer.parseInt(properties.getProperty("server_threadPoolSize"));
                if (temp > 0)
                    server_threadPoolSize = temp;
            }


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

    public static boolean isNumeric(String str)
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
        return algorithms_solveAlgorithm;
    }

    public static IMazeGenerator getGenerators_mazeGenerator() {
        return generators_mazeGenerator;
    }

    public static int getServer_threadPoolSize() {
        return server_threadPoolSize;
    }
}
