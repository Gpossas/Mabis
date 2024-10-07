package com.mabis.exceptions;

public class QRCodeToBytesException extends RuntimeException
{
    public QRCodeToBytesException(){ super("Couldn't transofrm QR Code to bytes"); }
}
