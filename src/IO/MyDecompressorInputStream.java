package IO;

import jdk.jfr.Unsigned;

import java.io.IOException;
import java.io.InputStream;

public class MyDecompressorInputStream extends InputStream {

    private InputStream in;
    private int values;
    private int next;
    private int curValue;
    private boolean valuesFlag;
    private boolean writeFlag;
    private int counter;

    public MyDecompressorInputStream(InputStream in) {
        this.in = in;
        values = 6;
        next = 0;
        curValue = 0;
        counter=0;
        valuesFlag = false;
        writeFlag = false;
    }



    @Override
    public int read() throws IOException {
        while (!valuesFlag){
            int temp = curValue;
            if (values>=0) {
                if (next == 0){
                    next = in.read();
                    values--;
                    return next;
                }
                else {
                    if (next > 0){
                        curValue = in.read();
                        next--;
                        temp = curValue;
                        if (values==0 && next==0){
                            values--;
                            temp = curValue;
                            curValue=0;
                            valuesFlag = true;
                        }
                        return temp;
                    }
                }
            }
        }

        if (!writeFlag){
            counter = in.read();
            if (counter==-1)
                return curValue;
            if (counter==0){
                counter = in.read();
                curValue = ((curValue == 1) ? 0: 1);

            }
            writeFlag = true;
        }

        if (counter>1){
            counter--;
            return curValue;
        }
        writeFlag = false;
        counter--;


        int temp = curValue;
        curValue = ((curValue == 1) ? 0: 1);
        return temp;
    }
}
