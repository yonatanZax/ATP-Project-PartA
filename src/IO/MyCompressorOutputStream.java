package IO;

import java.io.IOException;
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
