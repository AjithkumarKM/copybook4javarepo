/*
 * Copyright (c) 2015. Troels Liebe Bentsen <tlb@nversion.dk>
 * Copyright (c) 2016. Nordea Bank AB
 * Licensed under the MIT license (LICENSE.txt)
 */

package com.nordea.oss.copybook.converters;

import com.nordea.oss.copybook.exceptions.TypeConverterException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.nio.charset.StandardCharsets;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class IntegerToTypeConverterIntEnumTest {
    private TypeConverter typeConverter;
    private TypeConverterConfig config;

    public enum TestIntEnum implements TypeConverterIntEnum {
        HIGH(30), MEDIUM(20), LOW(10);
        private final int value;

        TestIntEnum(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }


    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @Before
    public void runBeforeEveryTest() {
        this.config = new TypeConverterConfig();
        this.config.setCharset(StandardCharsets.UTF_8);
        this.config.setPaddingChar('0');
        typeConverter = new IntegerToTypeConverterIntEnum();
        typeConverter.initialize(config);
        typeConverter.validate(TestIntEnum.class, 2, 1);
    }

    @Test
    public void testValidateSuccess() throws Exception {
        typeConverter.validate(TestIntEnum.class, 2, -1);
    }

    @Test(expected = TypeConverterException.class)
    public void testValidateFail() throws Exception {
        typeConverter.validate(Long.TYPE, 2, -1);
    }

    @Test
    public void testTo() throws Exception {
        assertEquals(TestIntEnum.HIGH, typeConverter.to("30".getBytes(StandardCharsets.UTF_8), 0, 2, -1, true));
        assertEquals(TestIntEnum.MEDIUM, typeConverter.to("20".getBytes(StandardCharsets.UTF_8), 0, 2, -1, true));
        assertEquals(TestIntEnum.LOW, typeConverter.to("10".getBytes(StandardCharsets.UTF_8), 0, 2, -1, true));
    }

    @Test
    public void testToNullDefaultValue() throws Exception {
        config.setNullFillerChar((char)0);
        config.setDefaultValue("10");
        typeConverter.initialize(config);
        assertEquals(TestIntEnum.LOW, typeConverter.to(new byte[4], 0, 2, 2, true));
    }

    @Test
    public void testToNullValue() throws Exception {
        expectedEx.expect(NumberFormatException.class);
        expectedEx.expectMessage("For input string");
        config.setNullFillerChar((char)0);
        typeConverter.initialize(config);
        assertEquals(null, typeConverter.to(new byte[4], 0, 2, 2, true));
    }

    @Test
    public void testFrom() throws Exception {
        assertArrayEquals("30".getBytes(StandardCharsets.UTF_8),  typeConverter.from(TestIntEnum.HIGH, 2, -1, true));
        assertArrayEquals("20".getBytes(StandardCharsets.UTF_8),  typeConverter.from(TestIntEnum.MEDIUM, 2, -1, true));
        assertArrayEquals("10".getBytes(StandardCharsets.UTF_8),  typeConverter.from(TestIntEnum.LOW, 2, -1, true));
    }

    @Test
    public void testFromOverflow() throws Exception {
        expectedEx.expect(TypeConverterException.class);
        expectedEx.expectMessage("Field to small for value");
        byte[] bytes = typeConverter.from(TestIntEnum.MEDIUM, 1, -1, true);
    }
}