package algorithms.mazeGenerators;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SimpleMazeGeneratorTest {


    @Test
    public void testSimpleMazeGenRunTime() {
        int successes = 0;
        int count = 0;
        int rounds = 50;
        SimpleMazeGenerator simpleMaze = new SimpleMazeGenerator();
        long checkTime =0;
        long avgTime = 0;
        boolean test = false;

        while(count<rounds){
            checkTime = simpleMaze.measureAlgorithmTimeMillis(1000,1000);
            test = checkTime < 60000;
            if (test){
                successes++;
                avgTime+= checkTime;
            }
            else{
                System.out.println("SimpleMazeGen RunTime test Failed! \n"
                        +"Finished in: " + checkTime);
                break;
            }
            count++;
        }
        test = successes == rounds;
        if (test){
            System.out.println("SimpleGen RunTime test Passed!");
            System.out.println("Avg RunTime: "+ avgTime/rounds + " Milisec.");
        }

        assertTrue(test);
    }



    @Test
    void testStartPosition(){
        SimpleMazeGenerator mg = new SimpleMazeGenerator();
        Maze m = mg.generate(1000,1000);
        Position st = m.getStartPosition();
        boolean test = 'S' == m.getCharAt(st.getRowIndex(),st.getColumnIndex());
        if (test)
            System.out.println("StartPosition test Passed!");
        else
            System.out.println("StartPosition test Failed! \n" +
                    "char at startPosition: " + m.getCharAt(st.getRowIndex(),st.getColumnIndex())
                    + "startPosition: " + st);

        assertTrue(test);
    }

    @Test
    void testGoalPosition(){
        SimpleMazeGenerator mg = new SimpleMazeGenerator();
        Maze m = mg.generate(1000,1000);
        Position gp = m.getGoalPosition();
        boolean test = 'E' == m.getCharAt(gp.getRowIndex(),gp.getColumnIndex());
        if (test)
            System.out.println("GoalPosition test Passed!");
        else
            System.out.println("GoalPosition test Failed! \n" +
                    "char at goalPosition: " + m.getCharAt(gp.getRowIndex(),gp.getColumnIndex())
                    + "goalPosition: " + gp);

        assertTrue(test);
    }

}
