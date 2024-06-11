package dev.veronb.mongock_demo.mongo.patches;

import dev.veronb.mongock_demo.entity.user.support.Status;
import io.mongock.api.annotations.ChangeUnit;
import io.mongock.api.annotations.Execution;
import io.mongock.api.annotations.RollbackExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.Arrays;

import static org.springframework.data.mongodb.core.query.Criteria.where;

@ChangeUnit(id = "202406051100-update-gmail-user-status", order = "2", author = "Veron", systemVersion = "2")
public class UpdateGmailUserStatus202406051100Changelog {
    private final MongoTemplate mongoTemplate;
    private static final Logger logger = LoggerFactory.getLogger(UpdateGmailUserStatus202406051100Changelog.class);

    public UpdateGmailUserStatus202406051100Changelog(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    // required method/annotation
    @Execution
    public void execute() {
        Query query = new Query(Criteria.where("emailAddress").regex(".*@gmail.com.*", "i"));
        Update update = new Update().set("status", Status.DELETED);

        try {
            mongoTemplate.updateMulti(query, update, "user");
        } catch (Exception e) {
            logger.error(e.getMessage(), Arrays.toString(e.getStackTrace()));
        }
    }

    // required method/annotation
    @RollbackExecution
    public void rollback() {
    }
}
