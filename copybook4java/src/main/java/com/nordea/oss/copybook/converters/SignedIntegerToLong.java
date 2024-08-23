/*
 * Copyright (c) 2015. Troels Liebe Bentsen <tlb@nversion.dk>
 * Copyright (c) 2016. Nordea Bank AB
 * Licensed under the MIT license (LICENSE.txt)
 */

package com.nordea.oss.copybook.converters;

import com.nordea.oss.ByteUtils;
import com.nordea.oss.copybook.exceptions.TypeConverterException;
import com.nordea.oss.copybook.serializers.CopyBookFieldSigningType;

public class SignedIntegerToLong extends SignedIntegerToInteger {
    @Override
    public void validate(Class<?> type, int size, int decimals) {
        if(size > 22 && (this.signingType == CopyBookFieldSigningType.PREFIX || this.signingType == CopyBookFieldSigningType.POSTFIX)) {
            throw new TypeConverterException("long is not large enough to hold possible value");
        }
        if(size > 21 && (this.signingType == CopyBookFieldSigningType.LAST_BYTE_BIT8 || this.signingType == CopyBookFieldSigningType.LAST_BYTE_EBCDIC_BIT5)) {
            throw new TypeConverterException("long is not large enough to hold possible value");
        }

        if(!(Long.class.equals(type) || Long.TYPE.equals(type))) {
            throw new TypeConverterException("Only supports converting to and from long or Long");
        }
    }

    @Override
    public Object to(byte[] bytes, int offset, int length, int decimals, boolean removePadding) {
        if(this.defaultValue != null && ByteUtils.allEquals(bytes, this.nullFillerByte, offset, bytes.length)) { // All of value is null filler
            return Long.parseLong(defaultValue);
        } else {
            return Long.parseLong(getSignedIntegerString(bytes, offset, length, removePadding));
        }
    }

    @Override
    public byte[] from(Object value, int length, int decimals, boolean addPadding) {
        if(value == null && this.defaultValue == null) {
            return null;
        }

        long i = value != null ? (long)value : Long.parseLong(this.defaultValue);
        byte[] strBytes = getSignedBytes(Long.toString(Math.abs(i)), i < 0L);
        if(strBytes.length > length) {
            throw new TypeConverterException("Field to small for value: " + length + " < " + strBytes.length);
        }
        if(addPadding) {
            strBytes = padBytes(strBytes, length);
        }
        return strBytes;
    }
}
