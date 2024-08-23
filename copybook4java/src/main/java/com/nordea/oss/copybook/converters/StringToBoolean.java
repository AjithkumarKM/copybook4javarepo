/*
 * Copyright (c) 2015. Troels Liebe Bentsen <tlb@nversion.dk>
 * Copyright (c) 2016. Nordea Bank AB
 * Licensed under the MIT license (LICENSE.txt)
 */

package com.nordea.oss.copybook.converters;

import com.nordea.oss.copybook.exceptions.TypeConverterException;

public class StringToBoolean extends StringToString {
    private String[] values;

    @Override
    public void initialize(TypeConverterConfig config) {
        super.initialize(config);
        values = format.split("\\|");
        if(values.length != 2) {
            throw new TypeConverterException("A minimum of two values should be provided in the format with | as seperator");
        }
    }

    @Override
    public void validate(Class<?> type, int size, int decimals) {
        if(!(Boolean.class.equals(type) || Boolean.TYPE.equals(type))) {
            throw new TypeConverterException("Only supports converting to and from int or Integer");
        }
    }

    @Override
    public Object to(byte[] bytes, int offset, int length, int decimals, boolean removePadding) {
        String value = (String)super.to(bytes, offset, length, decimals, removePadding);
        if(values[0].equals(value)) {
            return true;

        } else if(values[1].equals(value)) {
            return false;

        } else {
            throw new TypeConverterException("Unknown true or false value: " + value);
        }
    }

    @Override
    public byte[] from(Object value, int length, int decimals, boolean addPadding) {
        return super.from(value != null ? ((boolean)value ? values[0] : values[1]) : null, length, decimals, addPadding);
    }
}
