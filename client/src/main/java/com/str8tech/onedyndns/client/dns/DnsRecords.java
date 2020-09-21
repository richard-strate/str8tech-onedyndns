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

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Richard
 */
public class DnsRecords {

  public static DnsRecords parse(InputStream is) throws IOException {
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    return objectMapper.readValue(is, DnsRecords.class);
  }

  public enum RecordType {
    A,
    TXT
  }

  private Result result;

  public Result getResult() {
    return result;
  }

  public void setResult(Result result) {
    this.result = result;
  }

  public static class Result {

    @JsonProperty("data")
    private List<Data> dataList = new LinkedList<>();

    public List<Data> getDataList() {
      return dataList;
    }

    public void setDataList(List<Data> dataList) {
      this.dataList = dataList;
    }

  }

  public static class Data {

    private String id;
    private String type;
    private Attributes attributes;

    public String getId() {
      return id;
    }

    public void setId(String id) {
      this.id = id;
    }

    public String getType() {
      return type;
    }

    public void setType(String type) {
      this.type = type;
    }

    public Attributes getAttributes() {
      return attributes;
    }

    public void setAttributes(Attributes attributes) {
      this.attributes = attributes;
    }

  }

  public static class Attributes {

    private String prefix;
    private RecordType type;
    private String content;
    private int priority;
    private int ttl;

    public String getPrefix() {
      return prefix;
    }

    public void setPrefix(String prefix) {
      this.prefix = prefix;
    }

    public RecordType getType() {
      return type;
    }

    public void setType(RecordType type) {
      this.type = type;
    }

    public String getContent() {
      return content;
    }

    public void setContent(String content) {
      this.content = content;
    }

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

  }
}
