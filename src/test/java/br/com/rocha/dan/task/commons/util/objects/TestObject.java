package br.com.rocha.dan.task.commons.util.objects;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TestObject implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    private String id;
    private Boolean active;

    private List<String> listStr = new ArrayList<>();

    public TestObject(String id, Boolean active) {
        this.id = id;
        this.active = active;
    }

    public TestObject() {
    }

    public String getId() {
        return id;
    }

    public Boolean getActive() {
        return active;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public List<String> getListStr() {
        return listStr;
    }

    public void setListStr(List<String> listStr) {
        this.listStr = listStr;
    }

    public static class FactoryTestObject {
        public static List<TestObject> getList(int size) {
            ArrayList<TestObject> testObjects = new ArrayList<>();
            for (int i = 1; i <= size; i++)
                testObjects.add(create(i));
            return testObjects;
        }

        public static TestObject create(int i) {
            return new TestObject("0" + i, (i % 2 == 0));
        }
    }
}