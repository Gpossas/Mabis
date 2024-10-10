package com.mabis.domain.attachment;


public class QRCodeAttachmentUpload implements AttachmentUpload
{
    private final byte[] byte_array;
    private final String name;

    @Override
    public String get_name()
    {
        return this.name;
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

    public QRCodeAttachmentUpload(String name, byte[] byte_array)
    {
        this.name = name;
        this.byte_array = byte_array;
    }

}
