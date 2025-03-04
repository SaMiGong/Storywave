package com.ormi.storywave.users;

import com.ormi.storywave.admin.BanDto;
import com.ormi.storywave.admin.BanService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.format.DateTimeFormatter;

@org.springframework.stereotype.Controller
@RequestMapping("/user")
public class UserController {
  private final UserService userService;
  private final BanService banService;

  @Autowired
  public UserController(UserService userService, BanService banService) {
    this.userService = userService;
    this.banService = banService;
  }

  @GetMapping("/join")
  public String addUserForm(Model model) {
    return "join/join";
  }

  @PostMapping("/join")
  public String addUser(@ModelAttribute UserRequest.JoinDto joinDto) {
    UserDto addedUser = userService.addUser(joinDto);
    //        model.addAttribute("user", addedUser);
    return "join/welcome";
  }

  @GetMapping("/login")
  public String loginForm() {
    return "login/login";
  }

  @PostMapping("/login")
  public String login(
      @ModelAttribute UserRequest.LoginDto loginDto, HttpSession session, Model model) {

    UserDto loginResult = userService.loginUser(loginDto);

    if (loginResult == null) { // 로그인 실패
      model.addAttribute("error", "아이디 또는 비밀번호가 잘못 되었습니다. 아이디와 비밀번호를 정확히 입력해 주세요.");
      return "login/login";
    }
    BanDto banDto = banService.getBanByUserId(loginResult.getUserId());

    if (!loginResult.isActiveStatus() && banDto.getBanPeriod() >= 100) {
      // 영구 정지 회원의 경우
      model.addAttribute("ban", banDto);
      model.addAttribute("message", "영구 정지 회원입니다.");

      return "login/ban";
    } else if (!loginResult.isActiveStatus()) {
      // 비영구 정지 회원의 경우
      session.setAttribute("userId", loginResult.getUserId());
      session.setAttribute("activeStatus", loginResult.isActiveStatus());
      session.setAttribute("role", loginResult.getRole());

      model.addAttribute("message", "정지 회원입니다.");
      String endDate =
          banDto
              .getBanDate()
              .plusDays(banDto.getBanPeriod())
              .format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일"));

      model.addAttribute("ban", banDto);
      model.addAttribute("endDate", endDate);
      return "login/ban";
    } else { // 로그인 성공
      session.setAttribute("userId", loginResult.getUserId()); // 로그인 세션 유지
      session.setAttribute("role", loginResult.getRole());
      return "redirect:/home";
    }
  }

  @GetMapping("/logout")
  public String logout(HttpSession session) {
    if (session.getAttribute("userId") != null) {
      session.invalidate(); // 로그아웃 요청 시 로그인 세션 초기화
    }
    return "redirect:/home";
  }

  @PostMapping("/deleteAccount")
  public String deleteAccount(HttpSession session, Model model) {
    String userId = (String) session.getAttribute("userId");
    if (userId == null) {
      model.addAttribute("error", "세션에서 사용자 정보를 찾을 수 없습니다.");
      return "error";
    }
    boolean isDeleted = userService.deleteUser(userId); // 유저 삭제 서비스 호출
    if (isDeleted) {
      session.invalidate(); // 세션 무효화
      return "mypage/quit_finish"; // 탈퇴 완료 페이지로 리디렉션
    } else {
      model.addAttribute("error", "사용자 삭제에 실패했습니다.");
      return "error";
    }
  }

  @PostMapping("/user/delete")
  public String deleteUser(HttpSession session) {
    String userId = (String) session.getAttribute("userId");

    // 사용자와 관련된 모든 데이터 삭제
    userService.deleteUserAndContent(userId);

    // 로그아웃 및 세션 무효화
    session.invalidate();

    return "redirect:/home";
  }
}
