package dev.veronb.mongock_demo.templates;

import dev.veronb.mongock_demo.entity.user.User;
import dev.veronb.mongock_demo.entity.user.support.Status;
import io.mongock.api.annotations.ChangeUnit;
import io.mongock.api.annotations.Execution;
import io.mongock.api.annotations.RollbackExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;

@ChangeUnit(id = "202406051042-insert-user", order = "4", author = "Veron")
public class InsertOneChangelog {
    private final MongoTemplate mongoTemplate;
    private static final Logger logger = LoggerFactory.getLogger(UpdateOneChangelog.class);

    public InsertOneChangelog(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Execution
    public void execute() {
        User user = new User(1, "John", "Doe", "johndoe@gmail.com", Status.ACTIVE);
        mongoTemplate.insert(user, "user");
        logger.info("Finished InsertOneChangelog execution");
    }

    @RollbackExecution
    public void executeRollback() { }
}
