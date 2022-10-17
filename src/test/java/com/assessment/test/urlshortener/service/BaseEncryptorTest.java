package com.assessment.test.urlshortener.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class BaseEncryptorTest {

    private final BaseEncryptor baseEncryptor = new BaseEncryptor();

    @Test
    public void encode_lessThan62() {
        assertEquals("k", baseEncryptor.encode(10L));
    }

    @Test
    public void encode_moreThan62() {
        assertEquals("bq", baseEncryptor.encode(78L));
    }

    @Test
    public void decode_singleCharacter() {
        assertEquals(11, (long)baseEncryptor.decode("l"));
    }
}
