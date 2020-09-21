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

/**
 *
 * @author Richard
 */
public class UpdateDnsRecordRequest {

  private String type;
  private String id;
  private Attributes attributes;

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Attributes getAttributes() {
    return attributes;
  }

  public void setAttributes(Attributes attributes) {
    this.attributes = attributes;
  }

  public static class Attributes {

    private RecordType type;
    private String prefix;
    private String content;
    private int ttl;

    public RecordType getType() {
      return type;
    }

    public void setType(RecordType type) {
      this.type = type;
    }

    public String getPrefix() {
      return prefix;
    }

    public void setPrefix(String prefix) {
      this.prefix = prefix;
    }

    public String getContent() {
      return content;
    }

    public void setContent(String content) {
      this.content = content;
    }

    public int getTtl() {
      return ttl;
    }

    public void setTtl(int ttl) {
      this.ttl = ttl;
    }

  }
}
