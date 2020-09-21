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
package com.str8tech.onedyndns.client;

import com.str8tech.onedyndns.client.dns.AddDnsRecordResponse;
import com.str8tech.onedyndns.client.dns.DeleteDnsRecordResponse;
import com.str8tech.onedyndns.client.dns.DnsRecords;
import com.str8tech.onedyndns.client.dns.DnsRecords.RecordType;
import com.str8tech.onedyndns.client.dns.UpdateDnsRecordResponse;
import java.io.Closeable;
import java.io.IOException;

/**
 * This interface defines the methods to be implemented by any client supporting
 * management of the one.com services. It is expected to be created via an
 * {@link OneClientFactory} instance, which must deliver a client able to
 * authenticate against the remote, when/if required.
 * <br>
 * <br>
 * The client must support potential auth session timeout, preferably with
 * automatic refreshing but at least with a proper IOException when a sesion has
 * timed out.
 * <br>
 * <br>
 * The client is {@code Clsoeable} and an implementation is expected to free up
 * all resources possible, including the remote auth session, when closed.
 *
 * @author Richard
 */
public interface OneClient extends Closeable {

  /**
   * Add a new DNS record.If the record already exists, use
   * {@link #removeRecord} to avoid an exception.
   *
   * @param domain Domain to add record to (eg 'google.se')
   * @param priority DNS priority, may be NULL and must then default to 0
   * @param ttl Time to live, may be NULL and must then default to 3600
   * @param type Type of record
   * @param prefix Domain record prefix
   * @param content Address of the record (eg IP address)
   * @return
   * @throws java.io.IOException
   */
  AddDnsRecordResponse addDnsRecord(String domain, int priority, int ttl, RecordType type, String prefix, String content) throws IOException;

  /**
   * Get all 'custom DNS records'
   *
   * @param domain
   * @return
   * @throws java.io.IOException
   */
  DnsRecords getDnsRecords(String domain) throws IOException;

  DeleteDnsRecordResponse deleteDnsRecord(String domain, String id) throws IOException;

  UpdateDnsRecordResponse updateDnsRecord(String domain, String id, int ttl, RecordType type, String prefix, String content) throws IOException;

}
