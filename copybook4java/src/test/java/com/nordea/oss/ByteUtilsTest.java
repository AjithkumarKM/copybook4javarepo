/*
 * Copyright (c) 2015. Troels Liebe Bentsen <tlb@nversion.dk>
 * Copyright (c) 2016. Nordea Bank AB
 * Licensed under the MIT license (LICENSE.txt)
 */

package com.nordea.oss;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertArrayEquals;

public class ByteUtilsTest {

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @Test
    public void testAllEquals() throws Exception {
        // TODO: Create test for AllEquals
    }

    @Test
    public void testIndexOf() throws Exception {
        // TODO: Create test for IndexOf
    }

    @Test
    public void testTrimLeftMinLength0() throws Exception {
        byte[] inBytes = new byte[] { (byte)32, (byte)32, (byte)65 };
        byte[] outBytes = ByteUtils.trim(inBytes, (byte)32, false, 0);
        assertArrayEquals(new byte[] { (byte)65 }, outBytes );
    }

    @Test
    public void testTrimLeftMinLength0OnlyPadding() throws Exception {
        byte[] inBytes = new byte[] { (byte)32, (byte)32, (byte)32 };
        byte[] outBytes = ByteUtils.trim(inBytes, (byte)32, false, 0);
        assertArrayEquals(new byte[] { }, outBytes );
    }

    @Test
    public void testTrimLeftMinLength0Empty() throws Exception {
        byte[] inBytes = new byte[] { };
        byte[] outBytes = ByteUtils.trim(inBytes, (byte)32, false, 0);
        assertArrayEquals(new byte[] { }, outBytes );
    }

    @Test
    public void testTrimLeftMinLength1() throws Exception {
        byte[] inBytes = new byte[] { (byte)32, (byte)32, (byte)65 };
        byte[] outBytes = ByteUtils.trim(inBytes, (byte)32, false, 1);
        assertArrayEquals(new byte[] { (byte)65 }, outBytes );
    }

    @Test
    public void testTrimLeftMinLength1OnlyPadding() throws Exception {
        byte[] inBytes = new byte[] { (byte)32, (byte)32, (byte)32 };
        byte[] outBytes = ByteUtils.trim(inBytes, (byte)32, false, 1);
        assertArrayEquals(new byte[] { (byte)32 }, outBytes );
    }

    @Test
    public void testTrimRightMinLength0() throws Exception {
        byte[] inBytes = new byte[] { (byte)65, (byte)32, (byte)32 };
        byte[] outBytes = ByteUtils.trim(inBytes, (byte)32, true, 0);
        assertArrayEquals(new byte[]{(byte) 65}, outBytes);
    }

    @Test
    public void testTrimRightMinLength0OnlyPadding() throws Exception {
        byte[] inBytes = new byte[] { (byte)32, (byte)32, (byte)32 };
        byte[] outBytes = ByteUtils.trim(inBytes, (byte)32, true, 0);
        assertArrayEquals(new byte[]{}, outBytes);
    }

    @Test
    public void testTrimRightMinLength0Empty() throws Exception {
        byte[] inBytes = new byte[] { };
        byte[] outBytes = ByteUtils.trim(inBytes, (byte)32, true, 0);
        assertArrayEquals(new byte[]{}, outBytes);
    }

    @Test
    public void testTrimRightMinLength1() throws Exception {
        byte[] inBytes = new byte[] { (byte)65, (byte)32, (byte)32 };
        byte[] outBytes = ByteUtils.trim(inBytes, (byte)32, true, 1);
        assertArrayEquals(new byte[]{(byte) 65}, outBytes);
    }

    @Test
    public void testTrimRightMinLength1OnlyPadding() throws Exception {
        byte[] inBytes = new byte[] { (byte)32, (byte)32, (byte)32 };
        byte[] outBytes = ByteUtils.trim(inBytes, (byte)32, true, 1);
        assertArrayEquals(new byte[]{(byte) 32}, outBytes);
    }

    @Test
    public void testTrimRightMinLength1Empty() throws Exception {
        expectedEx.expect(RuntimeException.class);
        expectedEx.expectMessage("src array is smaller than minLength");
        byte[] inBytes = new byte[] { };
        byte[] outBytes = ByteUtils.trim(inBytes, (byte)32, true, 1);
        assertArrayEquals(new byte[]{}, outBytes);
    }
}