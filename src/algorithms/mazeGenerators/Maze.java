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
     * Size = (numOfBytes),mazeRow,(numOfBytes),mazeCol
     * start = (numOfBytes),startRow,(numOfBytes),startCol
     * Goal = (numOfBytes),goalRow,(numOfBytes),goalCol
     * Beginning of String example = "3,255,255,90,   3,255,255,90,  1,15,1,0,  3,255,245,1,240
     * Maze = byteMaze[3] - #,#,#,#,
     * @return
     */
    public Maze(byte[] byteMaze){
        String[] stringArr = new String[byteMaze.length];
        for (int i=0 ; i< byteMaze.length ; i++){
            stringArr[i] = String.valueOf(byteMaze[i]);
        }
        int[] initMazeSizes = getCompressedMazeValues(stringArr);

        map = new char[Integer.valueOf(initMazeSizes[0])][Integer.valueOf(initMazeSizes[1])];
        startPostion = new Position(Integer.valueOf(initMazeSizes[2]),Integer.valueOf(initMazeSizes[3]));
        goalPosition = new Position(Integer.valueOf(initMazeSizes[4]),Integer.valueOf(initMazeSizes[5]));
        int mazeFirstValue = initMazeSizes[6];

        char curChar = '0';
        int indexInStringMaze = 1;
        int numberFromStringMaze = Integer.valueOf(stringArr[0+mazeFirstValue]);
        for(int i=0 ; i< map.length ; i++){
            for (int j = 0; j < map[0].length; j ++){
                while (numberFromStringMaze == 0 && indexInStringMaze < stringArr.length){
                    numberFromStringMaze = Integer.valueOf(stringArr[indexInStringMaze]);
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


    /**
     * Makes an array of Coordinates from the longString
     "2,255,45,2,255,45,1,150,1,0,2,255,30,1,50,0,1,1,0,1,0..."
     result = {300,300,150,0,285,50,15}
     Size = result[0]*result[1] = 300*300
     Start = (result[2],result[3]) = (150,0)
     Goal = (result[4],result[5]) = (285,50)
     Maze starting index = result[6] = 15
     * @param longString
     * @return
     */
    private int[] getCompressedMazeValues(String[] longString){
        int[] result = new int[7];
        int curIndex = 0;
        int nextIndex = 0;
        int sum =0;
        int stringIndex =0;
        while(stringIndex<6){
            nextIndex += Integer.valueOf(longString[curIndex]) + 1;
            curIndex++;
            for (int i=curIndex; i<nextIndex ; i++){
                sum += Integer.valueOf(longString[i]);
            }
            curIndex = nextIndex;
            result[stringIndex] = sum;
            sum = 0; stringIndex++;
        }
        result[6] = nextIndex;
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


    //ToDo - toByteArray method
    /**
     * default content start with 0
     * Size = (numOfBytes),mazeRow,(numOfBytes),mazeCol
     * start = (numOfBytes),startRow,(numOfBytes),startCol
     * Goal = (numOfBytes),goalRow,(numOfBytes),goalCol
     * Beginning of String example = "3,255,255,90,   3,255,255,90,  1,15,1,0,  3,255,245,1,240
     * Maze = (After all the mazeCompressedCoordinates),#,#,#,#,
     * example -
     * @return
     */
    public byte[] toByteArray(){
        String s = getCompressedCoordinates(600);
        String byteString = "";
        byteString = getCompressedCoordinates(map.length) + "," + getCompressedCoordinates(map[0].length) + ",";
        byteString += getCompressedCoordinates(startPostion.getRowIndex()) + "," + getCompressedCoordinates(startPostion.getColumnIndex()) + ",";
        byteString += getCompressedCoordinates(goalPosition.getRowIndex()) + "," + getCompressedCoordinates(goalPosition.getColumnIndex()) + ",";
        byteString += generateMazeString();
        String[] splited = byteString.split(",");

        byte[] byteArr = new byte[splited.length];
        for (int i=0; i<splited.length ; i++){
            String temp = String.valueOf(Integer.valueOf(splited[i])-128);
            byteArr[i] = Byte.valueOf(temp);
        }
        return byteArr;
    }

    // Makes a string with a comma separator from char[]
    private String joinString(char[] row){
        String result = "";
        for (char c : row) {
            result += String.valueOf(c) + ',';
        }
        return result.substring(0,result.length()-1);
    }

    // Makes numbers to a compressed String, Example:
    // 300 = "255,45"
    private String getCompressedCoordinates(int n){
        String result = "";
        while(n>255){
            result += "255,";
            n -= 255;
        }
        result += String.valueOf(n);
        String[] arr = result.split(",");
        return arr.length +"," + result;
    }

    //Makes a long String from a char[][] maze
    private String generateMazeString() {
        String longString = "";
        for (int i = 0; i < map.length; i++){
            String temp = joinString(map[i]);
            longString += temp;
        }
        //String compressedMazeString = "";
        //char curNumber = '0';
        //int counter = 0;
        longString = longString.replace('E','0' );
        longString = longString.replace('S','0' );



        // Old compress
        /*for (int i = 0; i < longString.length(); i ++){
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
        */
        return longString;
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

