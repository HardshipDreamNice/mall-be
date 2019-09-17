package com.torinosrc.response.filter;

import com.fasterxml.jackson.core.JsonProcessingException;

public class test {

    public static void main(String args[]) throws JsonProcessingException {

        CustomerJsonSerializer cjs= new CustomerJsonSerializer();
        // 设置转换 Article 类时，只包含 id, name
        cjs.filter(Article.class, "id,title", null);

        String include = cjs.toJson(new Article());

        cjs = new CustomerJsonSerializer();
        // 设置转换 Article 类时，过滤掉 id, name
        cjs.filter(Article.class, null, "id,title");

        String filter = cjs.toJson(new Article());

        System.out.println("include: " + include);
        System.out.println("filter: " + filter);
//        ObjectMapper mapper = new ObjectMapper();
//        JacksonJsonFilter jacksonFilter = new JacksonJsonFilter();
//
//        // 过滤除了 id,title 以外的所有字段，也就是序列化的时候，只包含 id 和 title
//        String[] includes="id,title".split(",");
//        jacksonFilter.include(Article.class, includes);
//        mapper.setFilterProvider(jacksonFilter);  // 设置过滤器
//        mapper.addMixIn(Article.class, jacksonFilter.getClass()); // 为Article.class类应用过滤器
//        String include= mapper.writeValueAsString(new Article());
//
//
//        // 序列化所有字段，但是排除 id 和 title，也就是除了 id 和 title之外，其他字段都包含进 json
//        jacksonFilter = new JacksonJsonFilter();
//        jacksonFilter.filter(Article.class, includes);
//        mapper = new ObjectMapper();
//        mapper.setFilterProvider(jacksonFilter);
//        mapper.addMixIn(Article.class, jacksonFilter.getClass());
//
//        String filter = mapper.writeValueAsString(new Article());
//
//        System.out.println("include:" + include);
//        System.out.println("filter :" + filter);
    }

   public static class Article {
        private String id;
        private String title;
        private String content;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}
