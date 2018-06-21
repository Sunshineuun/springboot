package com.qiushengming.commons;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

import java.beans.PropertyEditorSupport;

public class EnumEditor
        extends PropertyEditorSupport {

    private final Class<?> clazz;

    public EnumEditor(Class<?> clazz) {
        Assert.notNull(clazz);
        this.clazz = clazz;
    }

    @Override
    @SuppressWarnings("rawtypes")
    public void setAsText(String text) throws IllegalArgumentException {
        if (StringUtils.isEmpty(text)) {
            return;
        }

        int value = Integer.parseInt(text);
        if (clazz.isEnum()) {
            Enum[] enums = (Enum[]) clazz.getEnumConstants();
            for (Enum e : enums) {
                if (e.ordinal() == value) {
                    setValue(e);
                    break;
                }
            }
        }
    }

    @Override
    @SuppressWarnings("rawtypes")
    public String getAsText() {
        if (getValue() == null) {
            return null;
        }

        if (clazz.isEnum()) {
            Enum[] enums = (Enum[]) clazz.getEnumConstants();
            for (Enum e : enums) {
                if (e == getValue()) {
                    return Integer.toString(e.ordinal());
                }
            }
        }
        return getValue().toString();
    }
}
