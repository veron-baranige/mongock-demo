package dev.veronb.mongock_demo.templates;

import com.mongodb.client.result.UpdateResult;
import io.mongock.api.annotations.ChangeUnit;
import io.mongock.api.annotations.Execution;
import io.mongock.api.annotations.RollbackExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import static org.springframework.data.mongodb.core.query.Criteria.where;

@ChangeUnit(id = "202406051035-update-user", order = "3", author = "Veron")
public class UpdateOneChangelog {
    private final MongoTemplate mongoTemplate;
    private static final Logger logger = LoggerFactory.getLogger(UpdateOneChangelog.class);

    public UpdateOneChangelog(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Execution
    public void execute() {
        Query query = new Query(where("id").is(1));
        Update update = new Update().set("firstName", "Veron");
        UpdateResult updateResult = mongoTemplate.updateFirst(query, update, "user");
        logger.info("Finished UpdateOneChangelog execution. Matched count: {}, Modified count: {}",
                updateResult.getMatchedCount(), updateResult.getModifiedCount());
    }

    @RollbackExecution
    public void executeRollback() { }
}
