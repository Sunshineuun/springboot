package com.qiushengming.commons;

import java.beans.PropertyEditorSupport;

/**
 * @author qiushengming
 * @date 2018/6/1
 */
public final class BooleanEditor extends PropertyEditorSupport {

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        if ("true".equals(text) || "on".equals(text) || "1".equals(text)) {
            setValue(true);
        } else {
            setValue(false);
        }
    }

    @Override
    public String getAsText() {
        if (getValue() == null) {
            return null;
        }

        return getValue().toString();
    }
}
