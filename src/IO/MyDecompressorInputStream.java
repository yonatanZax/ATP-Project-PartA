package IO;

import java.io.IOException;
import java.io.InputStream;

public class MyDecompressorInputStream extends InputStream {

    private InputStream in;

    public MyDecompressorInputStream(InputStream in) {
        this.in = in;
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
        if (row_one<0)
            row_one += 256;
        if (row_two<0)
            row_two += 256;
        if (col_one<0)
            col_one += 256;
        if (col_two<0)
            col_two += 256;

        int maxValues = (row_one*256 + row_two) * (col_one*256 + col_two);
        int limit = 8;

        for(int i = 4; i < 12; i ++)
            bytes[i] = (byte)in.read();

        int curIndex = 12;
        int next = in.read();
        int charAtInt = 8;
        while (next != -1){
            String nextBinary = Integer.toBinaryString(next);
            while(nextBinary.length() < 8)
                nextBinary = "0" + nextBinary;
            if(maxValues < 8)
                limit = maxValues;

            for (int j = limit; j > 0; j--) {
               charAtInt = Math.min(limit,nextBinary.length());
               byte b = (byte) ((nextBinary.charAt(charAtInt - j) == '1') ? 1 : 0);
               bytes[curIndex++] = b;

            }
            maxValues -= 8;
            next = in.read();
        }



        return 0;
    }


    @Override
    public int read() throws IOException {
        return 0;
    }
}
