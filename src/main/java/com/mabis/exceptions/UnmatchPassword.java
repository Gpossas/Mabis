package com.mabis.exceptions;

public class UnmatchPassword extends RuntimeException
{
    public UnmatchPassword(){ super("Password doesn't match"); }
}
