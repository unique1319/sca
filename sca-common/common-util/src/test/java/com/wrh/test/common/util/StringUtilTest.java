package com.wrh.test.common.util;

import com.wrh.common.util.PropertyPlaceholdeUtil;
import com.wrh.common.util.StringUtil;
import org.junit.Test;

import java.time.LocalDateTime;

/**
 * @author wrh
 * @version 1.0
 * @date 2020/10/13 14:28
 * @describe
 */
public class StringUtilTest {

    @Test
    public void resolveByTimeFmt() {
        System.out.println(StringUtil.frontCompWithZore(6,3));
        System.out.println(StringUtil.frontCompWithZore(24,3));
    }

}
