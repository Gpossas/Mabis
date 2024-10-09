package com.mabis.domain.attachment;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Scope(value = "prototype")
@Component
public class AttachmentService
{
    private final StorageService storage_service;

    AttachmentService(StorageService storage_service)
    {
        this.storage_service = storage_service;
    }

    public String upload(Attachment attachment)
    {
        return this.storage_service.upload(attachment);
    }

    public void delete(String key)
    {
        this.storage_service.delete(key);
    }
}
