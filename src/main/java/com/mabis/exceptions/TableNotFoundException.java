package com.mabis.exceptions;

public class TableNotFoundException extends RuntimeException
{
    public TableNotFoundException() { super("Table not found"); }
}
