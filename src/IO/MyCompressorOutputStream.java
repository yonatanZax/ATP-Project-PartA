package IO;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

public class MyCompressorOutputStream extends OutputStream {

    private OutputStream out;
    private int counter;
    private int next;
    private int values;
    private int curValue;

    public MyCompressorOutputStream(OutputStream outPutStream){
        out = outPutStream;
        counter = 0;
        values = 6;
        curValue = 0;
        next = 0;

        //Todo mplement
    }
    @Override
    public void write(byte[] bytes) throws IOException{
        System.out.println("MY COMPRESSOR - WRITE");

        for(int i = 0; i < 12; i ++)
            out.write((int)bytes[i]);
        byte[] eightBits;
        for(int i = 12; i < bytes.length; i ++){
            int limit = Math.min(7, bytes.length - i);
            eightBits = new byte[limit];
            byte sum = 0;
            for(int j = 0; j < limit; j++, i++){
                eightBits[j] = bytes[i];
            }
            for(int j = 0; j < limit; j ++){
                if (eightBits[j] == 1)
                    sum += Math.pow(2, 7 - j);
            }
            out.write((int)sum);
        }

    }



    @Override
    public void write(int b) throws IOException {
        //TODO implement

        int plus_128 = b;
        //int plus_128 = b;
        if (values>=0) {
            if (next == 0){
                next = plus_128;
                out.write(next);
                values--;
            }
            else {
                if (next > 0){
                    out.write(plus_128);
                next--;
                    if (values==0 && next==0)
                values--;
                    return;
                }
            }

        }else{
            if (curValue == plus_128)
                counter++;
            else {
                out.write(counter);
                curValue = plus_128;
                counter=1;
                return;
            }
        }

    }

}


