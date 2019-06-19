package br.com.rocha.dan.task.commons.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ResourceBundle;


/**
 * Created by mauriciourbanfilho on 05/10/2017.
 */
public final class PropertiesUtil {

    private PropertiesUtil() {
    }

    private static final String APPLICATION_PROPERTIES = "application";
    private static final String ERROR_PROPERTIES = "messages_en";
    private static final Logger LOGGER = LoggerFactory.getLogger(PropertiesUtil.class);

    /**
     * @param key identificador da propriedade
     * @param fileName nome do arquivo de propriedades
     * @return valor da propriedade
     */
    public static String getProperty(final String key, final String fileName) {
        return getPropertyValue(key, fileName);
    }

    /**
     * @param key identificador da propriedade
     * @return valor da propriedade
     */
    public static String getProperty(final String key) {
        return getPropertyValue(key, null);
    }

    private static String getPropertyValue(final String key, final String propertyFile) {
        String value = null;

        try {
            ResourceBundle bundle;
            if (propertyFile != null && !"".equals(propertyFile)) {
                bundle = ResourceBundle.getBundle(propertyFile);
            } else {
                bundle = ResourceBundle.getBundle(APPLICATION_PROPERTIES);
            }
            value = bundle.getString(key);

        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return value;
    }

    public static String getErrorMessage(final String key) {

        String value = null;

        try {
            ResourceBundle bundle = ResourceBundle.getBundle(ERROR_PROPERTIES);
            value = bundle.getString(key);

        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return value;
    }
}