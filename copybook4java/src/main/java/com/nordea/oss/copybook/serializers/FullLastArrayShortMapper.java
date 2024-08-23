/*
 * Copyright (c) 2015. Troels Liebe Bentsen <tlb@nversion.dk>
 * Copyright (c) 2016. Nordea Bank AB
 * Licensed under the MIT license (LICENSE.txt)
 */

package com.nordea.oss.copybook.serializers;

public class FullLastArrayShortMapper extends FullMapper {
    @Override
    public void initialize(CopyBookSerializerConfig config) {
        super.initialize(config);
        CopyBookField lastField = this.fields.get(this.fields.size() - 1);
        if (lastField.isArray()) {
            lastField.setMinOccurs(0);
        }
    }
}
