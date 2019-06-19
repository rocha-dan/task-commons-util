package br.com.rocha.dan.task.commons.util;

import org.apache.commons.beanutils.BeanUtilsBean;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import static java.util.Objects.isNull;

public class NonCopyNullable extends BeanUtilsBean {

    @Override
    public void copyProperty(Object dest, String name, Object value) throws IllegalAccessException, InvocationTargetException {
        if (isNull(value)) return;
        if (value instanceof ArrayList) {
        	ArrayList<?> array = (ArrayList<?>) value;
        	if (array.isEmpty())
            	return;
        }
        super.copyProperty(dest, name, value);
    }
}