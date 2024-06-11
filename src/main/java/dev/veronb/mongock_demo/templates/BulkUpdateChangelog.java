package dev.veronb.mongock_demo.templates;

import com.mongodb.bulk.BulkWriteResult;
import com.mongodb.client.MongoCursor;
import io.mongock.api.annotations.ChangeUnit;
import io.mongock.api.annotations.Execution;
import io.mongock.api.annotations.RollbackExecution;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.BulkOperations;
import org.springframework.data.mongodb.core.BulkOperations.BulkMode;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import static org.springframework.data.mongodb.core.query.Criteria.where;

@ChangeUnit(id = "202406050735-bulk-update-user-emails-to-lowercase", order = "1", author = "Veron")
public class BulkUpdateChangelog {
    private final MongoTemplate mongoTemplate;
    private static final Logger logger = LoggerFactory.getLogger(BulkUpdateChangelog.class);

    public BulkUpdateChangelog(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Execution
    public void execute() {
        int batchSize = 500;
        int processedCount = 0;

        Query query = new Query();
        query.addCriteria(Criteria.where("emailAddress")
                .exists(true)
                .ne("")
                .regex("[A-Z]"));
        query.fields().include("emailAddress");

        MongoCursor<Document> cursor = mongoTemplate.getCollection("user")
                .find(query.getQueryObject())
                .batchSize(batchSize)
                .cursor();

        BulkOperations bulkOps = mongoTemplate.bulkOps(BulkMode.UNORDERED, "user");

        try {
            while (cursor.hasNext()) {
                Document userDocument = cursor.next();
                String parsedEmailAddress = userDocument.getString("emailAddress").toLowerCase();
                Update update = new Update().set("emailAddress", parsedEmailAddress);
                bulkOps.updateOne(new Query(where("_id").is(userDocument.getObjectId("_id"))), update);
                processedCount++;

                if (!cursor.hasNext() || processedCount % batchSize == 0) {
                    BulkWriteResult bulkWriteResult = bulkOps.execute();
                    logger.info("Processed user bulk update batch. Matched count: {}, Modified count: {}",
                            bulkWriteResult.getMatchedCount(), bulkWriteResult.getModifiedCount());
                    // create new bulkOps instance for the next batch to be updated
                    bulkOps = mongoTemplate.bulkOps(BulkMode.UNORDERED, "user");
                }
            }
            logger.info("Finished BulkUpdateChangelog execution");
        } finally {
            cursor.close();
        }
    }

    @RollbackExecution
    public void executeRollback() { }
}
