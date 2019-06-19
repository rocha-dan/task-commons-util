package br.com.rocha.dan.task.commons.util;

import org.springframework.data.domain.Page;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Map;

import static java.util.Objects.nonNull;

public class URIBuilderUtil {

    private URIBuilderUtil() {
    }

    public static MultiValueMap<String, String> getPartialContentHeader(final Map<String, String> filters, Integer limit, Integer offset) {

        MultiValueMap<String, String> header = new LinkedMultiValueMap<>();

        ServletUriComponentsBuilder uriBuilder = ServletUriComponentsBuilder.fromCurrentRequestUri();
        if (nonNull(filters)) filters.forEach(uriBuilder::queryParam);

        uriBuilder.queryParam("limit", String.valueOf(limit));
        uriBuilder.queryParam("offset", String.valueOf(offset));

        header.add("Location", uriBuilder.toUriString());
        return header;
    }

    public static MultiValueMap<String, String> getPartialContentHeader(Page<?> page, final Map<String, String> filters) {
        int nextLimit = page.nextPageable().getPageSize();
        int nextOffset = page.nextPageable().getOffset();
        return getPartialContentHeader(filters, nextLimit, nextOffset);
    }
}
