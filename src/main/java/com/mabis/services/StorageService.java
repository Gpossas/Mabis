package com.mabis.services;

import com.mabis.domain.attachment.AttachmentUpload;

public interface StorageService
{
    String upload(AttachmentUpload attachment);

    void delete(String key);

    String get_service_name();
}
