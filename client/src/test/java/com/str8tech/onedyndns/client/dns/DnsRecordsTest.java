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
package com.str8tech.onedyndns.client.dns;

import com.str8tech.onedyndns.client.dns.DnsRecords.RecordType;
import java.io.IOException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Richard
 */
public class DnsRecordsTest {

  public DnsRecordsTest() {
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

  @Test
  public void testParse() throws Exception {
    DnsRecords actual = DnsRecords.parse(getClass().getResourceAsStream(getClass().getSimpleName() + "_testParse.json"));
    assertNotNull(actual);
    assertEquals(3, actual.getResult().getDataList().size());
    DnsRecords.Data actualFirstData = actual.getResult().getDataList().get(0);
    assertEquals("1", actualFirstData.getId());
    assertEquals("dns_custom_records", actualFirstData.getType());
    DnsRecords.Attributes actualFirstAttributes = actualFirstData.getAttributes();
    assertEquals(RecordType.TXT, actualFirstAttributes.getType());
    assertEquals("v=spf1 include:_custspf.one.com -all", actualFirstAttributes.getContent());
    assertEquals(0, actualFirstAttributes.getPriority());
    assertEquals(3600, actualFirstAttributes.getTtl());
    assertEquals("", actualFirstAttributes.getPrefix());
  }

  /**
   * Test of getResult method, of class DnsRecords.
   */
  @Test
  public void testGetResult() {
  }

  /**
   * Test of setResult method, of class DnsRecords.
   */
  @Test
  public void testSetResult() {
  }

  
  @Test
  public void testAddDnsRecordResponse() throws IOException {
    AddDnsRecordResponse.parse(getClass().getResourceAsStream("AddDnsRecordResponse.json"));
  }
}
