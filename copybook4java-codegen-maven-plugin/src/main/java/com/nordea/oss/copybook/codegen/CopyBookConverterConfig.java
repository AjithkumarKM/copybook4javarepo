/*
 * Copyright (c) 2015. Troels Liebe Bentsen <tlb@nversion.dk>
 * Copyright (c) 2016. Nordea Bank AB
 * Licensed under the MIT license (LICENSE.txt)
 */

package com.nordea.oss.copybook.codegen;

public class CopyBookConverterConfig {
    String input;
    String output;
    String language = "java";
    String charset = "UTF-8";
    String accessor;
    String packageName;

    public CopyBookConverterConfig setInput(String input) {
        this.input = input;
        return this;
    }

    public CopyBookConverterConfig setOutput(String output) {
        this.output = output;
        return this;
    }

    public CopyBookConverterConfig setLanguage(String language) {
        this.language = language;
        return this;
    }

    public CopyBookConverterConfig setCharset(String charset) {
        this.charset = charset;
        return this;
    }

    public CopyBookConverterConfig setAccessor(String accessor) {
        this.accessor = accessor;
        return this;
    }

    public CopyBookConverterConfig setPackageName(String packageName) {
        this.packageName = packageName;
        return this;
    }
}