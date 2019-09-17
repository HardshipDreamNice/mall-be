package com.torinosrc.response.filter;

/**
 * 返回字段过滤模型
 */
public class Field {
    private String include;
    private String filter;

    public String getInclude() {
        return include;
    }

    public void setInclude(String include) {
        this.include = include;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }
}
