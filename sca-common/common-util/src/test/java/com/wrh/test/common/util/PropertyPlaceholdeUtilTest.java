package com.wrh.test.common.util;

import com.wrh.common.util.PropertyPlaceholdeUtil;
import org.junit.Test;

import java.time.LocalDateTime;

/**
 * @author wrh
 * @version 1.0
 * @date 2020/10/13 14:28
 * @describe
 */
public class PropertyPlaceholdeUtilTest {

    @Test
    public void resolveByTimeFmt() {
        String content = "E:/gribprocess/fy4a_c004_grb/${yyyy}/${yyyyMMdd}/wrh${yyyyMMddHHmmss}.txt";
        System.out.println(PropertyPlaceholdeUtil.resolveByTimeFmt(content, LocalDateTime.now()));
    }

}
