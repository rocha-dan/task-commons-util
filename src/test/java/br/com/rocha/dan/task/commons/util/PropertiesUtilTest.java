package br.com.rocha.dan.task.commons.util;

import org.junit.Test;

import static br.com.rocha.dan.task.commons.util.PropertiesUtil.getErrorMessage;
import static br.com.rocha.dan.task.commons.util.PropertiesUtil.getProperty;
import static org.junit.Assert.assertNull;


public class PropertiesUtilTest {

    @Test()
    public void shouldGetPropertiesWithFileName() {
        assertNull(getProperty("1", "messages_error"));
    }

    @Test()
    public void shouldGetPropertiesWithoutFilename() {
        assertNull(getProperty("1"));
    }

    @Test
    public void shouldGetErrorMessage() {
        assertNull(getErrorMessage("1"));
    }
}
