package springSecurity.myPage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import springSecurity.myPage.domain.Member;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String userEmail);
}
