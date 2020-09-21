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

import java.io.IOException;
import static java.nio.charset.StandardCharsets.UTF_8;
import java.util.Arrays;
import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Richard
 */
public class App {

  private static final Logger LOG = LoggerFactory.getLogger(App.class);

  public static void main(String[] args) throws Exception {
    if (args.length == 0) {
      System.out.println(new String(App.class.getResourceAsStream("AppUsage.txt").readAllBytes(), UTF_8));
      System.exit(1);
    }
    try {
      new App().main_(args);
    } catch (Exception ex) {
      ex.printStackTrace(System.err);
      System.exit(1);
    }
  }

  public void main_(String[] args) throws IOException, Exception {
    if (Boolean.getBoolean("ondyndns.debug")) {
      PropertyConfigurator.configure(getClass().getResourceAsStream("/log4j-debug.properties"));
      LOG.debug("Debug logging is enabled");
    }
    if (args.length == 0) {
      throw new IllegalArgumentException("No configuration file specified ");
    }
    String[] appArgs = Arrays.copyOfRange(args, 1, args.length);
    String operation = args[0];
    if (operation.equals("download")) {
      DownloadApp app = new DownloadApp();
      app.execute(appArgs);
    } else if (operation.equals("update")) {
      UpdateApp app = new UpdateApp();
      app.execute(appArgs);
    } else {
      throw new IllegalArgumentException(String.format("Operation '%s' not supported", operation));
    }

  }

}
