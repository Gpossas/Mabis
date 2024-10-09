package com.mabis.exceptions;

public class NotActiveTableException extends RuntimeException
{
    public NotActiveTableException(){ super("Table is not active"); }
}
