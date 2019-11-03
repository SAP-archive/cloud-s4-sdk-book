package com.sap.cloud.s4hana.examples.addressmgr;

import org.junit.Test;

import static org.junit.Assert.*;

public class UnitTest {
    @Test
    public void test() {
        int x = new Unit().a(1,1);
        assertEquals(2, x);
    }
}
