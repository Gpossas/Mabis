package com.mabis.exceptions;

public class MultipartAttachmentException extends RuntimeException
{
    public MultipartAttachmentException(){ super("Failed to read bytes from MultipartFile"); }
}
