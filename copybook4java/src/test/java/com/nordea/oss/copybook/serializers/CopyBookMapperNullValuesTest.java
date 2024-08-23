/*
 * Copyright (c) 2015. Troels Liebe Bentsen <tlb@nversion.dk>
 * Copyright (c) 2016. Nordea Bank AB
 * Licensed under the MIT license (LICENSE.txt)
 */

package com.nordea.oss.copybook.serializers;

import com.nordea.oss.copybook.CopyBookSerializer;
import com.nordea.oss.copybook.annotations.CopyBook;
import com.nordea.oss.copybook.annotations.CopyBookFieldFormat;
import com.nordea.oss.copybook.annotations.CopyBookLine;
import com.nordea.oss.copybook.converters.StringToString;
import com.nordea.oss.copybook.exceptions.CopyBookException;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

import java.math.BigInteger;

import static org.junit.Assert.*;

public class CopyBookMapperNullValuesTest {

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @CopyBook(type = FullMapper.class)
    @CopyBookFieldFormat(type = StringToString.class, paddingChar = ' ', nullFillerChar = (byte)0, signingType = CopyBookFieldSigningType.PREFIX, rightPadding = false)
    static public class fieldTypeStringSetToNullFull {
        @CopyBookLine("01 FIELD PIC X(4).")
        public String field;
    }

    @org.junit.Test
    public void testFieldTypeStringSetToNullFull() throws Exception {
        CopyBookSerializer serializer = new CopyBookSerializer(fieldTypeStringSetToNullFull.class);
        fieldTypeStringSetToNullFull test = new fieldTypeStringSetToNullFull();
        test.field = null;
        byte[] testBytes = serializer.serialize(test);
        assertArrayEquals(testBytes, new byte[]{ 0, 0, 0, 0 });
        fieldTypeStringSetToNullFull test2 = serializer.deserialize(testBytes, fieldTypeStringSetToNullFull.class);
        assertNull(test2.field);
    }

    @CopyBook(type = PackedFirstLevelMapper.class)
    @CopyBookFieldFormat(type = StringToString.class, paddingChar = ' ', nullFillerChar = (byte)0, signingType = CopyBookFieldSigningType.PREFIX, rightPadding = false)
    static public class fieldTypeStringSetToNullPacked {
        @CopyBookLine("01 FIELD PIC X(4).")
        public String field;
    }

    @org.junit.Test
    public void testFieldTypeStringSetToNullPacked() throws Exception {
        CopyBookSerializer serializer = new CopyBookSerializer(fieldTypeStringSetToNullPacked.class);
        fieldTypeStringSetToNullPacked test = new fieldTypeStringSetToNullPacked();
        test.field = null;
        byte[] testBytes = serializer.serialize(test);
        assertArrayEquals(testBytes, new byte[]{ 0, 0, 0, 0, 0, 0, 0, 0 });
        fieldTypeStringSetToNullPacked test2 = serializer.deserialize(testBytes, fieldTypeStringSetToNullPacked.class);
        assertNull(test2.field);
    }

    @CopyBook()
    static public class objectField {
        @CopyBookLine("01 FIELD PIC X(4).")
        public String field;
    }

    @CopyBook(type = PackedFirstLevelMapper.class)
    @CopyBookFieldFormat(type = StringToString.class, paddingChar = ' ', nullFillerChar = (byte)0, signingType = CopyBookFieldSigningType.PREFIX, rightPadding = false)
    static public class fieldTypeNestedNullPacked {
        @CopyBookLine("01 FIELD.")
        public objectField field;
    }


    @org.junit.Test
    public void testFieldTypeNestedNullPacked() throws Exception {
        CopyBookSerializer serializer = new CopyBookSerializer(fieldTypeNestedNullPacked.class);
        fieldTypeNestedNullPacked test = new fieldTypeNestedNullPacked();
        test.field = new objectField();
        test.field.field = null;
        byte[] testBytes = serializer.serialize(test);
        assertArrayEquals(testBytes, new byte[] { -128, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 11 });
        fieldTypeNestedNullPacked test2 = serializer.deserialize(testBytes, fieldTypeNestedNullPacked.class);
        assertNotNull(test2.field);
        assertNull(test2.field.field);
    }

