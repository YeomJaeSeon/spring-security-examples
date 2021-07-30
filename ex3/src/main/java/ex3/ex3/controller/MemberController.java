package ex3.ex3.controller;

import ex3.ex3.domain.Role;
import ex3.ex3.dto.MemberDto;
import ex3.ex3.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/")
    public String dispHome(){
        return "index";
    }

    @GetMapping("/user/signup")
    public String dispSignup(){
        return "signup";
    }

    @PostMapping("/user/signup")
    public String signup(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam String role
    ){
        MemberDto memberDto = new MemberDto();
        memberDto.setUsername(username);
        memberDto.setPassword(password);
        for (Role roleV : Role.values()) {
            if(roleV.getValue().equals(role)) memberDto.setRole(roleV);
        }
        memberService.joinUser(memberDto);

        return "redirect:/user/login";
    }

    // 로그인 페이지
    @GetMapping("/user/login")
    public String dispLogin() {
        return "login";
    }

    // 로그인 결과 페이지
    @GetMapping("/user/login/result")
    public String dispLoginResult() {
        return "loginSuccess";
    }

    // 로그아웃 결과 페이지
    @GetMapping("/user/logout/result")
    public String dispLogout() {
        return "logout";
    }

    // 접근 거부 페이지
    @GetMapping("/user/denied")
    public String dispDenied() {
        return "denied";
    }

    // 내 정보 페이지
    @GetMapping("/user/info")
    public String dispMyInfo() {
        return "info";
    }

    // 어드민 페이지
    @GetMapping("/admin")
    public String dispAdmin() {
        return "admin";
    }
}
