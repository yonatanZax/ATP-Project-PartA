package IO;

import java.io.IOException;
import java.io.OutputStream;

public class MyCompressorOutputStream extends OutputStream {

    OutputStream out;
    private int counter;
    private int next;
    private int values;
    private int curValue;

    public MyCompressorOutputStream(OutputStream outPutStream){
        out = outPutStream;
        counter = 0;
        values = 6;
        curValue = 0;
        next = -1;

        //Todo mplement
    }

    @Override
    public void write(int b) throws IOException {
        //TODO implement
        int plus_128 = b+128;
        if (values>0) {
            if (next == -1)
                next = plus_128;
            else {
                curValue += plus_128;
                next--;
                if (next > 0)
                    return;

                out.write(curValue);
                curValue = 0;
                values--;
                next = -1;
            }

        }else{
            if (values==0) {
                curValue = 0;
                values = -1;
            }
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
