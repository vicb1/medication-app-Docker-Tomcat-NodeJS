package com.sml.test;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.sml.java.entities.UMS;

public class UMSTest {

	@Test
	public void test() {
		UMS ums1 = new UMS("5 mg = 1 tab(s), PO, qDay, # 90 tab(s), 3 Refill(s)");
		assertTrue(ums1.isBreakfast());
		UMS ums2 = new UMS("2.5 mg = 1 tab(s), PO, q12hr, # 180 tab(s), 1 Refill(s)");
		assertTrue(ums2.isBreakfast() && ums2.isDinner());
		UMS ums3 = new UMS("10 mg = 1 tab(s), PO, qDay, # 90 tab(s), 1 Refill(s)");
		assertTrue(ums3.isBreakfast());
		UMS ums4 = new UMS("PO, qDay, PRN for swelling or weight gain, 0 Refill(s)");
		assertTrue(ums4.isBreakfast());
		UMS ums5 = new UMS("2 mg = 1 tab(s), PO, qDay, # 90 tab(s), X 90 day(s), 3 Refill(s), Stop 6/6/19");
		assertTrue(ums5.isBreakfast());
		UMS ums6 = new UMS("3.125 mg = 1 tab(s), PO, BID, # 180 tab(s), 3 Refill(s)");
		assertTrue(ums6.isBreakfast() && ums6.isDinner());
		UMS ums7 = new UMS("40 mg = 1 tab(s), PO, BID, # 30 tab(s), 11 Refill(s)");
		assertTrue(ums7.isBreakfast() && ums7.isDinner());
		UMS ums8 = new UMS("300 mg = 1 tab(s), PO, qDay, # 90 tab(s), with evening meal, 1 Refill(s)");
		assertTrue(ums8.isDinner());
		UMS ums9 = new UMS("50 mg = 1 tab(s), PO, q12hr, PRN for pain, # 60 tab(s), 5 Refill(s)");
		assertTrue(ums9.isBreakfast() && ums9.isDinner());
	}

}
