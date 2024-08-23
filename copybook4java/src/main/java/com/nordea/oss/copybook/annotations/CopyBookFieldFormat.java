/*
 * Copyright (c) 2015. Troels Liebe Bentsen <tlb@nversion.dk>
 * Copyright (c) 2016. Nordea Bank AB
 * Licensed under the MIT license (LICENSE.txt)
 */

package com.nordea.oss.copybook.annotations;

import com.nordea.oss.copybook.converters.TypeConverter;
import com.nordea.oss.copybook.serializers.CopyBookFieldSigningType;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE, ElementType.FIELD })
@Repeatable(CopyBookFieldFormats.class)
public @interface CopyBookFieldFormat {
    Class<? extends TypeConverter> type();

    CopyBookFieldSigningType signingType() default CopyBookFieldSigningType.PREFIX;
    boolean rightPadding() default false;
    char paddingChar() default ' ';
    char nullFillerChar() default (byte)0;
    String defaultValue() default "";
    String errorValue() default ""; // TODO: Implement with @CopyBookFieldFormat(type = IntegerToInteger.class, errorValue = "0")
    String format() default "";
}
