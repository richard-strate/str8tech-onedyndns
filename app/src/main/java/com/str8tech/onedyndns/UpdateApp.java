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
public class UpdateApp extends AppBase {

  private static final Logger LOG = LoggerFactory.getLogger(UpdateApp.class);

  @Override
  public void execute(String[] args) throws Exception {
    loadRequest(args[0]);
    OneClientSessionImpl session = new OneClientSessionImpl();
    OneClientImpl client = session.login(getRequest().getUsername(), getRequest().getPassword());
    UpdateResponse updateResponse = new UpdateResponse();
    for (DyndnsUpdateRequest.Domain rqDomain : getRequest().getDomains()) {
      DnsRecords remoteDnsRecords = client.getDnsRecords(rqDomain.getName());
      for (DyndnsUpdateRequest.Record rqRecord : rqDomain.getRecords()) {
        DnsRecords.Data remoteDataRecord = remoteDnsRecords.findDataByPrefix(rqRecord.getPrefix());
        String processedContent = processContent(rqRecord.getContent());
        if (remoteDataRecord == null) {
          LOG.info("Adding new record '{}' to domain '{}", rqDomain.getName(), rqRecord.getPrefix());
          // Add as new
          client.addDnsRecord(rqDomain.getName(), rqRecord.getPriority(), rqRecord.getTtl(),
                  rqRecord.getType(), rqRecord.getPrefix(), processedContent);
          updateResponse.getAdded().add(new UpdateResponse.Record(rqRecord.getType(), rqRecord.getPrefix(), processedContent));
        } else if (remoteDataRecord.getAttributes().getContent().equals(processedContent)) {
          LOG.info("Ignoring already updated record '{}' in domain '{}'", rqDomain.getName(), rqRecord.getPrefix());
          updateResponse.getAlreadyUptodate().add(new UpdateResponse.Record(rqRecord.getType(), rqRecord.getPrefix(), processedContent));
        } else {
          LOG.info("Updating existing record '{}' in domain '{}", rqDomain.getName(), rqRecord.getPrefix());
          // Update existing
          client.updateDnsRecord(rqDomain.getName(), remoteDataRecord.getId(), rqRecord.getTtl(),
                  rqRecord.getType(), rqRecord.getPrefix(), processedContent);
          updateResponse.getUpdated().add(new UpdateResponse.Record(rqRecord.getType(), rqRecord.getPrefix(), processedContent));
        }
      }
    }
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    String json = objectMapper.writeValueAsString(updateResponse);
    System.out.println(json);
  }

}
