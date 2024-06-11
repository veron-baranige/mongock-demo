package dev.veronb.mongock_demo.templates;

import com.mongodb.client.result.DeleteResult;
import dev.veronb.mongock_demo.entity.user.support.Status;
import io.mongock.api.annotations.ChangeUnit;
import io.mongock.api.annotations.Execution;
import io.mongock.api.annotations.RollbackExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import static org.springframework.data.mongodb.core.query.Criteria.where;

@ChangeUnit(id = "202406051056-delete-status-deleted-users", order = "5", author = "Veron")
public class DeleteManyChangelog {
    private final MongoTemplate mongoTemplate;
    private static final Logger logger = LoggerFactory.getLogger(DeleteManyChangelog.class);

    public DeleteManyChangelog(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Execution
    public void execute() {
        Query query = new Query(where("status").is(Status.DELETED));
        DeleteResult deleteResult = mongoTemplate.remove(query, "user");
        logger.info("Finished DeleteManyChangelog execution. Deleted count: {}", deleteResult.getDeletedCount());
    }

    @RollbackExecution
    public void executeRollback() { }
}
