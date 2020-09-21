# Applications

TBD

## Download

Run with IP address as argument and it will check any existing DNS records on one.com to find matching entries. All entries are then rewritten as if they were part of a configuration file and printed to standard out. This is an easy to start up from scratch when you have a set of existing manually entered records. Copy+paste the output into the actual configuration to use for dyn updates, but remember to remove 'content' attribute since it contains the current IP address.

## Update

This is the dyndns-alike application which will run thru all the locally configured records and update them on one.com DNS server, while using the currently resolved external IP. The application will not update entries which are considered up-to-date. A report is printed afterwards, showing what entrie were added, updated or skipped/uptodate.
