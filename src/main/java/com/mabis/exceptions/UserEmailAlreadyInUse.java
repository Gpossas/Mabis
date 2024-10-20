package com.mabis.exceptions;

public class UserEmailAlreadyInUse extends RuntimeException
{
    public UserEmailAlreadyInUse(){ super("This email is already in use"); }
}
