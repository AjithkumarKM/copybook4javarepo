/*
 * Copyright (c) 2015. Troels Liebe Bentsen <tlb@nversion.dk>
 * Copyright (c) 2016. Nordea Bank AB
 * Licensed under the MIT license (LICENSE.txt)
 */

package com.nordea.oss.copybook.serializers;

import com.nordea.oss.copybook.annotations.CopyBookLine;
import com.nordea.oss.copybook.CopyBookSerializer;
import com.nordea.oss.copybook.annotations.CopyBook;
import com.nordea.oss.copybook.exceptions.CopyBookException;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

import java.math.BigDecimal;

public class CopyBookMapperFullTypeExceptionsTest {

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @CopyBook(type = FullMapper.class)
    static public class fieldTypeInteger {
        @CopyBookLine("01 FIELD PIC 9(2).")
        public int field;
    }

    @CopyBook(type = FullMapper.class)
    static public class fieldTypeString {
        @CopyBookLine("01 FIELD PIC X(2).")
        public String field;
    }

    @CopyBook(type = FullMapper.class)
    static public class fieldTypeDecimal {
        @CopyBookLine("01 FIELD PIC 9(2)V9(2).")
        public BigDecimal field;
    }

    @org.junit.Test
    public void testRightFieldTypeInteger() throws Exception {
        expectedEx.expect(CopyBookException.class);
        expectedEx.expectMessage("Field to small for value");
        CopyBookSerializer serializer = new CopyBookSerializer(fieldTypeInteger.class);
        fieldTypeInteger test = new fieldTypeInteger();
        test.field = 100;
        byte[] testBytes = serializer.serialize(test);
    }

    @org.junit.Test
    public void testRightFieldTypeString() throws Exception {
        expectedEx.expect(CopyBookException.class);
        expectedEx.expectMessage("Field to small for value");
        CopyBookSerializer serializer = new CopyBookSerializer(fieldTypeString.class);
        fieldTypeString test = new fieldTypeString();
        test.field = "ok1234";
        byte[] testBytes = serializer.serialize(test);
    }

    @org.junit.Test
    public void testRightFieldTypeDecimal() throws Exception {
        expectedEx.expect(CopyBookException.class);
        expectedEx.expectMessage("Field to small for value");
        CopyBookSerializer serializer = new CopyBookSerializer(fieldTypeDecimal.class);
        fieldTypeDecimal test = new fieldTypeDecimal();
        test.field = new BigDecimal("100.01");
        byte[] testBytes = serializer.serialize(test);
    }
}