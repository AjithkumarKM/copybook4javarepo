/*
 * Copyright (c) 2015. Troels Liebe Bentsen <tlb@nversion.dk>
 * Copyright (c) 2016. Nordea Bank AB
 * Licensed under the MIT license (LICENSE.txt)
 */

package com.nordea.oss.copybook.converters;

import com.nordea.oss.ByteUtils;
import com.nordea.oss.copybook.exceptions.TypeConverterException;

import java.math.BigDecimal;
import java.math.BigInteger;

public class DecimalToBigDecimal extends TypeConverterBase {
    @Override
    public void validate(Class<?> type, int size, int decimals) {
        if(size < 2) {
            throw new TypeConverterException("Field to small to hold a decimal number: " + size + " < 2");
        }
        if(!BigDecimal.class.equals(type)) {
            throw new TypeConverterException("Only supports converting to and from BigDecimal");
        }
    }

    @Override
    public Object to(byte[] bytes, int offset, int length, int decimals, boolean removePadding) {
        if(this.defaultValue != null && ByteUtils.allEquals(bytes, this.nullFillerByte, offset, bytes.length)) { // All of value is null filler
            return new BigDecimal(defaultValue);
        } else {
            return new BigDecimal(new BigInteger(getString(bytes, offset, length, removePadding, 1)), decimals);
        }
    }

    @Override
    public byte[] from(Object value, int length, int decimals, boolean addPadding) {
        if(value == null && this.defaultValue == null) {
            return null;
        }

        BigDecimal i = value != null ? ((BigDecimal)value) : new BigDecimal(this.defaultValue);
        if(i.signum() == -1) {
            throw new TypeConverterException("Number can not be negative");
        }
        // TODO: Validate loss of precision
        byte[] strBytes = i.movePointRight(decimals).toBigInteger().abs().toString().getBytes(this.charset);
        if(strBytes.length > length) {
            throw new TypeConverterException("Field to small for value: " + length + " < " + strBytes.length);
        }
        if(addPadding) {
            strBytes = padBytes(strBytes, length);
        }
        return strBytes;
    }


}
