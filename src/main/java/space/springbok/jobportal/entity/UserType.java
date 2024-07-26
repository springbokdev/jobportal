package space.springbok.jobportal.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "users_type")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserType {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int userTypeId;

    private String userTypeName;

    @OneToMany(targetEntity = User.class, mappedBy = "userTypeId", cascade = CascadeType.ALL)
    private List<User> users;

}
