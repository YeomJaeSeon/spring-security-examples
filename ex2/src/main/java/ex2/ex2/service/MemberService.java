package ex2.ex2.service;

import ex2.ex2.domain.Member;
import ex2.ex2.domain.Role;
import ex2.ex2.dto.MemberDto;
import ex2.ex2.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {
    private final MemberRepository memberRepository;


    @Transactional
    public Long joinUser(MemberDto memberDto){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        memberDto.setPassword(passwordEncoder.encode(memberDto.getPassword()));
        Member member = memberDto.toEntity();
        return memberRepository.save(member).getId();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Member> member = memberRepository.findByEmail(username);
        Member realMember = member.get();

        List<GrantedAuthority> authorities = new ArrayList<>();

        if(realMember.getRole() == Role.ADMIN){
            System.out.println("관리자네!!!!!!!!!!!!!!!!!!!!!!!!!");
            //admin
            authorities.add(new SimpleGrantedAuthority(Role.ADMIN.getValue()));
        }else{
            System.out.println("일반회원이네!!!!!!!!!!!!!!!!!!!!!!!!!!");
            //user
            authorities.add(new SimpleGrantedAuthority(Role.USER.getValue()));
        }
        System.out.println("authorities = " + authorities);

        return new User(realMember.getEmail(), realMember.getPassword(), authorities);
    }
}
