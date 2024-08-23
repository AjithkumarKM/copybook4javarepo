/*
 * Copyright (c) 2015. Troels Liebe Bentsen <tlb@nversion.dk>
 * Copyright (c) 2016. Nordea Bank AB
 * Licensed under the MIT license (LICENSE.txt)
 */

package com.nordea.oss.copybook.exceptions;

public class TypeConverterException extends RuntimeException {
    private static final long serialVersionUID = -7577535992947276304L;
    public TypeConverterException(String message) {
        super(message);
    }
}
