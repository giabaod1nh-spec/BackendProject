package com.example.trainning.point.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ ElementType.FIELD}) //Field de inject annotation nay
@Retention(RetentionPolicy.RUNTIME) //Annotation nay se duoc xu li luc nao
//@Repeatable(List.class)
@Documented
@Constraint(validatedBy = {DobValidator.class})
public @interface DobConstraint {
    //3 method co ban cua 1 annotation
    String message() default "Invalid date of birth";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    //Properties customize :
    int min();

    //Can them 1 lop validateBy de xu li annotation nay

    //Sau khi hoan thanh xong nho define ErrorCode
}
