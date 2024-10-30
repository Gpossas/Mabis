package com.mabis.services;

import com.mabis.domain.attachment.AttachmentUpload;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Scope(value = "prototype")
@Service
public class AttachmentService
{
    private final StorageService storage_service;

    AttachmentService(StorageService storage_service)
    {
        this.storage_service = storage_service;
    }

    public String upload(AttachmentUpload attachment)
    {
        return this.storage_service.upload(attachment);
    }

    public void delete(String key)
    {
        this.storage_service.delete(key);
    }
}