    @CopyBook()
    static public class objectFieldArray {
        @CopyBookLine("01 FIELDS OCCURS 2 TIMES.")
        @CopyBookLine("01 FIELD PIC X(2).")
        public String[] fields;
    }

    @CopyBook(type = PackedFirstLevelMapper.class)
    @CopyBookFieldFormat(type = StringToString.class, paddingChar = ' ', nullFillerChar = (byte)0, signingType = CopyBookFieldSigningType.PREFIX, rightPadding = false)
    static public class fieldTypeNestedArrayNullPacked {
        @CopyBookLine("01 FIELD.")
        public objectFieldArray field;
    }

    @org.junit.Test
    public void testFieldTypeNestedArrayNullPacked() throws Exception {
        CopyBookSerializer serializer = new CopyBookSerializer(fieldTypeNestedArrayNullPacked.class);
        fieldTypeNestedArrayNullPacked test = new fieldTypeNestedArrayNullPacked();
        test.field = new objectFieldArray();
        test.field.fields = new String[] { "do", null };
        byte[] testBytes = serializer.serialize(test);
        fieldTypeNestedArrayNullPacked test2 = serializer.deserialize(testBytes, fieldTypeNestedArrayNullPacked.class);
        assertNotNull(test2.field);
        assertEquals("do", test2.field.fields[0]);
        assertNull(test2.field.fields[1]);

        // Test start with null value
        test.field.fields = new String[] { null, "do" };
        testBytes = serializer.serialize(test);
        test2 = serializer.deserialize(testBytes, fieldTypeNestedArrayNullPacked.class);
        assertNotNull(test2.field);
        assertNull(test2.field.fields[0]);
        assertEquals("do", test2.field.fields[1]);
    }

    @CopyBook()
    static public class FieldBigInteger {
        @CopyBookLine("07 SOME-ACCOUNT PIC 9(2).")
        private BigInteger someAccount;
    }

    @org.junit.Test
    public void testFieldBigIntegerNull() throws Exception {
        CopyBookSerializer serializer = new CopyBookSerializer(FieldBigInteger.class);
        FieldBigInteger test = new FieldBigInteger();
        byte[] testBytes = serializer.serialize(test);
        assertArrayEquals(new byte[] { 48, 48 }, testBytes);
    }

    @CopyBook()
    static public class ObjectFieldInt {
        @CopyBookLine("01 FIELD PIC 9(2).")
        public int value;
    }

    @CopyBook(type = FullMapper.class)
    static public class FieldTypeNestedIntNull {
        @CopyBookLine("01 FIELD.")
        public ObjectFieldInt field;
    }

    @org.junit.Test
    public void testFieldTypeNestedIntNull() throws Exception {
        CopyBookSerializer serializer = new CopyBookSerializer(FieldTypeNestedIntNull.class);
        FieldTypeNestedIntNull test = new FieldTypeNestedIntNull();
        byte[] testBytes = serializer.serialize(test);
        assertArrayEquals(new byte[] { 48, 48 }, testBytes);
    }

    @CopyBook(type = FullMapper.class, strict = true)
    static public class FieldTypeNestedIntNullStrict {
        @CopyBookLine("01 FIELD.")
        public ObjectFieldInt field;
    }

    @org.junit.Test
    public void testFieldTypeNestedIntNullStrict() throws Exception {
        expectedEx.expect(CopyBookException.class);
        expectedEx.expectMessage("Root object for field");
        CopyBookSerializer serializer = new CopyBookSerializer(FieldTypeNestedIntNullStrict.class);
        FieldTypeNestedIntNullStrict test = new FieldTypeNestedIntNullStrict();
        byte[] testBytes = serializer.serialize(test);
    }

}