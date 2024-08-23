/*
 * Copyright (c) 2015. Troels Liebe Bentsen <tlb@nversion.dk>
 * Copyright (c) 2016. Nordea Bank AB
 * Licensed under the MIT license (LICENSE.txt)
 */

package com.nordea.oss.copybook.exceptions;

public class CopyBookException extends RuntimeException {
    private static final long serialVersionUID = 28118369047109260L;
    public CopyBookException(String message) {
        super(message);
    }

    public CopyBookException(String message, TypeConverterException ex) {
        super(message + ": " + ex.getClass().getSimpleName() + " :" + ex.getMessage());
    }

    public CopyBookException(String message, Exception ex) {
        super(message + ": " + ex.getClass().getSimpleName() + " :" + ex.getMessage());
    }
}
