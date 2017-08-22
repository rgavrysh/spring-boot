# PostgreSQL and ElasticSearh as two services for storage and searching respectively

## This is POC, how to combine two storage services together.

PostgreSQL DB used as a main storage. Entity service uses JPA repository for simple CRUD operations.
On the other hand, ElasticSearch is used for searching. Entity service has separate search repository which extends ElasticsearchRepository, hence can call/execute full text search and other complex selectors.

## Use browser to get results
Open localhost:8080/ in your browser, and start typing in the Employee input field. JQuery ajax sends request to the server which uses elasticsearch template to find results.

For studying purpose, data synchronization between Postgres and Elasticsearch performed by DataSynchronizer utility class, which simply grab all data from relational DB and index to Elasticsearch.
DataSynchronizer class is of prototype and Thread type and controlled by DataSyncController which is an MBean object.
By default, after start data are not synchronizing, to start synchronization you have to use one of the JMX console (ex. VisualVM), find 'dataSyncController' bean and use available operations to start, stop, set timeout interval and others...
This is one of the approach how to synchronize data (you can trigger synchronization after every insert/update operations).
Other approach is to use Logstash -> will be implemented later.
