package com.torinosrc.response.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.torinosrc.response.json.JSON;
import org.apache.commons.lang3.StringUtils;

/**
 * 自定义json序列化
 */
public class CustomerJsonSerializer {
    ObjectMapper mapper = new ObjectMapper();
    JacksonJsonFilter jacksonFilter = new JacksonJsonFilter();

    /**
     * @param clazz target type
     * @param include include fields
     * @param filter filter fields
     */
    public void filter(Class<?> clazz, String include, String filter) {
        if (clazz == null) return;
        if (StringUtils.isNotBlank(include)) {
            jacksonFilter.include(clazz, include.split(","));
        }
        if (StringUtils.isNotBlank(filter)) {
            jacksonFilter.filter(clazz, filter.split(","));
        }
        mapper.addMixIn(clazz, jacksonFilter.getClass());
    }

    public String toJson(Object object) throws JsonProcessingException {
        mapper.setFilterProvider(jacksonFilter);
        return mapper.writeValueAsString(object);
    }
    public void filter(JSON json) {
        this.filter(json.type(), json.include(), json.filter());
    }

    public void filter(Class<?> clazz,String include) {
        this.filter(clazz, include, null);
    }
}
