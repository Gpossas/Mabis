package com.mabis.domain.qr_code;

public interface QRCodeGenerator
{
    byte[] generate_qr_code(String content);
}
