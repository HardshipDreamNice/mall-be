package com.torinosrc.model.view.common;

import com.torinosrc.model.entity.shop.Shop;

import java.util.List;

/**
 * <b><code>CustomPage</code></b>
 * <p>
 * class_comment
 * </p>
 * <b>Create Time:</b>12:20
 *
 * @author PanXin
 * @version 1.0.0
 * @since mall-be  1.0.0
 */
public class CustomPage<T> {

    private  long totalElements;

    private List<T> content;

    public long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }

    public List<T> getContent() {
        return content;
    }

    public void setContent(List<T> content) {
        this.content = content;
    }
}
