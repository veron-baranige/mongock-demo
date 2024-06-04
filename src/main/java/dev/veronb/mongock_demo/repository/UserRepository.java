package dev.veronb.mongock_demo.repository;

import dev.veronb.mongock_demo.entity.user.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {

}
