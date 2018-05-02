
package algorithms.search;

import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;
import algorithms.mazeGenerators.Position;
import org.junit.jupiter.api.Test;

import java.rmi.MarshalException;

import static org.junit.jupiter.api.Assertions.*;

class JUnitTestingBestFirstSearch {

    @Test
    void testTime() {
        MyMazeGenerator mg = new MyMazeGenerator();
        Maze maze = mg.generate(1000,1000);
        SearchableMaze sm = null;
        try {
            sm = new SearchableMaze(maze);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        BestFirstSearch bestfs = new BestFirstSearch();
        long start = System.currentTimeMillis();
        bestfs.solve(sm);
        long finished = System.currentTimeMillis();
        boolean test = finished - start < 60000;
        if (test)
            System.out.println("BestFirstSearch Run Time test PASSED! time is: " + (finished - start) + " ms");
        else
            System.out.println("BestFirstSearch Run Time test FAILED!");
        assertTrue(test);
    }

    @Test
    void testNull(){
        BestFirstSearch bestfs = new BestFirstSearch();
        Solution solution = bestfs.solve(null);
        boolean test = solution == null;
        if (test)
            System.out.println("BestFirstSearch Null test PASSED!");
        else
            System.out.println("BestFirstSearch Null test FAILED!");
        assertTrue(test);
    }

    @Test
    void testUnSolvableMaze(){
        char[][] map = {{'S','1','0','1','1'},
                        {'0','0','1','0','1'},
                        {'0','1','1','0','1'},
                        {'0','0','0','1','1'},
                        {'0','0','1','E','1'},
                        {'0','0','0','1','1'}};
        boolean test = false;
        Solution solution = null;
        SearchableMaze sm = null;
        BestFirstSearch bestfs = null;
        try {
            Maze maze = new Maze(map, new Position(0, 0, null), new Position(4, 3, null));
            sm = new SearchableMaze(maze);
            bestfs = new BestFirstSearch();
        }catch (Exception e){ }
        solution = bestfs.solve(sm);
        test = solution == null;
        if (test)
            System.out.println("BestFirstSearch unsolvable test PASSED!");
        else
            System.out.println("BestFirstSearch unsolvable test FAILED!");
        assertTrue(test);
    }

    @Test
    void testMazeWithNull(){
        SearchableMaze sm = null;
        Maze maze = null;
        try {
            maze = new Maze(null, new Position(0, 0, null), new Position(4, 3, null));
            sm = new SearchableMaze(maze);
        }catch (Exception e){}
        BestFirstSearch bestfs = new BestFirstSearch();
        Solution solution = bestfs.solve(sm);
        boolean test = solution == null;
        if (test)
            System.out.println("BestFirstSearch MazeWithNull test PASSED!");
        else
            System.out.println("BestFirstSearch MazeWithNull test FAILED!");
        assertTrue(test);
    }

    @Test
    void testMazeWithNullStartPosition(){
        char[][] map = {{'S','1','0','1','1'},
                {'0','0','1','0','1'},
                {'0','1','1','0','1'},
                {'0','0','0','1','1'},
                {'0','0','0','E','1'},
                {'0','0','0','1','1'}};
        SearchableMaze sm = null;
        try {
            Maze maze = new Maze(map, null, new Position(4, 3, null));
            sm = new SearchableMaze(maze);
        }catch (Exception e){}
        BestFirstSearch bestfs = new BestFirstSearch();
        Solution solution = bestfs.solve(sm);
        boolean test = solution == null;
        if (test)
            System.out.println("BestFirstSearch testMazeWithNullStartPosition test PASSED!");
        else
            System.out.println("BestFirstSearch testMazeWithNullStartPosition test FAILED!");
        assertTrue(test);
    }

    @Test
    void testMazeWithNullGoalPosition(){
        char[][] map = {{'S','1','0','1','1'},
                {'0','0','1','0','1'},
                {'0','1','1','0','1'},
                {'0','0','0','1','1'},
                {'0','0','0','E','1'},
                {'0','0','0','1','1'}};
        SearchableMaze sm = null;
        try {
            Maze maze = new Maze(map, new Position(0, 0, null),null);
            sm = new SearchableMaze(maze);
        }catch (Exception e){}
        BestFirstSearch bestfs = new BestFirstSearch();
        Solution solution = bestfs.solve(sm);
        boolean test = solution == null;
        if (test)
            System.out.println("BestFirstSearch testMazeWithNullGoalPosition test PASSED!");
        else
            System.out.println("BestFirstSearch testMazeWithNullGoalPosition test FAILED!");
        assertTrue(test);
    }

    @Test
    void testMazeWithWrongGoalPosition(){
        char[][] map = {{'S','1','0','1','1'},
                {'0','0','1','0','1'},
                {'0','1','1','0','1'},
                {'0','0','0','1','1'},
                {'0','0','0','E','1'},
                {'0','0','0','1','1'}};
        SearchableMaze sm = null;
        try {
            Maze maze = new Maze(map, new Position(0, 0, null),new Position(4,0,null));
            sm = new SearchableMaze(maze);
        }catch (Exception e){}
        BestFirstSearch bestfs = new BestFirstSearch();
        Solution solution = bestfs.solve(sm);
        boolean test = solution == null;
        if (test)
            System.out.println("BestFirstSearch testMazeWithWrongGoalPosition test PASSED!");
        else
            System.out.println("BestFirstSearch testMazeWithWrongGoalPosition test FAILED!");
        assertTrue(test);
    }

    @Test
    void testMazeWithWrongStartPosition(){
        char[][] map = {{'S','1','0','1','1'},
                {'0','0','1','0','1'},
                {'0','1','1','0','1'},
                {'0','0','0','1','1'},
                {'0','0','0','E','1'},
                {'0','0','0','1','1'}};
        SearchableMaze sm = null;
        try {
            Maze maze = new Maze(map, new Position(1, 1, null),new Position(4,3,null));
            sm = new SearchableMaze(maze);
        }catch (Exception e){}
        BestFirstSearch bestfs = new BestFirstSearch();
        Solution solution = bestfs.solve(sm);
        boolean test = solution == null;
        if (test)
            System.out.println("BestFirstSearch testMazeWithWrongStartPosition test PASSED!");
        else
            System.out.println("BestFirstSearch testMazeWithWrongStartPosition test FAILED!");
        assertTrue(test);
    }

    @Test
    void testSolution(){
        char[][] map = {{'S','1','0','1','1'},
                        {'0','1','1','0','1'},
                        {'0','1','1','0','1'},
                        {'0','0','0','0','1'},
                        {'0','0','1','E','1'},
                        {'0','0','0','1','1'}};
        SearchableMaze sm = null;
        String mySolution = "Solution path:\n" +
                            "1. {0,0}\n" +
                            "2. {1,0}\n" +
                            "3. {2,0}\n" +
                            "4. {3,1}\n" +
                            "5. {3,2}\n" +
                            "6. {4,3}\n" ;
        try {
            Maze maze = new Maze(map, new Position(0, 0, null),new Position(4,3,null));
            sm = new SearchableMaze(maze);
        }catch (Exception e){}
        BestFirstSearch bestfs = new BestFirstSearch();
        Solution solution = bestfs.solve(sm);
        boolean test = solution.toString().equals(mySolution);
        if (test)
            System.out.println("BestFirstSearch testSolution test PASSED!");
        else
            System.out.println("BestFirstSearch testSolution test FAILED!");
        assertTrue(test);
    }

    @Test
    void testSolutionNumberOfEvaluated(){
        char[][] map = {{'S','1','0','1','1'},
                        {'0','1','1','0','1'},
                        {'0','1','1','0','1'},
                        {'0','0','0','0','1'},
                        {'0','0','1','E','1'},
                        {'0','0','0','1','1'}};
        SearchableMaze sm = null;
        try {
            Maze maze = new Maze(map, new Position(0, 0, null),new Position(4,3,null));
            sm = new SearchableMaze(maze);
        }catch (Exception e){}
        BestFirstSearch bestfs = new BestFirstSearch();
        Solution solution = bestfs.solve(sm);
        boolean test = bestfs.getNumberOfNodesEvaluated() == 13;
        if (test)
            System.out.println("BestFirstSearch testSolutionNumberOfEvaluated test PASSED!");
        else
            System.out.println("BestFirstSearch testSolutionNumberOfEvaluated test FAILED!");
        assertTrue(test);
    }

    @Test
    void testInvalidMap(){
        char[][] map = {{'S','1','0','1','1'},
                        {'0','1','1','0','1'},
                        {'0','2','1','0','1'},
                        {'0','0','0','0','1'},
                        {'0','0','1','E','1'},
                        {'0','0','0','1','1'}};
        SearchableMaze sm = null;
        try {
            Maze maze = new Maze(map, new Position(0, 0, null),new Position(4,3,null));
            sm = new SearchableMaze(maze);
        }catch (Exception e){}
        BestFirstSearch bestfs = new BestFirstSearch();
        Solution solution = bestfs.solve(sm);
        boolean test = solution == null;
        if (test)
            System.out.println("BestFirstSearch testInvalidMap test PASSED!");
        else
            System.out.println("BestFirstSearch testInvalidMap test FAILED!");
        assertTrue(test);
    }

    @Test
    void testMultipleUses(){
        MyMazeGenerator mg = new MyMazeGenerator();
        Maze maze = mg.generate(30,30 );
        SearchableMaze sm = new SearchableMaze(maze);
        BestFirstSearch bestFS = new BestFirstSearch();
        bestFS.solve(sm);

        int numOfEvaluatedNodes = bestFS.getNumberOfNodesEvaluated();
        
        bestFS.solve(sm);
        boolean test = numOfEvaluatedNodes == bestFS.getNumberOfNodesEvaluated();
        if (test)
            System.out.println("BestFirstSearch testMultipleUses test PASSED!");
        else
            System.out.println("BestFirstSearch testMultipleUses test FAILED!");
        assertTrue(test);
    }
}