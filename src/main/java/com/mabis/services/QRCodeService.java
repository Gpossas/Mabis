package com.mabis.services;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.mabis.exceptions.QRCodeEncodeException;
import com.mabis.exceptions.QRCodeToBytesException;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
public class QRCodeService
{
    public byte[] generate_qr_code(String barcodeText)
    {
        QRCodeWriter barcodeWriter = new QRCodeWriter();
        ByteArrayOutputStream byte_array = new ByteArrayOutputStream();

        try
        {
            BitMatrix bit_matrix =
                    barcodeWriter.encode(barcodeText, BarcodeFormat.QR_CODE, 200, 200);

            MatrixToImageWriter.writeToStream(bit_matrix, "PNG", byte_array);
        }
        catch (WriterException e)
        {
            throw new QRCodeEncodeException();
        }
        catch (IOException e)
        {
            throw new QRCodeToBytesException();
        }

        return byte_array.toByteArray();
    }
}
