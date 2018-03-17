package com.dailiv.util.common;

import org.junit.Test;

import static com.dailiv.util.common.NameUtil.splitName;
import static org.junit.Assert.*;

/**
 * Created by aldo on 3/16/18.
 */
public class NameUtilTest {

    @Test
    public void testSplitName() {

        String name1 = "Aldo Makmur";
        assertEquals("Aldo", splitName(name1).first);
        assertEquals("Makmur", splitName(name1).second);

        String name2 = "Aldo";
        assertEquals("Aldo", splitName(name2).first);
        assertEquals("Aldo", splitName(name2).second);

        String name3 = "Aldo ";
        assertEquals("Aldo", splitName(name3).first);
        assertEquals("Aldo", splitName(name3).second);

        String name4 = "Aldo Makmur Test";
        assertEquals("Aldo Makmur", splitName(name4).first);
        assertEquals("Test", splitName(name4).second);
    }
}