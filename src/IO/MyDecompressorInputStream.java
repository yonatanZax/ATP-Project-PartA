package IO;

import java.io.IOException;
import java.io.InputStream;

public class MyDecompressorInputStream extends InputStream {

    InputStream in;

    public MyDecompressorInputStream(InputStream in) {
        this.in = in;
    }

    @Override
    public int read() throws IOException {
        int i1 = in.read();
        System.out.println(in.read());
        int i2 = in.read();
        System.out.println(in.read());
        int i3 = in.read();
        int i4 = in.read();
        int i5 = in.read();
        int i6 = in.read();
        int i7 = in.read();
        int i8 = in.read();
        int i9 = in.read();
        int i10 = in.read();
        int i11 = in.read();
        int i12 = in.read();
        int i13 = in.read();
        int i14 = in.read();
        int i15 = in.read();
        int i16 = in.read();
        return in.read();
    }
}
