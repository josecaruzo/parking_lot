package com.fiap_tech_challenge.parking_lot.service.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UserCreationValidator.class)
public @interface ValidUserCreation{
	String message() default "Validation Error";

	Class<?>[] groups() default {};
	Class<? extends Payload> [] payload() default {};
}
