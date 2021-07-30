package ex3.ex3.service;

import ex3.ex3.domain.Member;
import ex3.ex3.domain.Role;
import ex3.ex3.dto.MemberDto;
import ex3.ex3.repository.MemberRepository;
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

@Service
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Transactional
    public Long joinUser(MemberDto memberDto){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        memberDto.setPassword(passwordEncoder.encode(memberDto.getPassword()));
        Member member = memberDto.toEntity();
        Long memberId = memberRepository.save(member);
        return memberId;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<Member> members = memberRepository.findByUsername(username);
        if(members.size() == 0){
            throw new IllegalStateException("회원 없음");
        }
        Member member = members.get(0);

        List<GrantedAuthority> authorities = new ArrayList<>();
        if(member.getRole() == Role.USER){
            //USER
            authorities.add(new SimpleGrantedAuthority(Role.USER.getValue()));
        }else{
            //ADMIN
            authorities.add(new SimpleGrantedAuthority(Role.ADMIN.getValue()));
        }

        return new User(member.getUsername(), member.getPassword(), authorities);
    }
}
