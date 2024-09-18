package com.mabis.exceptions;

public class InvalidStorageServiceException extends RuntimeException
{
    public InvalidStorageServiceException()
    {
        super("Invalid storage service");
    }
}
