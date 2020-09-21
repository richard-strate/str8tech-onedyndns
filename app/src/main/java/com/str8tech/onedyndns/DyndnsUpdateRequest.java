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

import com.str8tech.onedyndns.client.dns.DnsRecords;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Richard
 */
public class DyndnsUpdateRequest {

  private String username;
  private String password;
  private String ipResolver = IpinfoIpResolver.class.getName();
  private List<Domain> domains = new LinkedList<>();

  public String getIpResolver() {
    return ipResolver;
  }

  public void setIpResolver(String ipResolver) {
    this.ipResolver = ipResolver;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public List<Domain> getDomains() {
    return domains;
  }

  public void setDomains(List<Domain> domains) {
    this.domains = domains;
  }

  public static class Domain {

    private String name;
    private List<Record> records = new LinkedList<>();

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public List<Record> getRecords() {
      return records;
    }

    public void setRecords(List<Record> records) {
      this.records = records;
    }

  }

  public static class Record {

    private int priority = 0;
    private int ttl = 3600;
    private DnsRecords.RecordType type;
    private String prefix;
    private String content = "${external-ip}";

    public int getPriority() {
      return priority;
    }

    public void setPriority(int priority) {
      this.priority = priority;
    }

    public int getTtl() {
      return ttl;
    }

    public void setTtl(int ttl) {
      this.ttl = ttl;
    }

    public DnsRecords.RecordType getType() {
      return type;
    }

    public void setType(DnsRecords.RecordType type) {
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

  }

}
