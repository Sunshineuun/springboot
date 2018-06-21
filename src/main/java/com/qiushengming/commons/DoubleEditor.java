package com.qiushengming.commons;

import org.apache.commons.lang3.StringUtils;

import java.beans.PropertyEditorSupport;

/**
 * @author qiushengming
 * @date 2018/6/1
 */
public final class DoubleEditor extends PropertyEditorSupport {
    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        if (StringUtils.isEmpty(text)) {
            text = "0.0";
        }

        setValue(Double.parseDouble(text));
    }

    @Override
    public String getAsText() {
        if (getValue() == null) {
            return null;
        }

        return getValue().toString();
    }
}
