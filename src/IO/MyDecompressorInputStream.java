package IO;

import java.io.IOException;
import java.io.InputStream;

public class MyDecompressorInputStream extends InputStream {

    private InputStream in;

    // Values - We are expecting 6 values for mapSize,startPosition,goalPosition
    private int values;
    /* Next
    * Before every coordinate we have a number that tells us how many numbers we need
    * Example: "2,255,45" = 255+45 = 300
    * */
    //private int next;
    /* At first it saves
    * */
    private int curValue;
    private boolean valuesFlag;
    private boolean writeFlag;
    private int counter;

    public MyDecompressorInputStream(InputStream in) {
        this.in = in;
        values = 6;
        //next = 0;
        curValue = 0;
        counter=0;
        valuesFlag = false;
        writeFlag = false;
    }

    @Override
    public int read(byte[] bytes) throws IOException{
        System.out.println("MY DECOMPRESSED");

        int row_one = (byte)in.read();
        bytes[0] = (byte)row_one;
        int row_two = (byte)in.read();
        bytes[1] = (byte)row_two;
        int col_one = (byte)in.read();
        bytes[2] = (byte)col_one;
        int col_two = (byte)in.read();
        bytes[3] = (byte)col_two;
        int maxValues = (row_one*256 + row_two + col_one*256 + col_two);
        int limit = 8;

        for(int i = 4; i < 12; i ++)
            bytes[i] = (byte)in.read();

        int curIndex = 13;
        int next = in.read();
        while (next != -1){
            String nextBinary = Integer.toBinaryString(next);
            if(maxValues < 8)
                limit = maxValues;

            for (int j = limit; j > 0; j--) {
                if (j > nextBinary.length())
                    bytes[curIndex++] = 0;
                else{
                    byte b = (byte) ((nextBinary.charAt(nextBinary.length() - j) == '1') ? 1 : 0);
                    bytes[curIndex++] = b;
                }


            }
            next = in.read();
        }



        return 0;
    }


    @Override
    public int read() throws IOException {
        return 0;
    }
}
