package com.mabis.exceptions;

public class TableTokenNotMatchException extends RuntimeException
{
    public TableTokenNotMatchException(int table_number)
    {
        super("You're not authorized to place orders in table " + table_number);
    }
}
