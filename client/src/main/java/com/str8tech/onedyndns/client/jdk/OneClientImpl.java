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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.str8tech.onedyndns.client.OneClient;
import com.str8tech.onedyndns.client.dns.AddDnsRecordRequest;
import com.str8tech.onedyndns.client.dns.AddDnsRecordResponse;
import com.str8tech.onedyndns.client.dns.DeleteDnsRecordResponse;
import com.str8tech.onedyndns.client.dns.DnsRecords;
import com.str8tech.onedyndns.client.dns.DnsRecords.RecordType;
import com.str8tech.onedyndns.client.dns.UpdateDnsRecordRequest;
import com.str8tech.onedyndns.client.dns.UpdateDnsRecordResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import static java.nio.charset.StandardCharsets.UTF_8;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Richard
 */
public class OneClientImpl implements OneClient {

  private static final Logger LOG = LoggerFactory.getLogger(OneClientImpl.class);
  private final OneClientSessionImpl session;

  public OneClientImpl(OneClientSessionImpl session) {
    this.session = session;
  }

  @Override
  public DeleteDnsRecordResponse deleteDnsRecord(String domain, String id) throws IOException {
    HttpRequest request
            = HttpRequest
                    .newBuilder()
                    .uri(URI.create(String.format("https://www.one.com/admin/api/domains/%s/dns/custom_records/%s", domain, id)))
                    .DELETE()
                    .build();
    HttpResponse<InputStream> response;
    try {
      response = session.getHttpClient().send(request, HttpResponse.BodyHandlers.ofInputStream());
      if (response.statusCode() != 200) {
        throw new IOException(String.format("Non-success response to delete of DNS record '%s' for domain '%s': %d", id, domain, response.statusCode()));
      }
    } catch (InterruptedException ex) {
      throw new IOException("Request for delete of DNS record was interrupted", ex);
    }
    return DeleteDnsRecordResponse.parse(response.body());
  }

  @Override
  public AddDnsRecordResponse addDnsRecord(String domain, int priority, int ttl, DnsRecords.RecordType type, String prefix, String content) throws IOException {
    AddDnsRecordRequest addDnsRecordRequest = new AddDnsRecordRequest();
    addDnsRecordRequest.setType("dns_custom_records");
    addDnsRecordRequest.setAttributes(new AddDnsRecordRequest.Attributes());
    addDnsRecordRequest.getAttributes().setPriority(priority);
    addDnsRecordRequest.getAttributes().setTtl(ttl);
    addDnsRecordRequest.getAttributes().setType(type);
    addDnsRecordRequest.getAttributes().setPrefix(prefix);
    addDnsRecordRequest.getAttributes().setContent(content);
    ObjectMapper objectMapper = new ObjectMapper();
    String json;
    try {
      json = objectMapper.writeValueAsString(addDnsRecordRequest);
    } catch (JsonProcessingException ex) {
      throw new IOException("Failed to serialize request to JSON", ex);
    }
    LOG.trace("addDnsRecordRequest={}", json);
    HttpRequest request
            = HttpRequest
                    .newBuilder()
                    .uri(URI.create(String.format("https://www.one.com/admin/api/domains/%s/dns/custom_records", domain)))
                    .header("Content-Type", "application/json; charset=UTF-8")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();
    HttpResponse<InputStream> response;
    try {
      response = session.getHttpClient().send(request, HttpResponse.BodyHandlers.ofInputStream());
      if (response.statusCode() != 200) {
        throw new IOException(String.format("Non-success response to adding DNS record '%s' to domain '%s': %d", prefix, domain, response.statusCode()));
      }
    } catch (InterruptedException ex) {
      throw new IOException(String.format("Request to add DNS record '%s' to domain '%s' was interrupted", prefix, domain), ex);
    }
    String responseJson = new String(response.body().readAllBytes(), UTF_8);
    LOG.trace("addDnsRecordResponse={}", responseJson);
    AddDnsRecordResponse ret = AddDnsRecordResponse.parse(new ByteArrayInputStream(responseJson.getBytes(UTF_8)));
    return ret;
  }

  @Override
  public DnsRecords getDnsRecords(String domain) throws IOException {
    // Set the accept header to app/jsn?
    HttpRequest request
            = HttpRequest
                    .newBuilder()
                    .uri(URI.create(String.format("https://www.one.com/admin/api/domains/%s/dns/custom_records", domain)))
                    .GET()
                    .build();
    HttpResponse<InputStream> response;
    try {
      response = session.getHttpClient().send(request, HttpResponse.BodyHandlers.ofInputStream());
      if (response.statusCode() != 200) {
        throw new IOException(String.format("Non-success response to listing of DNS records for domain '%s': %d", domain, response.statusCode()));
      }
    } catch (InterruptedException ex) {
      throw new IOException("Request for listing of DNS records was interrupted", ex);
    }
    DnsRecords ret = DnsRecords.parse(response.body());
    LOG.trace("Found {} data records", ret.getResult().getDataList().size());
    return ret;
  }

  @Override
  public UpdateDnsRecordResponse updateDnsRecord(String domain, String id, int ttl, RecordType type, String prefix, String content) throws IOException {
    LOG.trace("Update DNS record '{}' for domain '{}'", id, domain);
    UpdateDnsRecordRequest updateDnsRecordRequest = new UpdateDnsRecordRequest();
    updateDnsRecordRequest.setType("dns_service_records");
    updateDnsRecordRequest.setAttributes(new UpdateDnsRecordRequest.Attributes());
    updateDnsRecordRequest.getAttributes().setTtl(ttl);
    updateDnsRecordRequest.getAttributes().setType(type);
    updateDnsRecordRequest.getAttributes().setPrefix(prefix);
    updateDnsRecordRequest.getAttributes().setContent(content);
    ObjectMapper objectMapper = new ObjectMapper();
    String json;
    try {
      json = objectMapper.writeValueAsString(updateDnsRecordRequest);
    } catch (JsonProcessingException ex) {
      throw new IOException("Failed to serialize request to JSON", ex);
    }
    LOG.trace("updateDnsRecordRequest={}", json);
    HttpRequest request
            = HttpRequest
                    .newBuilder()
                    .uri(URI.create(String.format("https://www.one.com/admin/api/domains/%s/dns/custom_records/%s", domain, id)))
                    .header("Content-Type", "application/json; charset=UTF-8")
                    .method("PATCH", HttpRequest.BodyPublishers.ofString(json))
                    .build();
    HttpResponse<InputStream> response;
    try {
      response = session.getHttpClient().send(request, HttpResponse.BodyHandlers.ofInputStream());
      if (response.statusCode() != 200) {
        throw new IOException(String.format("Non-success response to update of DNS record '%s' for domain '%s': %d", id, domain, response.statusCode()));
      }
    } catch (InterruptedException ex) {
      throw new IOException(String.format("Request for update of DNS record '%s' was interrupted", id), ex);
    }
    String responseJson = new String(response.body().readAllBytes(), UTF_8);
    LOG.trace("updateDnsRecordResponse={}", responseJson);
    UpdateDnsRecordResponse ret = UpdateDnsRecordResponse.parse(new ByteArrayInputStream(responseJson.getBytes(UTF_8)));
    return ret;

  }

  @Override
  public void close() throws IOException {
    session.logout();
  }

}
