package com.mabis.domain.attachment;

import java.util.UUID;

public class QRCodeAttachment implements Attachment
{
    private final byte[] byte_array;

    @Override
    public String get_name()
    {
        return UUID.randomUUID().toString();
    }

    @Override
    public String get_content_type()
    {
        return "image/png";
    }

    @Override
    public byte[] get_bytes()
    {
        return byte_array;
    }

    public QRCodeAttachment(byte[] byte_array)
    {
        this.byte_array = byte_array;
    }

}
