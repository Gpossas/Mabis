package com.mabis.services;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
public class QRCodeService
{
    public byte[] generate_qr_code(String barcodeText) throws WriterException, IOException {
        QRCodeWriter barcodeWriter = new QRCodeWriter();
        BitMatrix bit_matrix =
                barcodeWriter.encode(barcodeText, BarcodeFormat.QR_CODE, 200, 200);

        ByteArrayOutputStream byte_array = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bit_matrix, "PNG", byte_array);
        return byte_array.toByteArray();
    }
}
