# Applications

TBD

## Configuration

The configuration file is JSON-formatted and read into [this class](https://github.com/richard-strate/str8tech-onedyndns/blob/master/app/src/main/java/com/str8tech/onedyndns/DyndnsUpdateRequest.java). The table below explains the structure:

Path|Scope|Usage|
-|-|-
`/username`|Required|One.com account user name
`/password`|Required|One.com account user name
`/ipResolver`|Optional, defauls to `com.str8tech.onedyndns.IpinfoIpResolver`|Resolver used to discover external IP address
`/domains[]`|Optional|List of domains managed by the account
`/domains[]/name`|Required|Name of the domain, eg `google.se`
`/domains[]/records[]`|Optional|DNS records inside the domain, eg `www`, `ftp`
`/domains[]/records[]/type`|Required|Record type, eg `A`, `TXT`, see [enum](https://github.com/richard-strate/str8tech-onedyndns/blob/master/client/src/main/java/com/str8tech/onedyndns/client/dns/DnsRecords.java)
`/domains[]/records[]/prefix`|Required|Record priority
`/domains[]/records[]/priority`|Optional, defaults to `0`|Record priority
`/domains[]/records[]/ttl`|Optional, defaults to `3600`|Time-to-live
`/domains[]/records[]/content`|Optional, defaults to `${external-ip}`|Content of the record, eg the IP address. Supports the token `${external-ip}` which is replaced with the externally resolved IPv4 address

## Download

`java -jar onedyndns-app-capsule.jar download %config%`

Argument|Meaning
-|-
`%config%`|Path to configuration file

Run with IP address as argument and it will check any existing DNS records on one.com to find matching entries. All entries are then rewritten as if they were part of a configuration file and printed to standard out. This is an easy to start up from scratch when you have a set of existing manually entered records. Copy+paste the output into the actual configuration to use for dyn updates, but remember to remove 'content' attribute since it contains the current IP address.

## Update

This is the dyndns-alike application which will run thru all the locally configured records and update them on one.com DNS server, while using the currently resolved external IP. The application will not update entries which are considered up-to-date. A report is printed afterwards, showing what entrie were added, updated or skipped/uptodate.
