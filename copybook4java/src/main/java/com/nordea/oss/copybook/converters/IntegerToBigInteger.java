/*
 * Copyright (c) 2015. Troels Liebe Bentsen <tlb@nversion.dk>
 * Copyright (c) 2016. Nordea Bank AB
 * Licensed under the MIT license (LICENSE.txt)
 */

package com.nordea.oss.copybook.converters;

import com.nordea.oss.ByteUtils;
import com.nordea.oss.copybook.exceptions.TypeConverterException;

import java.math.BigInteger;
import java.util.Arrays;

public class IntegerToBigInteger extends IntegerToInteger {
    @Override
    public void validate(Class<?> type, int size, int decimals) {
        if(!BigInteger.class.equals(type)) {
            throw new TypeConverterException("Only supports converting to and from BigInteger");
        }
    }

    @Override
    public BigInteger to(byte[] bytes, int offset, int length, int decimals, boolean removePadding) {
        if(this.defaultValue != null && ByteUtils.allEquals(bytes, this.nullFillerByte, offset, bytes.length)) { // All of value is null filler
            return new BigInteger(defaultValue);
        } else {
            return new BigInteger(getIntegerString(bytes, offset, length, removePadding));
        }
    }

    @Override
    public byte[] from(Object value, int length, int decimals, boolean addPadding) {
        if(value == null && this.defaultValue == null) {
            return null;
        }

        BigInteger i = value != null ? (BigInteger)value : new BigInteger(this.defaultValue);
        if(i.signum() == -1) {
            throw new TypeConverterException("Number can not be negative");
        }

        byte[] strBytes;
        BigInteger absValue = i.abs();
        if(absValue.signum() == 0) {
            // Make sure we fill the strBytes with the number of decimals plus one extra zero
            strBytes = new byte[decimals + 1];
            Arrays.fill(strBytes, ("0".getBytes(this.charset))[0]);

        } else {
            strBytes = absValue.toString().getBytes(this.charset);
        }

        if(strBytes.length > length) {
            throw new TypeConverterException("Field to small for value: " + length + " < " + strBytes.length);
        }

        if(addPadding) {
            strBytes = padBytes(strBytes, length);
        }
        return strBytes;
    }
}
