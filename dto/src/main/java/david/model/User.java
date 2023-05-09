package david.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    private long id;
    private String email;
    private String password;
    private UserType userType;

}
