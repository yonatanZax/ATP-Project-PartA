package IO;

import java.io.IOException;
import java.io.OutputStream;

public class MyCompressorOutputStream extends OutputStream {

    OutputStream out;
    int counter;
    int lastB;

    public MyCompressorOutputStream(OutputStream outPutStream){
        out = outPutStream;
        counter = 0;
        lastB = 0;
        //Todo mplement
    }

    @Override
    public void write(int b) throws IOException {
        //TODO implement
        out.write(b);
    }
}
