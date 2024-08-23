/*
 * Copyright (c) 2015. Troels Liebe Bentsen <tlb@nversion.dk>
 * Copyright (c) 2016. Nordea Bank AB
 * Licensed under the MIT license (LICENSE.txt)
 */

package com.nordea.oss.copybook.annotations;

import com.nordea.oss.copybook.converters.*;
import com.nordea.oss.copybook.serializers.CopyBookFieldSigningType;
import com.nordea.oss.copybook.serializers.FullMapper;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@CopyBook(type = FullMapper.class, charset = "UTF-8")
@CopyBookFieldFormat(type = IntegerToInteger.class, rightPadding = false, paddingChar = '0', nullFillerChar = (byte)0, defaultValue = "0", signingType = CopyBookFieldSigningType.PREFIX)
@CopyBookFieldFormat(type = IntegerToLong.class, rightPadding = false, paddingChar = '0', nullFillerChar = (byte)0, defaultValue = "0", signingType = CopyBookFieldSigningType.PREFIX)
@CopyBookFieldFormat(type = IntegerToBigInteger.class, rightPadding = false, paddingChar = '0', nullFillerChar = (byte)0, defaultValue = "0", signingType = CopyBookFieldSigningType.PREFIX)
@CopyBookFieldFormat(type = SignedIntegerToInteger.class, rightPadding = false, paddingChar = '0', nullFillerChar = (byte)0, defaultValue = "0", signingType = CopyBookFieldSigningType.PREFIX)
@CopyBookFieldFormat(type = SignedIntegerToLong.class, rightPadding = false, paddingChar = '0', nullFillerChar = (byte)0, defaultValue = "0", signingType = CopyBookFieldSigningType.PREFIX)
@CopyBookFieldFormat(type = SignedIntegerToBigInteger.class, rightPadding = false, paddingChar = '0', nullFillerChar = (byte)0, defaultValue = "0", signingType = CopyBookFieldSigningType.PREFIX)
@CopyBookFieldFormat(type = DecimalToBigDecimal.class, rightPadding = false, paddingChar = '0', nullFillerChar = (byte)0, defaultValue = "0", signingType = CopyBookFieldSigningType.PREFIX)
@CopyBookFieldFormat(type = SignedDecimalToBigDecimal.class, rightPadding = false, paddingChar = '0', nullFillerChar = (byte)0, defaultValue = "0", signingType = CopyBookFieldSigningType.PREFIX)
@CopyBookFieldFormat(type = StringToString.class, rightPadding = true, paddingChar = ' ', nullFillerChar = (byte)0, signingType = CopyBookFieldSigningType.PREFIX)
@CopyBookFieldFormat(type = IntegerToTypeConverterIntEnum.class, rightPadding = false, paddingChar = '0', nullFillerChar = (byte)0, signingType = CopyBookFieldSigningType.PREFIX)
@CopyBookFieldFormat(type = SignedIntegerToTypeConverterIntEnum.class, rightPadding = false, paddingChar = '0', nullFillerChar = (byte)0, signingType = CopyBookFieldSigningType.PREFIX)
@CopyBookFieldFormat(type = StringToTypeConverterStringEnum.class, rightPadding = true, paddingChar  = ' ', nullFillerChar = (byte)0, signingType = CopyBookFieldSigningType.PREFIX)
@CopyBookFieldFormat(type = IntegerToBoolean.class, rightPadding = false, paddingChar = '0', nullFillerChar = (byte)0, signingType = CopyBookFieldSigningType.PREFIX)
@CopyBookFieldFormat(type = SignedIntegerToBoolean.class, rightPadding = false, paddingChar = '0', nullFillerChar = (byte)0, signingType = CopyBookFieldSigningType.PREFIX)
@CopyBookFieldFormat(type = StringToBoolean.class, rightPadding = true, format = "Y|N", paddingChar  = ' ', nullFillerChar = (byte)0, signingType = CopyBookFieldSigningType.PREFIX)
@CopyBookFieldFormat(type = StringToLocalDateTime.class, rightPadding = true, format ="yyyyMMddHHmmss", paddingChar  = ' ', nullFillerChar = (byte)0, signingType = CopyBookFieldSigningType.PREFIX)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface CopyBookDefaults {
}
