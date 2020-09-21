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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.str8tech.onedyndns.client.dns.DnsRecords;
import com.str8tech.onedyndns.client.jdk.OneClientImpl;
import com.str8tech.onedyndns.client.jdk.OneClientSessionImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Richard
 */
public class DownloadApp extends AppBase {

  private static final Logger LOG = LoggerFactory.getLogger(DownloadApp.class);

  @Override
  public void execute(String[] args) throws Exception {
    loadRequest(args[0]);
    String checkContent = args[1];
    LOG.info("Downloading all records with content matching '%s'", checkContent);
    OneClientSessionImpl session = new OneClientSessionImpl();
    OneClientImpl client = session.login(getRequest().getUsername(), getRequest().getPassword());
    DyndnsUpdateRequest output = new DyndnsUpdateRequest();
    for (DyndnsUpdateRequest.Domain domain : getRequest().getDomains()) {
      DnsRecords remoteDnsRecords = client.getDnsRecords(domain.getName());
      DyndnsUpdateRequest.Domain outputDomain = new DyndnsUpdateRequest.Domain();
      outputDomain.setName(domain.getName());
      for (DnsRecords.Data remoteData : remoteDnsRecords.getResult().getDataList()) {
        if (remoteData.getAttributes().getContent().equals(checkContent)) {
          DyndnsUpdateRequest.Record outputRecord = new DyndnsUpdateRequest.Record();
          outputRecord.setContent(checkContent);
          outputRecord.setPrefix(remoteData.getAttributes().getPrefix());
          outputRecord.setPriority(remoteData.getAttributes().getPriority());
          outputRecord.setTtl(remoteData.getAttributes().getTtl());
          outputRecord.setType(remoteData.getAttributes().getType());
          outputDomain.getRecords().add(outputRecord);
        }
      }
      if (!outputDomain.getRecords().isEmpty()) {
        output.getDomains().add(outputDomain);
      }
    }
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    String json = objectMapper.writeValueAsString(output);
    System.out.println(json);
  }

}
