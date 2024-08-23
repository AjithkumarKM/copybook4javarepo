/*
 * Copyright (c) 2015. Troels Liebe Bentsen <tlb@nversion.dk>
 * Copyright (c) 2016. Nordea Bank AB
 * Licensed under the MIT license (LICENSE.txt)
 */

package com.nordea.oss.copybook;

import com.nordea.oss.copybook.serializers.CopyBookParser;
import com.nordea.oss.ByteUtils;
import com.nordea.oss.copybook.exceptions.CopyBookException;
import com.nordea.oss.copybook.serializers.CopyBookMapper;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class CopyBookSerializer {
    private CopyBookMapper serializer;

    public CopyBookSerializer(Class<?> type) {
        this(type, false);
    }

    public CopyBookSerializer(Class<?> type, boolean debug) {
        this(type, null, debug);
    }

    public CopyBookSerializer(Class<?> type, Class<CopyBookMapper> mapper, boolean debug) {
        CopyBookParser parser = new CopyBookParser(type, debug);
        try {
            if(mapper != null) {
                serializer = mapper.getDeclaredConstructor().newInstance();

            } else {
                serializer = parser.getSerializerClass().getDeclaredConstructor().newInstance();
            }
            serializer.initialize(parser.getConfig());

        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new CopyBookException("Failed to load Serialization class("+ parser.getSerializerClass().getSimpleName() +")", e);
        }
    }

    public <T> byte[] serialize(T obj) {
        return serializer.serialize(obj);
    }

    public <T> T deserialize(byte[] bytes, Class<T> type) {
        return serializer.deserialize(bytes, type);
    }

    public <T> T deserialize(InputStream inputStream, Class<T> type) throws IOException {
        return deserialize(ByteUtils.toByteArray(inputStream), type);
    }

    public List<CopyBookException> getErrors() {
        return null; // TODO: Implement depending on errorValue
    }

    public int getMinRecordSize() {
        return serializer.getMinRecordSize();
    }

    public int getMaxRecordSize() {
        return serializer.getMaxRecordSize();
    }

}
