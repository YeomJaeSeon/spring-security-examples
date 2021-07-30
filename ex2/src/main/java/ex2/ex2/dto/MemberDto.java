package ex2.ex2.dto;

import ex2.ex2.domain.Member;
import ex2.ex2.domain.Role;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MemberDto {
    private Long id;
    private String email;
    private String password;
    private Role role;

    public Member toEntity(){
        Member member = new Member();
        member.setId(this.id);
        member.setEmail(this.email);
        member.setPassword(this.password);
        member.setRole(this.role);

        return member;
    }
}
