package algorithms.mazeGenerators;


/**    The maze is represented as a 2D char array.
 *     Zero ('0') = Pass
 *     One ('1') = Wall
 *     Start ('S') = The starting position
 *     End ('E') = The "Goal" position
 */
public class Maze
{

    private char[][] map; //the actual maze
    private Position startPostion; // representing the start Position
    private Position goalPosition; // representing the exit Position


    /**
     * A Constructor to create a new maze
     * @param map represents the 2D maze. can't be null
     * @param sp represent the start Position, can't be null, need to match the 'S' in the map
     * @param gp represent the exit Position, can't be null, need to match the 'S' in the map
     * @throws Exception if a given parameter isn't valid it will throw an 'Exception' with the problem message.
     */
    public Maze(char[][] map, Position sp, Position gp) throws Exception {
        if(map == null || sp == null || gp == null) // check for null parameters
            throw new Exception("Maze error: can't initial Maze with null arguments");
        // check if the map really have 'E' and 'S' at goalPosition and StartPosition and the map only contains '1', '0', 'S' and 'E'.
        if (!checkPositionsCorrectness(map,sp,gp) || !checkMapCorrectness(map))
            throw new Exception("Maze error: can't initial Maze with wrong positions arguments");
        this.map = map;
        startPostion = sp;
        goalPosition = gp;
    }


    /**
     * default content start with 0
     * seperated with "-" in the big string
     * seperated with "," in the index's
     * Size = stringArr[0] - row,col
     * start = stringArr[1] - row,col
     * Goal = stringArr[2] - row,col
     * Maze = stringArr[3] - #,#,#,#,
     * @return
     */
    public Maze(byte[] byteMaze){
        String byteMazeString = new String(byteMaze);
        String[] stringArr = byteMazeString.split("-");
        String[] stringSize = stringArr[0].split(",");
        String[] stringStart = stringArr[1].split(",");
        String[] stringGoal = stringArr[2].split(",");
        String[] stringMaze = stringArr[3].split(",");

        startPostion = new Position(Integer.valueOf(stringStart[0]),Integer.valueOf(stringStart[1]));
        goalPosition = new Position(Integer.valueOf(stringGoal[0]),Integer.valueOf(stringGoal[1]));
        map = new char[Integer.valueOf(stringSize[0])][Integer.valueOf(stringSize[1])];

        char curChar = '0';
        int indexInStringMaze = 1;
        int numberFromStringMaze = Integer.valueOf(stringMaze[0]);
        for(int i=0 ; i< map.length ; i++){
            for (int j = 0; j < map[0].length; j ++){
                while (numberFromStringMaze == 0 && indexInStringMaze < stringMaze.length){
                    numberFromStringMaze = Integer.valueOf(stringMaze[indexInStringMaze]);
                    indexInStringMaze++;
                    curChar = ((curChar == '0') ? '1': '0');
                }
                map[i][j] = curChar;
                numberFromStringMaze--;
            }
        }
        map[startPostion.getRowIndex()][startPostion.getColumnIndex()] = 'S';
        map[goalPosition.getRowIndex()][goalPosition.getColumnIndex()] = 'E';
    }

    /*** Getters ***/

    public Position getStartPosition() {
        return new Position(startPostion);
    }


    public Position getGoalPosition(){
        return new Position(goalPosition);
    }


    /**
     *     Print method: simply prints the char 2D array values
     *     Example: A random maze (6 by 6)
     *
     *     			011101
     *     			S00101
     *     			010001
     *     			001111
     *     			100E00
     *     			110110
     */
    public void print() {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++)
                System.out.print(map[i][j]);
            System.out.println();
        }
    }



    /**
     * 	Gets what is in the map at the location given in the parameters.
     * 	@param row The row you want to check.
     * 	@param col The column you want to check.
     * 	@return Returns the char, if the parameters are not in the map will return '\0' .
     */
    public char getCharAt(int row, int col){
        if ((row < map.length && col < map[0].length) && row >= 0 && col >= 0){
            return map[row][col];
        }
        return '\0';
    }


    /**
     * Check if the map really have 'E' and 'S' at goalPosition and StartPosition.
     * @param map the map it checks.
     * @param sp the start Position.
     * @param gp the Exit Postion.
     * @return true if it really have 'E' and 'S' at the spesified locations, else false.
     */
    private boolean checkPositionsCorrectness(char[][] map, Position sp, Position gp) {
        try{
            int spRow = sp.getRowIndex();
            int spCol = sp.getColumnIndex();
            int gpRow = gp.getRowIndex();
            int gpCol = gp.getColumnIndex();
            if(map[spRow][spCol] == 'S' && map[gpRow][gpCol] == 'E')
                return true;
        }catch(Exception e){
            return false;
        }
        return false;
    }

    /**
     * Checks the given map contains only: '1', '0', 'S' and 'E'.
     * @param map The map you want to check.
     * @return true if the map comstains only '1', '0', 'S' and 'E',
     * 			false if any cell contains something else.
     */
    private boolean checkMapCorrectness(char[][] map) {
        for(int i = 0; i < map.length; i ++){
            for(int j = 0; j < map[i].length; j ++){
                if (map[i][j] != 'E' && map[i][j] != 'S' && map[i][j] != '1' && map[i][j] != '0')
                    return false;
            }
        }
        return true;
    }

    /**
     * default content start with 0
     * seperated with "-" in the big string
     * seperated with "," in the index's
     * Size = stringArr[0] - row,col
     * start = stringArr[1] - row,col
     * Goal = stringArr[2] - row,col
     * Maze = stringArr[3] - #,#,#,#,
     * example - "10,20-5,6-9,4-1,3,5,3,6,8,2"
     * @return
     */
    public byte[] toByteArray(){
        String byteString = "";
        byteString = map.length + "," + map[0].length + "-";
        byteString += startPostion.getRowIndex() + "," + startPostion.getColumnIndex() + "-";
        byteString += goalPosition.getRowIndex() + "," + goalPosition.getColumnIndex() + "-";
        byteString += generateMazeString();
        byte[] byteArr = byteString.getBytes();
        return byteArr;
    }

    private String generateMazeString() {
        String longString = "";
        for (int i = 0; i < map.length; i++){
            longString += String.valueOf(map[i]);
        }
        String compressedMazeString = "";
        char curNumber = '0';
        int counter = 0;
        longString = longString.replace('E','0' );
        longString = longString.replace('S','0' );

        for (int i = 0; i < longString.length(); i ++){
            if (curNumber == longString.charAt(i)){
                counter++;
            }
            else{
                compressedMazeString += counter + ",";
                counter = 0;
                curNumber = longString.charAt(i);
                i--;
            }
        }
        compressedMazeString += counter;
        return compressedMazeString;
    }

    private boolean equals(Maze maze2){
        for (int i = 0; i < map.length; i ++){
            for (int j = 0; j < map[0].length ;j ++){
                if (map[i][j] != maze2.map[i][j])
                    return false;
            }
        }
        return true;
    }
/*
    public static void main(String[] args) {
        char[][] map = {{'S','1','0','1','1'},
                        {'0','1','1','0','1'},
                        {'0','1','1','0','1'},
                        {'0','0','0','0','1'},
                        {'0','0','1','E','1'},
                        {'0','0','0','1','1'}};
        Maze maze;
        Maze maze2;
        MyMazeGenerator mg = new MyMazeGenerator();
        try {
            maze = mg.generate(1000,1000 );
            maze2 = new Maze(maze.toByteArray());
            System.out.println(maze.equals(maze2));
        }catch (Exception e){
            System.out.println(e.getMessage());
        }


    }*/
}

