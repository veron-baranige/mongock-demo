# Mongock Integration

## Integrating Mongock with Spring Boot Applications
1. Install required dependencies
```xml
<dependency>
    <groupId>io.mongock</groupId>
    <artifactId>mongock-springboot-v3</artifactId>
    <version>5.4.2</version>
</dependency>

<dependency>
    <groupId>io.mongock</groupId>
    <artifactId>mongodb-springdata-v4-driver</artifactId>
    <version>5.4.2</version>
</dependency>
```
2. Add MongoTransactionManager configuration
```java
@Configuration
public class MongoConfiguration {

    @Bean
    public MongoTransactionManager transactionManager(MongoTemplate mongoTemplate) {
        TransactionOptions transactionalOptions = TransactionOptions.builder()
                .readConcern(ReadConcern.MAJORITY)
                .readPreference(ReadPreference.primary())
                .writeConcern(WriteConcern.MAJORITY.withJournal(true))
                .build();
        return new MongoTransactionManager(mongoTemplate.getMongoDatabaseFactory(), transactionalOptions);
    }
}
```
3. Add annotation for the Spring Boot application class to enable Mongock: `@EnableMongock`

4. Add application properties to configure Mongock
```
mongock.migration-scan-package=dev.veronb.mongock_demo.mongo.patches
mongock.enabled=true
mongock.start-system-version=1
mongock.end-system-version=1
mongock.transaction-strategy=execution
mongock.transaction-enabled=true
```
5. Add ChangeUnit classes(db patches) inside the defined package of `mongock.migration-scan-package`


## Working with local MongoDB instance
- For transactions to work properly with the transaction manger config, the connected MongoDB should be running as a replica set
- Change application property `mongock.transaction-enabled` to `false` to avoid exceptions in local environment

## Guidelines/Best Practices
- Long running patches should be run separately using node mongodb scripts to eliminate the server load
- Patches/ChangeUnits should be managed by the system/release version by changeing the `@ChangeUnit`'s system version as well as the `mongock.start-system-version` and `mongock.end-system-version` in _application.properties_