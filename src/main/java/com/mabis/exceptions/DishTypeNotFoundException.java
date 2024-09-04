package com.mabis.exceptions;

public class DishTypeNotFoundException extends RuntimeException
{
    public DishTypeNotFoundException()
    {
        super("Dish type not found");
    }

    public DishTypeNotFoundException(String message)
    {
        super(message);
    }
}
