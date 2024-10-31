package com.mabis.exceptions;

public class MenuItemNotFound extends RuntimeException
{
    public MenuItemNotFound(){ super("Menu item not found"); }
}
