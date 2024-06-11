package dev.veronb.mongock_demo.templates;

import com.mongodb.client.result.UpdateResult;
import dev.veronb.mongock_demo.entity.user.support.Status;
import io.mongock.api.annotations.ChangeUnit;
import io.mongock.api.annotations.Execution;
import io.mongock.api.annotations.RollbackExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import static org.springframework.data.mongodb.core.query.Criteria.where;

@ChangeUnit(id = "202406051010-update-user-status", order = "2", author = "Veron")
public class UpdateManyChangelog {
    private final MongoTemplate mongoTemplate;
    private static final Logger logger = LoggerFactory.getLogger(UpdateManyChangelog.class);

    public UpdateManyChangelog(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Execution
    public void execute() {
        Query query = new Query(where("status").ne(Status.ACTIVE));
        Update update = new Update().set("status", Status.DELETED);
        UpdateResult updateResult = mongoTemplate.updateMulti(query, update, "user");
        logger.info("Finished BulkUpdateChangelog execution. Matched count: {}, Modified count: {}",
                updateResult.getMatchedCount(), updateResult.getModifiedCount());
    }

    @RollbackExecution
    public void executeRollback() { }
}
