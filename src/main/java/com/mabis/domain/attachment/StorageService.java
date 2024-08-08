package com.mabis.domain.attachment;

import org.springframework.web.multipart.MultipartFile;

public interface StorageService
{
    public String upload(MultipartFile file);
}
