package com.mabis.config;

import com.mabis.domain.qr_code.QRCodeGenerator;
import com.mabis.domain.qr_code.ZXingQRCodeGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QRCodeConfig
{
    @Bean
    public QRCodeGenerator qr_code()
    {
        return new ZXingQRCodeGenerator();
    }
}
