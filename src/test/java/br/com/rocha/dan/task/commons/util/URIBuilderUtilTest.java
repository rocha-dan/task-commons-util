package br.com.rocha.dan.task.commons.util;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

import static java.util.Collections.emptyList;
import static java.util.Collections.enumeration;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.web.context.request.RequestContextHolder.setRequestAttributes;

public class URIBuilderUtilTest {

    @Before
    public void before() {
        initMocks(this);
        HttpServletRequest servletRequest = Mockito.mock(HttpServletRequest.class);
        when(servletRequest.getRequestURL()).thenReturn(new StringBuffer("http://localhost.com.br"));
        when(servletRequest.getHeaderNames()).thenReturn(enumeration(emptyList()));
        setRequestAttributes(new ServletRequestAttributes(servletRequest));
    }

    @Test
    public void getPartialContentHeader() {
        Map<String, String> filters = new HashMap<>();
        filters.put("IDE", "Eclipse");

        Integer limit = 10;
        Integer offset = 0;

        MultiValueMap<String, String> partialContentHeader = URIBuilderUtil.getPartialContentHeader(filters, limit, offset);

        assertEquals("http://localhost.com.br?IDE=Eclipse&limit=10&offset=0", partialContentHeader.get("Location").get(0));
    }
}