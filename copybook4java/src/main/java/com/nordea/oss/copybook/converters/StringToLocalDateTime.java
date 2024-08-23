/*
 * Copyright (c) 2015. Troels Liebe Bentsen <tlb@nversion.dk>
 * Copyright (c) 2016. Nordea Bank AB
 * Licensed under the MIT license (LICENSE.txt)
 */

package com.nordea.oss.copybook.converters;

import com.nordea.oss.copybook.exceptions.TypeConverterException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class StringToLocalDateTime extends StringToString {
    DateTimeFormatter formatter;

    @Override
    public void initialize(TypeConverterConfig config) {
        super.initialize(config);
        this.formatter = DateTimeFormatter.ofPattern(this.format);
    }

    @Override
    public void validate(Class<?> type, int size, int decimals) {
        if(!(LocalDateTime.class.isAssignableFrom(type))) {
            throw new TypeConverterException("Only supports converting to and from an Enum that is or extends from LocalDateTime");
        }
    }

    @Override
    public Object to(byte[] bytes, int offset, int length, int decimals, boolean removePadding) {
        String value = (String)super.to(bytes, offset, length, decimals, removePadding);
        if(value != null && !value.isEmpty()) {
            return LocalDateTime.parse(value, formatter);

        } else {
            return null;
        }
    }

    @Override
    public byte[] from(Object value, int length, int decimals, boolean addPadding) {
        if(value == null) {
            if(this.defaultValue != null) {
                value = LocalDateTime.parse(this.defaultValue, formatter);

            } else {
                return null;
            }
        }

        byte[] strBytes = ((LocalDateTime)value).format(this.formatter).getBytes(this.charset);
        if (strBytes.length > length) {
            throw new TypeConverterException("Field to small for value: " + length + " < " + strBytes.length);
        }

        if (addPadding) {
            strBytes = padBytes(strBytes, length);
        }

        return strBytes;
    }


}
