package com.smartTrade.backend.Utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.smartTrade.backend.Factory.ConverterFactory;
import com.smartTrade.backend.Template.PNGConverter;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class QRGenerator {

    private static void generateQRcode(String data, String path, String charset, Map map, int h, int w) throws WriterException, IOException {
        BitMatrix matrix = new MultiFormatWriter().encode(new String(data.getBytes(charset), charset), BarcodeFormat.QR_CODE, w, h);
        MatrixToImageWriter.writeToFile(matrix, path.substring(path.lastIndexOf('.') + 1), new File(path));
    }


    public static String crearQR(String str) throws IOException, WriterException {
        String fechaHoy = java.time.LocalDate.now().toString();
        String path = "src/main/resources/GeneratedQR/" + System.currentTimeMillis() + "---" + fechaHoy + ".png";
        String charset = "UTF-8";
        Map<EncodeHintType, ErrorCorrectionLevel> hashMap = new HashMap<EncodeHintType, ErrorCorrectionLevel>();
        hashMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);

        generateQRcode(str, path, charset, hashMap, 1024, 1024);//increase or decrease height and width accodingly

        ConverterFactory factory = new ConverterFactory();
        PNGConverter converter = (PNGConverter) factory.createConversor("PNG");
        return converter.convertFileToBase64(path);

    }
}