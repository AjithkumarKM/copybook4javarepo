/*
 * Copyright (c) 2015. Troels Liebe Bentsen <tlb@nversion.dk>
 * Copyright (c) 2016. Nordea Bank AB
 * Licensed under the MIT license (LICENSE.txt)
 */

package com.nordea.oss.copybook.converters;

import com.nordea.oss.ByteUtils;
import com.nordea.oss.copybook.exceptions.TypeConverterException;

import java.math.BigInteger;

public class SignedIntegerToBigInteger extends SignedIntegerToInteger {
    @Override
    public void validate(Class<?> type, int size, int decimals) {
        if(!BigInteger.class.equals(type)) {
            throw new TypeConverterException("Only supports converting to and from BigInteger");
        }
    }

    @Override
    public Object to(byte[] bytes, int offset, int length, int decimals, boolean removePadding) {
        if(this.defaultValue != null && ByteUtils.allEquals(bytes, this.nullFillerByte, offset, bytes.length)) { // All of value is null filler
            return new BigInteger(defaultValue);
        } else {
            return new BigInteger(getSignedIntegerString(bytes, offset, length, removePadding));
        }
    }

    @Override
    public byte[] from(Object value, int length, int decimals, boolean addPadding) {
        if(value == null && this.defaultValue == null) {
            return null;
        }

        BigInteger i = value != null ? (BigInteger)value : new BigInteger(this.defaultValue);
        byte[] strBytes = getSignedBytes(i.abs().toString(), i.signum() < 0);
        if(strBytes.length > length) {
            throw new TypeConverterException("Field to small for value: " + length + " < " + strBytes.length);
        }
        if(addPadding) {
            strBytes = padBytes(strBytes, length);
        }
        return strBytes;

    }
}
