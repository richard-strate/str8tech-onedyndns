/*
 * Copyright (C) 2020 Straight Technologies
 * All rights reserved.
 * http://www.str8tech.com
 * 
 * This is a legal notice from Straight Technologies,
 * registered in Sweden.
 * 
 * This document is proprietary, confidential and is legally privileged.
 * Any un-authorised access to it is prohibited. Its disclosure, copying,
 * distribution or any action taken or omitted to be taken in reliance on
 * it is prohibited and may be unlawful.
 */
package com.str8tech.onedyndns;

import java.util.regex.Pattern;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Richard
 */
public class IpinfoIpResolverIT {

  public IpinfoIpResolverIT() {
  }

  @BeforeClass
  public static void setUpClass() {
  }

  @AfterClass
  public static void tearDownClass() {
  }

  @Before
  public void setUp() {
  }

  @After
  public void tearDown() {
  }

  /**
   * Test of resolve method, of class IpinfoIpResolver.
   *
   * @throws java.lang.Exception
   */
  @Test
  public void testResolve() throws Exception {
    String actual = new IpinfoIpResolver().resolve();
    assertNotNull(actual);
    Pattern patternSimpleIpv4 = Pattern.compile("^\\d+\\.\\d+\\.\\d+\\.\\d+$");
    assertTrue(String.format("Returned IP '%s' did not match simple IPv4 pattern", actual), patternSimpleIpv4.matcher(actual).matches());
  }

}
