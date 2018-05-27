package Server;

import algorithms.search.BestFirstSearch;
import algorithms.search.BreadthFirstSearch;
import algorithms.search.DepthFirstSearch;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Configurations {

    private static String algorithms_searchAlgorithm;
    private static String generators_mazeGenerator;
    private static String server_threadPoolSize;


    public static void main( String[] args ){

        Properties prop = new Properties();
        InputStream input = null;

        try {

            String filename = "./Resources/config.properties";
            input = new FileInputStream(filename);
            if(input==null){
                System.out.println("Sorry, unable to find " + filename);
                return;
            }

            //load a properties file from class path, inside static method
            prop.load(input);
            String search = prop.getProperty("serachAlgorithm");
            if (search == "BFS"){
                ServerStrategySolveSearchProblem.searchAlgorithm = new BreadthFirstSearch();
            }
            else if (search == "DFS")
                ServerStrategySolveSearchProblem.searchAlgorithm = new DepthFirstSearch();
            else
                ServerStrategySolveSearchProblem.searchAlgorithm = new BestFirstSearch();
            //get the property value and print it out
            System.out.println(prop.getProperty("database"));
            System.out.println(prop.getProperty("dbuser"));
            System.out.println(prop.getProperty("dbpassword"));

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

}