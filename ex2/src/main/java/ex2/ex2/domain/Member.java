package ex2.ex2.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class Member {
    @Id @GeneratedValue
    private Long id;

    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;
}
