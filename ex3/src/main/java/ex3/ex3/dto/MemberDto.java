package ex3.ex3.dto;

import ex3.ex3.domain.Member;
import ex3.ex3.domain.Role;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MemberDto {
    private Long id;
    private String username;
    private String password;
    private Role role;

    public Member toEntity(){
        Member member = new Member();
        member.setId(this.id);
        member.setUsername(this.username);
        member.setPassword(this.password);
        member.setRole(this.role);

        return member;
    }
}
