package com.mabis.domain.attachment;

import org.springframework.web.multipart.MultipartFile;

public interface StorageService
{
    String upload(MultipartFile file);

    String get_service_name();
}
