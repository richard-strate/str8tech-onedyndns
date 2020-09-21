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
package com.str8tech.onedyndns.client.jdk;

import com.str8tech.onedyndns.client.OneClient;
import com.str8tech.onedyndns.client.dns.AddDnsRecordResponse;
import com.str8tech.onedyndns.client.dns.DnsRecords;
import com.str8tech.onedyndns.client.dns.UpdateDnsRecordResponse;
import java.util.UUID;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertNotNull;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Richard
 */
public class OneClientSessionImplIT {

  public OneClientSessionImplIT() {
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
   * Test of login method, of class OneClientSessionApacheImpl.
   */
  @Test
  public void testLogin() throws Exception {
    OneClientSessionImpl oneClientSessionApacheImpl = new OneClientSessionImpl();
    final String domain = System.getProperty("it.one.domain");
    final String userName = System.getProperty("it.one.userName");
    final String password = System.getProperty("it.one.password");
    try ( OneClient client = oneClientSessionApacheImpl.login(userName, password)) {
      DnsRecords dnsRecords = client.getDnsRecords(domain);
      assertNotNull(dnsRecords);
      String testDomain = String.format("one-client-%s", UUID.randomUUID().toString());
      // TODO: What if record name already exists?
      // TODO: What is the IP address (or other values) are incorrect?
      AddDnsRecordResponse addDnsRecord = client.addDnsRecord(domain, 0, 3600, DnsRecords.RecordType.A, testDomain, "1.2.3.4");
      // TODO: What is record doesn't exist?
      // TODO: What is the IP address (or other values) are incorrect?
      UpdateDnsRecordResponse updateDnsRecord = client.updateDnsRecord(domain, addDnsRecord.getResult().getData().getId(), 3600, DnsRecords.RecordType.A, testDomain, "5.6.7.8");
      // TODO: What is record doesn't exist?
      client.deleteDnsRecord(domain, updateDnsRecord.getResult().getData().getId());
    }
  }

}
