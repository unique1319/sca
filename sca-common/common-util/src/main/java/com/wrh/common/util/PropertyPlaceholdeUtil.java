package com.wrh.common.util;

import org.springframework.util.PropertyPlaceholderHelper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author wrh
 * @version 1.0
 * @date 2020/10/13 14:28
 * @describe
 */
public final class PropertyPlaceholdeUtil {

    private static final PropertyPlaceholderHelper defaultHelper = new PropertyPlaceholderHelper("${", "}");

    public static String resolveByTimeFmt(String str, LocalDateTime t) {
        return defaultHelper.replacePlaceholders(str, fmt -> LocalDateTimeUtil.localDateTimeToString(t, fmt));
    }

    public static List<String> getModels(String content) {
        String pattern = "(?<=\\{).*?(?=})";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(content);
        List<String> matchStrs = new ArrayList<>();
        while (m.find()) {
            matchStrs.add(m.group());
        }
        return matchStrs;
    }

}
