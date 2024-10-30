package com.mabis.exceptions;

public class ActiveTableException extends RuntimeException
{
    public ActiveTableException() { super("A table can't be modified while active"); }
}
