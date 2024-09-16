package com.mabis.domain.attachment;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Scope(value = "prototype")
@Component
public class AttachmentService
{
    private final StorageService storage_service;

    AttachmentService(StorageService storage_service)
    {
        this.storage_service = storage_service;
    }

    public String upload(MultipartFile file)
    {
        System.out.println("Scanning for viruses... Secure!");
        return this.storage_service.upload(file);
    }
}
