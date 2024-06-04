package dev.veronb.mongock_demo.entity.user;

import dev.veronb.mongock_demo.entity.user.support.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    private int id;
    private String firstName;
    private String lastName;
    private String emailAddress;
    private Status status;
}
