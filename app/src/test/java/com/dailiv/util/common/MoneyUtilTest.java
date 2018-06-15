package com.dailiv.util.common;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by aldo on 5/20/18.
 */
public class MoneyUtilTest {

    @Test
    public void getMoney() throws Exception {
        int a = 123456;
        int b = 23;
        int c = 1234567890;
        int d = 0;

        assertEquals("Rp123.456", MoneyUtil.getMoney(a));
        assertEquals("Rp23", MoneyUtil.getMoney(b));
        assertEquals("Rp1.234.567.890", MoneyUtil.getMoney(c));
        assertEquals("Rp0", MoneyUtil.getMoney(d));
    }

}