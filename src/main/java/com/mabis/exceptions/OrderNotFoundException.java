package com.mabis.exceptions;

public class OrderNotFoundException extends RuntimeException
{
    public OrderNotFoundException(){ super("Order not found"); }
}