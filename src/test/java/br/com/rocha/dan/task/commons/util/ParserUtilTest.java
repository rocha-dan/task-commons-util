package br.com.rocha.dan.task.commons.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.lang.reflect.Constructor;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import br.com.rocha.dan.task.commons.util.objects.TestObject;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ParserUtilTest {

    @Test
    public void convertToJSON() throws Exception {
        String json = ParserUtil.convertToJSON(new TestObject("01", true));
        assertEquals("{\"id\":\"01\",\"active\":true,\"listStr\":[]}", json);
    }

    @Test
    public void stringToBase64() {
        assertEquals("VEVTVCBCQVNFNjQ=", ParserUtil.stringToBase64("TEST BASE64"));
    }

    @Test
    public void base64ToString() {
        assertEquals("TEST BASE64", ParserUtil.base64ToString("VEVTVCBCQVNFNjQ="));
    }

    @Test
    public void failBase64ToStringWhenInvalidInput() {
        assertEquals("", ParserUtil.base64ToString("Base64 não valida"));
    }

    @Test
    public void failStringToBase64WhenStringIsNull() {
        assertEquals("", ParserUtil.stringToBase64(null));
    }

    @Test
    public void shouldSucceedConvertValue() {
        TestObject testObject = new TestObject("1", true);
        TestObject testObject1 = ParserUtil.convertValue(testObject, TestObject.class);
        assertNotNull(testObject1);
    }

    @Test
    public void shouldSucceedConvertValues() {
        TestObject testObject = new TestObject("1", true);
        List<TestObject> list = Arrays.asList(testObject);
        List<TestObject> testObject1 = ParserUtil.convertValues(list, TestObject.class);
        assertNotNull(testObject1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void convertJSONToObjectError() throws Exception {
        String json = "[{\"id\":\"01\",\"active\":true,\"listStr\":[]}]";
        TestObject testObject = ParserUtil.convertJSONToObject(json, TestObject.class);
        assertNotNull(testObject);
    }

    public void convertJSONToObjectSucceed() throws Exception {
        String json = "{\"id\":\"01\",\"active\":true,\"listStr\":[]}";
        TestObject testObject = ParserUtil.convertJSONToObject(json, TestObject.class);
        assertNotNull(testObject);
    }

    @Test(expected = IllegalArgumentException.class)
    public void convertJSONToListError() throws Exception {
        String json = "{\"id\":\"01\",\"active\":true,\"listStr\":[]}";
        List<TestObject> testObject = ParserUtil.convertJSONToList(json, TestObject.class);
        assertNotNull(testObject);
    }

    @Test
    public void convertJSONToList() throws Exception {
        String json = "[{\"id\":\"01\",\"active\":true,\"listStr\":[]}]";
        List<TestObject> testObject = ParserUtil.convertJSONToList(json, TestObject.class);
        assertNotNull(testObject);
    }

    @Test
    public void testCreateJsr310ObjectMapper() throws Exception {
        ObjectMapper mapper = ParserUtil.createJsr310ObjectMapper(null);
        LocalDate localDate = LocalDate.of(2000, 1, 1);
        assertEquals(localDate, mapper.readValue(mapper.writeValueAsString(localDate), LocalDate.class));
    }

    @Test
    public void constructorCoverage() throws Exception {
        Constructor<ParserUtil> c = ParserUtil.class.getDeclaredConstructor();
        c.setAccessible(true);
        c.newInstance();
    }
}