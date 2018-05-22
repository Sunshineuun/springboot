package com.qiushengming;

import org.apache.commons.collections.map.CaseInsensitiveMap;
import org.springframework.util.LinkedCaseInsensitiveMap;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by qiushengming on 2018/5/22.
 */
public class Tests {
    public static void main(String[] args) {
        Map map = new CaseInsensitiveMap();
        map.put("A", "A");
        map.put("b", "b");
        map.put("C", "c");
        map.put("d", "D");
        map.put("下", "D");
        System.out.println(map.get("a"));
        System.out.println(map.get("B"));
        System.out.println(map.get("c"));
        System.out.println(map.get("下"));
        System.out.println(map);

    }
}
