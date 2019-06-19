package br.com.rocha.dan.task.commons.util;

import org.junit.Test;

import static br.com.rocha.dan.task.commons.util.StringUtil.nonNullAndNotEmpty;
import static br.com.rocha.dan.task.commons.util.StringUtil.nullOrEmpty;
import static br.com.rocha.dan.task.commons.util.StringUtil.onlyNumbers;
import static br.com.rocha.dan.task.commons.util.StringUtil.removeSpecialCharacters;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Constructor;

public class StringUtilTest {

    @Test
    public void shouldOnlyNumbersRemoveLetters() {
        assertFalse(onlyNumbers("123asd3da").contains("a"));
    }

    @Test
    public void shouldOnlyNumbersRemoveDots() {
        assertFalse(onlyNumbers("12@3@a@sd3da").contains("@"));
    }

    @Test
    public void shouldOnlyNumbersRemoveAll() {
        assertTrue(onlyNumbers("adsf@#$%FA¨&%H").isEmpty());
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldOnlyNumbersNull() {
        onlyNumbers("");
    }

    @Test
    public void sholdVerifyStringNull() {
        assertTrue(nullOrEmpty(null));
    }

    @Test
    public void sholdVerifyStringEmpty() {
        assertTrue(nullOrEmpty(""));
    }

    @Test
    public void sholdVerifyStringEmptyWithSpace() {
        assertTrue(nullOrEmpty("       "));
    }

    @Test
    public void sholdVerifyStringNonNullAndNotEmpty() {
        assertTrue(nonNullAndNotEmpty("Test"));
    }
    @Test
    public void shouldRemoveSpecialCharacters() {assertEquals("Andre87 Fulano aaa",removeSpecialCharacters("André87 !@#$%&*()Fulano()_+ àãâ-*/"));}

    @Test
    public void shouldNotRemoveSpecialCharacters() {assertEquals("Serasa Digital",removeSpecialCharacters("Serasa Digital"));}


    @Test
    public void padRight() {
        assertEquals("1  ", StringUtil.padRight("1", 3));
    }

    @Test
    public void padLeft() {
        assertEquals("  191", StringUtil.padLeft("191", 5));
    }

    @Test
    public void padNumber() {
        assertEquals("00000000191", StringUtil.padNumber("191", 11));
    }

    @Test
    public void padNumberNull() {
        assertNull(StringUtil.padNumber(null, 11));
    }

    @Test
    public void padNumberInvalidInput() {
        assertEquals("", StringUtil.padNumber("x1", 11));
    }

    @Test
    public void padNumberInvalidPadding() {
        assertEquals("", StringUtil.padNumber("1", -1));
    }


    @Test
    public void constructorCoverage() throws Exception {
        Constructor<StringUtil> c = StringUtil.class.getDeclaredConstructor();
        c.setAccessible(true);
        c.newInstance();
    }
}