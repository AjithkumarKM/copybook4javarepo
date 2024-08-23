/*
 * Copyright (c) 2015. Troels Liebe Bentsen <tlb@nversion.dk>
 * Copyright (c) 2016. Nordea Bank AB
 * Licensed under the MIT license (LICENSE.txt)
 */

package com.nordea.oss.copybook.serializers;

import com.nordea.oss.copybook.CopyBookSerializer;
import com.nordea.oss.copybook.annotations.CopyBook;
import com.nordea.oss.copybook.annotations.CopyBookLine;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class CopyBookMapperFullTest {
    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @CopyBook(charset = "UTF-8", type = FullMapper.class)
    public static class RequestTest {
        @CopyBookLine("01 ID PIC 9(8).")
        public int id;
        @CopyBookLine("01 CMD PIC X(10).")
        public String command;
        @CopyBookLine("01 HELLO.")
        public RequestMessage hello;
        @CopyBookLine("01 HELLOCNT PIC 9(2).")
        public int hellos_count;
        @CopyBookLine("01 ARGCNT PIC 9(2).")
        public int args_count;
        @CopyBookLine("01 MSGCNT PIC 9(2).")
        public int messages_count;
        @CopyBookLine("01 HELLOS OCCURS 3 TIMES.")
        public RequestMessage[] hellos;
        @CopyBookLine("01 ARGS OCCURS 10 TIMES.")
        @CopyBookLine("02 ARG PIC X(8).")
        public String[] args;
        @CopyBookLine("01 MSGS OCCURS 2 TIMES.")
        public RequestMessage[] messages;
    }

    @CopyBook()
    public static class RequestMessage {
        @CopyBookLine("02 TITLE PIC X(5).")
        public String title;
        @CopyBookLine("02 BODY PIC X(10).")
        public String body;

        public RequestMessage() {

        }

        public RequestMessage(String title, String body) {
            this.title = title;
            this.body = body;
        }
    }

    @Test
    public void testSerializeDeserialize() throws Exception {
        // Build test object
        RequestTest requestTest = new RequestTest();
        requestTest.id = 1;
        requestTest.command = "cmd1234()";
        requestTest.hello = new RequestMessage("Hello", "Body1234");
        requestTest.hellos = new RequestMessage[] { new RequestMessage("abc", "1234ydob") };
        requestTest.hellos_count = requestTest.hellos.length;
        requestTest.messages = new RequestMessage[] { new RequestMessage("msg1", "stuff123"), new RequestMessage("msg2", "stuff12345") };
        requestTest.messages_count = requestTest.messages.length;
        requestTest.args = new String[]{ "do", "stuff" };
        requestTest.args_count = requestTest.args.length;

        // Serializer and Deserializer object to and from bytes
        CopyBookSerializer requestTestSerializer = new CopyBookSerializer(RequestTest.class);
        byte[] bytes = requestTestSerializer.serialize(requestTest);
        RequestTest requestTest1 = requestTestSerializer.deserialize(bytes, RequestTest.class);

        assertEquals(requestTest.id, requestTest1.id);
        assertEquals(requestTest.command, requestTest1.command);
        assertEquals(requestTest.hello.title, requestTest1.hello.title);
        assertEquals(requestTest.hello.body, requestTest1.hello.body);
        assertEquals(requestTest.messages_count, requestTest1.messages_count);
        assertEquals(requestTest.messages.length, requestTest1.messages.length);
        assertEquals(requestTest.messages[0].title, requestTest1.messages[0].title);
        assertEquals(requestTest.messages[0].body, requestTest1.messages[0].body);
        assertEquals(requestTest.messages[1].title, requestTest1.messages[1].title);
        assertEquals(requestTest.messages[1].body, requestTest1.messages[1].body);
        assertEquals(requestTest.args_count, requestTest1.args_count);
        assertEquals(requestTest.args.length, requestTest1.args.length);
        assertEquals(requestTest.args[0], requestTest1.args[0]);
        assertEquals(requestTest.args[1], requestTest1.args[1]);
    }

    @Test
    public void testSerializeDeserializeNullValues() throws Exception {
        // Build test object
        RequestTest requestTest = new RequestTest();
        requestTest.hellos = new RequestMessage[] { null };
        requestTest.hellos_count = requestTest.hellos.length;

        // Serializer and Deserializer object to and from bytes
        CopyBookSerializer requestTestSerializer = new CopyBookSerializer(RequestTest.class);
        byte[] bytes = requestTestSerializer.serialize(requestTest);
        RequestTest requestTest1 = requestTestSerializer.deserialize(bytes, RequestTest.class);

        assertEquals(0, requestTest1.args.length);
    }

    @Test
    public void testSerializeDeserializeEmptyList() throws Exception {
        // Build test object
        RequestTest requestTest = new RequestTest();
        requestTest.args = new String[]{};
        requestTest.messages = new RequestMessage[] {};

        // Serializer and Deserializer object to and from bytes
        CopyBookSerializer requestTestSerializer = new CopyBookSerializer(RequestTest.class);
        byte[] bytes = requestTestSerializer.serialize(requestTest);
        RequestTest requestTest1 = requestTestSerializer.deserialize(bytes, RequestTest.class);

        assertEquals(0, requestTest1.args.length);
        assertEquals(0, requestTest1.messages.length);
    }

    // Depending on fields in sub field
    @CopyBook(type = FullMapper.class)
    public static class CounterDependingInOn {
        @CopyBookLine("02 COUNT PIC 9(2).")
        private int count;
    }
    @CopyBook(type = FullMapper.class)
    public static class StringArrayToStringArrayDependingInOn {
        @CopyBookLine("01 SUBFIELD.")
        private CounterDependingInOn subfield;
        @CopyBookLine("01 RESULT.")
        @CopyBookLine("02 FIELDS OCCURS 0 TO 10 TIMES PIC X(8) DEPENDING ON COUNT IN SUBFIELD.")
        private String[] fields;
    }

    @org.junit.Test
    public void testStringArrayToStringArrayDependingOnIn() throws Exception {
        CopyBookSerializer serializer = new CopyBookSerializer(StringArrayToStringArrayDependingInOn.class);
        StringArrayToStringArrayDependingInOn test = new StringArrayToStringArrayDependingInOn();
        test.fields = new String []{ "test", "test2" };
        test.subfield = new CounterDependingInOn();
        test.subfield.count = test.fields.length;
        byte[] testBytes = serializer.serialize(test);
        assertEquals(2 + 8 * test.fields.length, testBytes.length);
        StringArrayToStringArrayDependingInOn test2 = serializer.deserialize(testBytes, StringArrayToStringArrayDependingInOn.class);
        assertArrayEquals(test.fields, test2.fields);
    }

    @CopyBook(type = FullMapper.class)
    public static class TwoStringArrayToStringArrayDependingOn {
        @CopyBookLine("02 FIELDSCOUNT PIC 9(2).")
        private int xfields1;
        @CopyBookLine("02 FIELDSCOUNT PIC 9(2).")
        private int xfields2;
        @CopyBookLine("02 FIELDS OCCURS 0 TO 10 TIMES PIC X(8) DEPENDING ON FIELDSCOUNT.")
        private String[] fields1;
        @CopyBookLine("02 FIELDS OCCURS 0 TO 10 TIMES PIC X(8) DEPENDING ON FIELDSCOUNT.")
        private String[] fields2;
    }

    @org.junit.Test
    public void twoStringArrayToStringArrayDependingOnTest() throws Exception {
        CopyBookSerializer serializer = new CopyBookSerializer(TwoStringArrayToStringArrayDependingOn.class);
        TwoStringArrayToStringArrayDependingOn test = new TwoStringArrayToStringArrayDependingOn();
        test.fields1 = new String []{ "test", "test2" };
        test.fields2 = new String []{ "tset", "2tset" };
        test.xfields1 = test.fields1.length;
        test.xfields2 = test.fields2.length;
        byte[] testBytes = serializer.serialize(test);
        assertEquals(2 + 2 + 8 * test.fields1.length + 8 * test.fields2.length, testBytes.length);
        TwoStringArrayToStringArrayDependingOn test2 = serializer.deserialize(testBytes, TwoStringArrayToStringArrayDependingOn.class);
        assertArrayEquals(test.fields1, test2.fields1);
        assertArrayEquals(test.fields2, test2.fields2);
    }

}