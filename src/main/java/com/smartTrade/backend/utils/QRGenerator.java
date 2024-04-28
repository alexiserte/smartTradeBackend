package com.smartTrade.backend.Utils;
import com.aspose.barcode.*;
public class QRGenerator {
    public void createQR() {
        BarcodeGenerator gen = new BarcodeGenerator(EncodeTypes.QR, "Aspose");
        gen.getParameters().getBarcode().getXDimension().setPixels(4);
        generator.getParameters().setResolution(400);

        // Establecer versión automática
        gen.getParameters().getBarcode().getQR().setQrVersion(QRVersion.AUTO);

        // Establecer el tipo de codificación QR de ForceMicroQR
        gen.getParameters().getBarcode().getQR().setQrEncodeType(QREncodeType.FORCE_MICRO_QR);
        gen.save(dataDir + "QREncodeTypeForceMicroQR.png", BarCodeImageFormat.PNG);
    }
}
