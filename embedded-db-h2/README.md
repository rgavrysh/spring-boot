# Spring-Boot Embadded DB - H2

This project contains simple configuration of embadded H2 database, *.sql file wich is executed on starting.
Custom Tomcat servlet configuration is used (like port:8081).
Added actuator with disabled security on sensible endpoints but on different port (8082). Most interesting endpoints to look at (/metrics, /mappings/, /dump).
