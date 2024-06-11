package dev.veronb.mongock_demo.mongo.patches;

import dev.veronb.mongock_demo.entity.user.User;
import dev.veronb.mongock_demo.entity.user.support.Status;
import io.mongock.api.annotations.ChangeUnit;
import io.mongock.api.annotations.Execution;
import io.mongock.api.annotations.RollbackExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.Arrays;

@ChangeUnit(id = "202406051042-add-initial-users", order = "1", author = "Veron", systemVersion = "1")
public class AddInitialUsersChangeLog202406051042 {
        private final MongoTemplate mongoTemplate;
        private static final Logger logger = LoggerFactory.getLogger(AddInitialUsersChangeLog202406051042.class);

        public AddInitialUsersChangeLog202406051042(MongoTemplate mongoTemplate) {
            this.mongoTemplate = mongoTemplate;
        }

        // required method/annotation
        @Execution
        public void execute() {
            try {
                User sysAdmin = new User(1, "John", "Doe", "johndoe@gmail.com", Status.ACTIVE);
                mongoTemplate.insert(sysAdmin, "user");

                User dbAdmin = new User(2, "Jane", "Doe", "janedoe@gmail.com", Status.ACTIVE);
                mongoTemplate.insert(dbAdmin, "user");

                logger.info("Finished AddInitialUsersChangeLog202406051042 execution");
            } catch (Exception e) {
                logger.error(e.getMessage(), Arrays.toString(e.getStackTrace()));
            }
        }

        // required method/annotation
        @RollbackExecution
        public void executeRollback() { }
}
