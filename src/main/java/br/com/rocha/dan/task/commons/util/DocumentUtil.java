package br.com.rocha.dan.task.commons.util;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.converters.BigDecimalConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.nonNull;


public class DocumentUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(DocumentUtil.class);

    private DocumentUtil(){}
    
    /**
     * Descrição: Copia as propriedades de um documento para outro
     *
     * @param target
     * @param source
     */
    public static void copyProperties(final Object target, final Object source) {
        try {
            BeanUtilsBean notNull = new NonCopyNullable();
            BigDecimalConverter bigDecimalConverter = new BigDecimalConverter(null);
            notNull.getConvertUtils().register(bigDecimalConverter, BigDecimal.class);
            notNull.copyProperties(target, source);
        } catch (Exception e) {
            String message = "Error to copy properties between objects";
            LOGGER.error(message, e);
            throw new IllegalArgumentException(message, e);
        }
    }

    public static <T> T findObjectInUserAccountById(List<T> listTarget, String id) {
        T obj = listTarget.stream().filter(
                e -> getValueProperty("getId", e).equals((Object) id) && getValueProperty("getActive", e).equals(true))
                .findFirst().orElse(null);

        return obj;
    }

    public static <T> List<T> getListObjectActive(final List<T> listTarget) {
        try {
            final List<T> listObjectActive = new ArrayList<>();

            listTarget.forEach(e -> {
                Object value = getValueProperty("getActive", e);
                if (nonNull(value) && value.equals(true))
                    listObjectActive.add(e);
            });

            return listObjectActive;
        } catch (Exception e) {
            String message = "Error trying to load list of asset items";
            LOGGER.error(message, e);
            throw new IllegalArgumentException(message, e);
        }
    }

    public static Pageable getPageable(Integer limit, Integer offset) {
        int registers = 100; // valor default
        int page = 0; // valor default

        if (nonNull(limit)) {
            if (limit == 0) {
                throw new IllegalArgumentException("Limit must be greater than zero");
            }
            registers = limit;
        }

        if (nonNull(offset)) {
            int intOffSet = offset;
            if (intOffSet > 0 && intOffSet >= registers) {
                page = (intOffSet / registers);
            }
        }

        return new PageRequest(page, registers);
    }

    private static Object getValueProperty(String property, Object object) {
        for (Method method : object.getClass().getDeclaredMethods()) {
            if (Modifier.isPublic(method.getModifiers())
                    && method.getParameterTypes().length == 0
                    && method.getReturnType() != void.class
                    && method.getName().equalsIgnoreCase(property)) {
                try {
                    return method.invoke(object);
                } catch (Exception e) {
                    String message = "Erro ao tentar carregar a lista de objetos ativos";
                    LOGGER.error(message, e);
                    throw new IllegalArgumentException(message, e);
                }
            }
        }
        return null;
    }
}