package com.mabis.exceptions;

public class QRCodeEncodeException extends RuntimeException
{
    public QRCodeEncodeException(){ super("Couldn't encode to QR Code"); }
}
