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

import com.str8tech.onedyndns.client.dns.DnsRecords.RecordType;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Richard
 */
public class UpdateResponse {

  private List<Record> added = new LinkedList<>();
  private List<Record> updated = new LinkedList<>();
  private List<Record> alreadyUptodate = new LinkedList<>();

  public List<Record> getAdded() {
    return added;
  }

  public void setAdded(List<Record> added) {
    this.added = added;
  }

  public List<Record> getUpdated() {
    return updated;
  }

  public void setUpdated(List<Record> updated) {
    this.updated = updated;
  }

  public List<Record> getAlreadyUptodate() {
    return alreadyUptodate;
  }

  public void setAlreadyUptodate(List<Record> alreadyUptodate) {
    this.alreadyUptodate = alreadyUptodate;
  }

  public static class Record {

    private RecordType type;
    private String prefix;
    private String content;

    public Record(RecordType type, String prefix, String content) {
      this.type = type;
      this.prefix = prefix;
      this.content = content;
    }

    public Record() {
    }

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

  }
}
