package com.mabis.services;

import com.mabis.domain.qr_code.QRCodeGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class QRCodeService
{
    private final QRCodeGenerator qr_code_generator;

    public byte[] generate_qr_code(String content)
    {
        return this.qr_code_generator.generate_qr_code(content);
    }
}
