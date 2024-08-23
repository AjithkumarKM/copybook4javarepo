/*
 * Copyright (c) 2015. Troels Liebe Bentsen <tlb@nversion.dk>
 * Copyright (c) 2016. Nordea Bank AB
 * Licensed under the MIT license (LICENSE.txt)
 */

package com.nordea.oss.copybook.serializers;

public enum CopyBookFieldSigningType {
    POSTFIX,
    PREFIX,
    LAST_BYTE_BIT8,
    LAST_BYTE_EBCDIC_BIT5
}