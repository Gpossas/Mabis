package com.mabis.domain.attachment;

import com.mabis.exceptions.MultipartAttachmentException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public class MultipartAttachmentUpload implements AttachmentUpload
{
    private final String name;
    private final String content_type;
    private final byte[] bytes;

    @Override
    public String get_name()
    {
        return name;
    }

    @Override
    public String get_content_type()
    {
        return content_type;
    }

    @Override
    public byte[] get_bytes()
    {
        return bytes;
    }

    public MultipartAttachmentUpload(MultipartFile multipart_file)
    {
        this.name = multipart_file.getOriginalFilename();
        this.content_type = multipart_file.getContentType();
        try { this.bytes = multipart_file.getBytes(); } catch (IOException e) { throw new MultipartAttachmentException(); }
    }
}
