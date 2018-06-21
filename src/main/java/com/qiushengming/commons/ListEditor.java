package com.qiushengming.commons;

import com.qiushengming.utils.JackJson;
import org.apache.commons.lang3.StringUtils;

import java.beans.PropertyEditorSupport;
import java.util.List;

/**
 * @author qiushengming
 * @date 2018/6/21
 */
public class ListEditor
    extends PropertyEditorSupport {

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        if (StringUtils.isEmpty(text)) {
            text = "[]";
        }

        setValue(JackJson.fromJsonToObject(text, List.class));
    }

    @Override
    public String getAsText() {
        if (getValue() == null) {
            return null;
        }

        return getValue().toString();
    }
}
