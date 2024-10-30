package com.mabis.infra;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = EnumValidator.class) // Associa a anotação ao validador
@Target({ElementType.FIELD, ElementType.PARAMETER}) // Pode ser usada em campos ou parâmetros
@Retention(RetentionPolicy.RUNTIME) // A anotação estará disponível em tempo de execução
public @interface ValidEnum {
    String message() default "Invalid value"; // Mensagem de erro padrão
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    Class<? extends Enum<?>> enumClass(); // O enum para verificar
}
