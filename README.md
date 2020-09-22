# str8tech-onedyndns

REST API client for the [one.com](https://www.one.com) DNS managment. Also comes with a console application to make use of a 'dyndns' approach, eg run in a script on computer startup to update DNS records with a potentially new IP address of the host.

# Modules

Module|Description
-|-
[Client](https://github.com/richard-strate/str8tech-onedyndns/tree/master/client)|Client library used to manage one.com DNS (via REST API)
[App](https://github.com/richard-strate/str8tech-onedyndns/tree/master/app)|Applications based on clients

# Maven

````xml
<dependency>
  <groupId>com.str8tech.onedyndns</groupId>
  <artifactId>str8tech-onedyndns</artifactId>
  <version>4</version>
</dependency>
````

Available via this maven repository:

````xml
<repository>
  <id>str8tech.repo</id>
  <name>Str8Tech Maven2 Repository</name>
  <url>https://www.str8tech.com/m2</url>
  <releases>
    <enabled>true</enabled>
  </releases>
</repository>
````
