package com.smartTrade.backend.Utils;
import java.io.File;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
 
public class QRGenerator 
{
    public File generateQR(File file, String text, int h, int w) throws Exception
    {
       Charset charset = Charset.forName("ISO-8859-1");
       CharsetEncoder encoder = charset.newEncoder();
       byte[] b = null;
       ByteBuffer bb = encoder.encode(CharBuffer.wrap(text));
       b = bb.array();
       String data = new String(b, "ISO-8859-1");
       
       BitMatrix matrix = null;
       QRCodeWriter writer = new QRCodeWriter();
       matrix = writer.encode(data, com.google.zxing.BarcodeFormat.QR_CODE, w, h);
       MatrixToImageWriter.writeToFile(matrix, "PNG", file);
       return file;
    }
}
