package com.mabis.domain.attachment;

public interface AttachmentUpload
{
    String get_name();
    String get_content_type();
    byte[] get_bytes();
}
