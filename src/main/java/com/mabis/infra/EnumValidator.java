package com.mabis.infra;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;

public class EnumValidator implements ConstraintValidator<ValidEnum, String>
{
    private Class<? extends Enum<?>> enum_class;

    @Override
    public void initialize(ValidEnum constraintAnnotation)
    {
        this.enum_class = constraintAnnotation.enumClass();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context)
    {
        return Arrays.stream(enum_class.getEnumConstants())
                .anyMatch(e -> e.name().equals(value));
    }
}

