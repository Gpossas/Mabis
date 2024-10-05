package com.mabis.domain.attachment;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetUrlRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.util.UUID;


@Component
@RequiredArgsConstructor
public class S3StorageService implements StorageService
{
    @Value("${aws.s3.bucket_name}")
    private String bucket_name;
    private final S3Client s3;

    @Override
    public String upload(Attachment attachment)
    {
        String contentType = attachment.get_content_type();
        String key = UUID.randomUUID() + "-" + attachment.get_name();

        s3.putObject(
            PutObjectRequest.builder().bucket(bucket_name).key(key).contentType(contentType).build(),
            RequestBody.fromBytes(attachment.get_bytes())
        );

        GetUrlRequest request = GetUrlRequest.builder().bucket(bucket_name).key(key).build();
        return s3.utilities().getUrl(request).toExternalForm();
    }

    @Override
    public String get_service_name()
    {
        return "S3";
    }
}
