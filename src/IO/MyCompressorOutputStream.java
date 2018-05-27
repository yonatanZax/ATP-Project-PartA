package IO;

import java.io.*;

public class MyCompressorOutputStream extends OutputStream {

    private OutputStream out;

    public MyCompressorOutputStream(OutputStream outPutStream) {

        this.out = outPutStream;

    }

    @Override
    public void write(byte[] bytes) throws IOException {
        System.out.println("MY COMPRESSOR - WRITE");
        byte[] tempSendBytes = new byte[bytes.length];
        int sendBytesIndex = 0;
        for (int i = 0; i < 12; i++)
            tempSendBytes[i] = bytes[i];
        //out.write((int) bytes[i]);
        sendBytesIndex = 12;
        byte[] eightBits;
        for (int i = 12; i < bytes.length; ) {
            int limit = Math.min(8, bytes.length - i);
            eightBits = new byte[limit];
            byte sum = 0;
            for (int j = 0; j < limit; j++, i++) {
                eightBits[j] = bytes[i];
            }
            for (int j = 0; j < limit; j++) {
                if (eightBits[j] == 1)
                    sum += Math.pow(2, 7 - j);
            }
            tempSendBytes[sendBytesIndex++] = sum;
            //out.write((int)sum);
        }
        byte[] sendBytes = new byte[sendBytesIndex];
        for (int i = 0; i < sendBytesIndex; i++)
            sendBytes[i] = tempSendBytes[i];


        out.write(sendBytes);




    }


    @Override
    public void write(int b) throws IOException {
        System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
    }
}


