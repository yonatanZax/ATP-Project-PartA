package algorithms.search;

import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.Position;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class SearchableMazeTest {

    @Test
    void getAllPossibleStates() {
        char[][] map = {{'S','1','0','1','1'},
                         {'0','0','1','0','1'},
                         {'0','1','1','0','1'},
                         {'0','0','0','0','1'},
                         {'0','0','0','E','1'},
                         {'0','0','0','1','1'}};
        String[] check = {"{0,0}" ,"{1,0}","{2,0}"};
        Maze maze = null;
        try {
            maze = new Maze(map, new Position(0, 0, null), new Position(4, 3, null));
        }catch (Exception e){

        }
        ISearchable searchableMaze = new SearchableMaze(maze);
        ArrayList<AState> list = searchableMaze.getAllPossibleStates(new MazeState(new Position(1,1,null)));
        for (int i = 0; i < 3 ;i++) {
            String s = ((MazeState) list.get(i)).getPosition().toString();
            assertArrayEquals(check[i].toCharArray(), s.toCharArray());
        }
        System.out.println("getAllPossibleStates test Passed!");
    }

    @Test
    void getGoalState() {
    }

    @Test
    void getStartState() {
    }
}