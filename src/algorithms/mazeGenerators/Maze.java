package algorithms.mazeGenerators;


import java.io.Serializable;

/**    The maze is represented as a 2D char array.
 *     Zero ('0') = Pass
 *     One ('1') = Wall
 *     Start ('S') = The starting position
 *     End ('E') = The "Goal" position
 */
public class Maze implements Serializable
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
     */
    public Maze(byte[] byteMaze){
        String[] stringArr = new String[byteMaze.length];
        // Convert byte[] byteMaze to String[] stringArr
        for (int i=0 ; i< byteMaze.length ; i++){
            stringArr[i] = String.valueOf(byteMaze[i]);
        }
        // Gets the values for map,Start,End
        int[] initMazeSizes = getCompressedMazeValues(stringArr);

        map = new char[Integer.valueOf(initMazeSizes[0])][Integer.valueOf(initMazeSizes[1])];
        startPostion = new Position(Integer.valueOf(initMazeSizes[2]),Integer.valueOf(initMazeSizes[3]));
        goalPosition = new Position(Integer.valueOf(initMazeSizes[4]),Integer.valueOf(initMazeSizes[5]));
        // The index of the first char of the maze


        // Make the map from the stringArr
        int indexInByteMaze = 12;
        for(int i = 0 ; i < map.length ; i++){
            for (int j = 0; j < map[0].length; j++){
                map[i][j] = stringArr[indexInByteMaze].charAt(0);
                indexInByteMaze++;
            }
        }
        //Update startPosition and goalPosition
        map[startPostion.getRowIndex()][startPostion.getColumnIndex()] = 'S';
        map[goalPosition.getRowIndex()][goalPosition.getColumnIndex()] = 'E';
    }


    /**
     * Makes an array of Coordinates from the longString
     "1,45,1,45,0,150,0,0,1,30,0,50,0,1,1,0,1,0..."
     result = {300,300,150,0,285,50,15}
     Size = result[0]*result[1] = 300*300
     Start = (result[2],result[3]) = (150,0)
     Goal = (result[4],result[5]) = (285,50)
     * @param longString - all the data (not compressed)
     * @return int array with the values for map,Start,End
     */
    private int[] getCompressedMazeValues(String[] longString){
        int[] result = new int[6];
        for (int i = 0; i < 6; i ++){
            int b = Byte.valueOf(longString[i*2+1]);
            if (b < 0)
                b += 256;
            result[i] = Integer.valueOf(longString[i*2])*256 + b;

            }
        return result;
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
     * Size = (numOfBytes),mazeRow,(numOfBytes),mazeCol
     * start = (numOfBytes),startRow,(numOfBytes),startCol
     * Goal = (numOfBytes),goalRow,(numOfBytes),goalCol
     * Beginning of String example =    2,90             2,90,      0,15 0,0    1,245  0,240
     *                              MazeRows = 600    MazeCols=600   S(15,0)    End(500,240)
     * Maze = "1,0,0,1,0,1,0,1,0,1,0....."
     * @return byte[] byteArray - not compressed
     */
    public byte[] toByteArray(){
        String byteString = "";
        byteString = getCompressedCoordinates(map.length) + "," + getCompressedCoordinates(map[0].length) + ",";
        byteString += getCompressedCoordinates(startPostion.getRowIndex()) + "," + getCompressedCoordinates(startPostion.getColumnIndex()) + ",";
        byteString += getCompressedCoordinates(goalPosition.getRowIndex()) + "," + getCompressedCoordinates(goalPosition.getColumnIndex()) + ",";
        byteString += generateMazeString();
        // Makes a string[] split by comma
        String[] splited = byteString.split(",");

        //Builds byte[] from the splited Sting[]
        byte[] byteArr = new byte[splited.length];
        for (int i=0; i<splited.length ; i++){
            String temp = String.valueOf(Integer.valueOf(splited[i]));
            byteArr[i] = (byte)(int)Integer.valueOf(temp);
        }
        return byteArr;
    }

    // Makes a string with a comma separator from char[]
    private String joinString(char[] row){
        String result = "";
        for (char c : row) {
            result += String.valueOf(c) + ',';
        }
        return result.substring(0,result.length());
    }

    // Makes numbers to a compressed String, Example:
    // 300 = "1,44"
    private String getCompressedCoordinates(int n){
        String ans = "";
        int divided = n / 256;
        int leftOver = n % 256;
        ans = divided + "," + leftOver;
        return ans;
    }


    //Makes a long String from a char[][] maze
    private String generateMazeString() {
        String longString = "";
        for (int i = 0; i < map.length; i++){
            String temp = joinString(map[i]);
            longString += temp;
        }

        // replaces E,S with'0'
        longString = longString.replace('E','0' );
        longString = longString.replace('S','0' );

        return longString.substring(0,longString.length()-1);
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
            //maze.print();
            maze2 = new Maze(maze.toByteArray());
            System.out.println(maze.equals(maze2));
        }catch (Exception e){
            System.out.println(e.getMessage());
        }


    }
}

