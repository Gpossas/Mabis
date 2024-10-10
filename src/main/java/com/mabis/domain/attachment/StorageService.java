package com.mabis.domain.attachment;

public interface StorageService
{
    String upload(AttachmentUpload attachment);

    void delete(String key);

    String get_service_name();
}
