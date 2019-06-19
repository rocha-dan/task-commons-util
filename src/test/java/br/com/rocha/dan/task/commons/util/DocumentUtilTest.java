package br.com.rocha.dan.task.commons.util;

import br.com.rocha.dan.task.commons.util.objects.TestObject;
import org.junit.Test;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static java.util.Collections.singletonList;
import static org.junit.Assert.*;

public class DocumentUtilTest {

    @Test
    public void getListObjectInactive() throws Exception {
        List<Object> result = DocumentUtil.getListObjectActive(singletonList(new TestObject("001", false)));
        assertTrue(result.isEmpty());
    }

    @Test
    public void getListObjectActive() throws Exception {
        List<Object> result = DocumentUtil.getListObjectActive(singletonList(new TestObject("001", true)));
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
    }

    @Test
    public void getPageable() throws Exception {
        Pageable result = DocumentUtil.getPageable(10, 0);
        assertEquals(0, result.getOffset());
        assertEquals(0, result.getPageNumber());
        assertEquals(10, result.getPageSize());
    }

    @Test
    public void testFindObjectInUserAccountById() throws Exception {
        TestObject result = DocumentUtil.findObjectInUserAccountById(TestObject.FactoryTestObject.getList(2), "02");
        assertEquals("02", result.getId());
    }

    @Test
    public void failFindObjectInUserAccountByIdDontHasIdField() throws Exception {
        TestObject result = DocumentUtil.findObjectInUserAccountById(TestObject.FactoryTestObject.getList(2), "02");
        assertEquals("02", result.getId());
    }

    @Test
    public void testGetListObjectActive() throws Exception {
        List<TestObject> result = DocumentUtil.getListObjectActive(TestObject.FactoryTestObject.getList(10));
        assertEquals(5, result.size());
    }

    @Test
    public void copyProperties (){
        TestObject source = TestObject.FactoryTestObject.create(1);
        TestObject target = new TestObject();

        DocumentUtil.copyProperties(target, source);

        assertEquals(target.getId(), source.getId());
        assertEquals(target.getActive(), source.getActive());
    }

    @Test
    public void failCopyPropertiesSourceNull (){
        try {
            DocumentUtil.copyProperties(new TestObject(), null);
        } catch (Exception e) {
            assertEquals("Error to copy properties between objects", e.getMessage());
        }
    }

    @Test
    public void failCopyPropertiesTargetNull (){
        try {
            DocumentUtil.copyProperties(null , new TestObject());
        } catch (IllegalArgumentException e) {
            assertEquals("Error to copy properties between objects", e.getMessage());
        }
    }

    @Test
    public void failGetListObjectActive(){
        try {
            DocumentUtil.getListObjectActive(null);
            fail("Method must throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("Error trying to load list of asset items", e.getMessage());
        }
    }

    @Test
    public void failGetPageableLimitZero() {
        try {
            DocumentUtil.getPageable(0, 0);
            fail("Method must throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("Limit must be greater than zero", e.getMessage());
        }
    }

    @Test
    public void getPageableHight() throws Exception {
        Pageable result = DocumentUtil.getPageable(10, 150);

        assertEquals(150, result.getOffset());
        assertEquals(15, result.getPageNumber());
        assertEquals(10, result.getPageSize());
    }
}