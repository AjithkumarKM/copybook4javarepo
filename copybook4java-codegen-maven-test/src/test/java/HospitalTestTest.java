/*
 * Copyright (c) 2015. Troels Liebe Bentsen <tlb@nversion.dk>
 * Copyright (c) 2016. Nordea Bank AB
 * Licensed under the MIT license (LICENSE.txt)
 */

import com.nordea.oss.copybook.CopyBookSerializer;
import mypackage.HospitalTest;
import org.junit.Test;
import mypackage.mypackagename.HospitalTest2;

import static org.junit.Assert.*;

public class HospitalTestTest {

    @Test
    public void testNewGeneratedClass() {
        HospitalTest hospitalTest = new HospitalTest();
        HospitalTest2 hospitalTest2 = new HospitalTest2();
        CopyBookSerializer requestSerializer = new CopyBookSerializer(HospitalTest.class);
        CopyBookSerializer responseSerializer = new CopyBookSerializer(HospitalTest2.class);

        HospitalTest request = new HospitalTest();

        byte[] requestBytes = requestSerializer.serialize(request);


    }
}