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
import java.util.Arrays;

public class SignedDecimalToBigDecimal extends SignedIntegerToInteger {
    @Override
    public void validate(Class<?> type, int size, int decimals) {
        if(!BigDecimal.class.equals(type)) {
            throw new TypeConverterException("Only supports converting to and from BigDecimal");
        }
    }

    @Override
    public Object to(byte[] bytes, int offset, int length, int decimals, boolean removePadding) {
        if(this.defaultValue != null && ByteUtils.allEquals(bytes, this.nullFillerByte, offset, bytes.length)) { // All of value is null filler
            return new BigDecimal(defaultValue);
        } else {
            return new BigDecimal(new BigInteger(getSignedIntegerString(bytes, offset, length, removePadding)), decimals);
        }
    }

    @Override
    public byte[] from(Object value, int length, int decimals, boolean addPadding) {
        if(value == null && this.defaultValue == null) {
            return null;
        }

        BigDecimal i = value != null ? ((BigDecimal)value) : new BigDecimal(this.defaultValue);
        // TODO: Validate loss of precision
        BigInteger absValue = i.movePointRight(decimals).toBigInteger().abs();

        String strValue = absValue.toString();
        if(absValue.signum() == 0) { // Value is zero
            // Make sure we add the number of decimals so the sign byte is in the right location
            char[] chars = new char[decimals];
            Arrays.fill(chars, '0');
            strValue += new String(chars);
        }

        byte[] strBytes = getSignedBytes(strValue, i.signum() < 0);
        if(strBytes.length > length) {
            throw new TypeConverterException("Field to small for value: " + length + " < " + strBytes.length);
        }
        if(addPadding) {
            strBytes = padBytes(strBytes, length);
        }
        return strBytes;
    }
}
