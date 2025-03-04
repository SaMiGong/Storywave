package com.ormi.storywave.board;

import com.ormi.storywave.users.UserDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.ormi.storywave.users.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Controller
public class BoardController {

  @Autowired private UserService userService; // UserService를 주입받아야 합니다.

  @Autowired private BoardService boardService;

  @GetMapping("/board/search")
  public ResponseEntity<List<PostListDto>> searchPosts(@RequestParam String keyword) {
    List<PostListDto> searchResults = boardService.searchPosts(keyword);
    return ResponseEntity.ok(searchResults);
  }

  @GetMapping("/check-login")
  @ResponseBody
  public ResponseEntity<Boolean> checkLogin(HttpSession session) {
    String userId = (String) session.getAttribute("userId");
    boolean isLoggedIn = userId != null;
    return ResponseEntity.ok(isLoggedIn);
  }

  @GetMapping("/board/Searchlist")
  public String showSearchList(Model model, HttpSession session) {
    // 세션에서 userId를 가져옵니다.
    String userId = (String) session.getAttribute("userId");

    if (userId != null) {
      // userId가 세션에 있는 경우, UserDto를 조회합니다.
      Optional<UserDto> user = userService.getUserById(userId);
      if (user.isPresent()) {
        model.addAttribute("isLoggedIn", true);
        model.addAttribute("userId", userId); // userId를 모델에 추가

      } else {
        // UserDto가 null인 경우, 로그인 상태가 아님
        model.addAttribute("isLoggedIn", false);
      }
    } else {
      // userId가 세션에 없는 경우, 로그인 상태가 아님
      model.addAttribute("isLoggedIn", false);
    }
    return "/board/Searchlist";
  }

  @GetMapping("/board/Noticepostlist")
  public String showNoticePostList(Model model, HttpSession session) {
    // 세션에서 userId를 가져옵니다.
    String userId = (String) session.getAttribute("userId");
    String role = (String) session.getAttribute("role");

    if (userId != null) {
      // userId가 세션에 있는 경우, UserDto를 조회합니다.
      Optional<UserDto> user = userService.getUserById(userId);
      if (user.isPresent()) {
        model.addAttribute("isLoggedIn", true);
        model.addAttribute("userId", userId); // userId를 모델에 추가
        model.addAttribute("role", role);

      } else {
        // UserDto가 null인 경우, 로그인 상태가 아님
        model.addAttribute("isLoggedIn", false);
      }
    } else {
      // userId가 세션에 없는 경우, 로그인 상태가 아님
      model.addAttribute("isLoggedIn", false);
    }
    return "/board/Noticepostlist";
  }

  @GetMapping("/board/Moviepostlist")
  public String showMoviePostList(Model model, HttpSession session) {
    // 세션에서 userId를 가져옵니다.
    String userId = (String) session.getAttribute("userId");

    if (userId != null) {
      // userId가 세션에 있는 경우, UserDto를 조회합니다.
      Optional<UserDto> user = userService.getUserById(userId);
      if (user.isPresent()) {
        model.addAttribute("isLoggedIn", true);
        model.addAttribute("userId", userId); // userId를 모델에 추가

      } else {
        // UserDto가 null인 경우, 로그인 상태가 아님
        model.addAttribute("isLoggedIn", false);
      }
    } else {
      // userId가 세션에 없는 경우, 로그인 상태가 아님
      model.addAttribute("isLoggedIn", false);
    }
    return "board/Moviepostlist";
  }

  @GetMapping("/board/Bookpostlist")
  public String showBookPostList(Model model, HttpSession session) {
    // 세션에서 userId를 가져옵니다.
    String userId = (String) session.getAttribute("userId");

    if (userId != null) {
      // userId가 세션에 있는 경우, UserDto를 조회합니다.
      Optional<UserDto> user = userService.getUserById(userId);
      if (user.isPresent()) {
        model.addAttribute("isLoggedIn", true);
        model.addAttribute("userId", userId); // userId를 모델에 추가

      } else {
        // UserDto가 null인 경우, 로그인 상태가 아님
        model.addAttribute("isLoggedIn", false);
      }
    } else {
      // userId가 세션에 없는 경우, 로그인 상태가 아님
      model.addAttribute("isLoggedIn", false);
    }
    return "/board/Bookpostlist";
  }

  @GetMapping("/board/Noticepostwrite")
  public String showNoticePostWrite(Model model, HttpSession session) {
    // 세션에서 userId를 가져옵니다.
    String userId = (String) session.getAttribute("userId");

    if (userId != null) {
      // userId가 세션에 있는 경우, UserDto를 조회합니다.
      Optional<UserDto> user = userService.getUserById(userId);
      if (user.isPresent()) {
        model.addAttribute("isLoggedIn", true);
        model.addAttribute("userId", userId); // userId를 모델에 추가

      } else {
        // UserDto가 null인 경우, 로그인 상태가 아님
        model.addAttribute("isLoggedIn", false);
      }
    } else {
      // userId가 세션에 없는 경우, 로그인 상태가 아님
      model.addAttribute("isLoggedIn", false);
    }
    return "/board/Noticepostwrite";
  }

  @GetMapping("/board/Moviepostwrite")
  public String showMoviePostWrite(Model model, HttpSession session) {
    // 세션에서 userId를 가져옵니다.
    String userId = (String) session.getAttribute("userId");

    if (userId != null) {
      // userId가 세션에 있는 경우, UserDto를 조회합니다.
      Optional<UserDto> user = userService.getUserById(userId);
      if (user.isPresent()) {
        model.addAttribute("isLoggedIn", true);
        model.addAttribute("userId", userId); // userId를 모델에 추가
      } else {
        // UserDto가 null인 경우, 로그인 상태가 아님
        model.addAttribute("isLoggedIn", false);
      }
    } else {
      // userId가 세션에 없는 경우, 로그인 상태가 아님
      model.addAttribute("isLoggedIn", false);
    }
    return "/board/Moviepostwrite";
  }

  @GetMapping("/board/Bookpostwrite")
  public String showBookPostWrite(Model model, HttpSession session) {
    // 세션에서 userId를 가져옵니다.
    String userId = (String) session.getAttribute("userId");

    if (userId != null) {
      // userId가 세션에 있는 경우, UserDto를 조회합니다.
      Optional<UserDto> user = userService.getUserById(userId);
      if (user.isPresent()) {
        model.addAttribute("isLoggedIn", true);
        model.addAttribute("userId", userId); // userId를 모델에 추가
      } else {
        // UserDto가 null인 경우, 로그인 상태가 아님
        model.addAttribute("isLoggedIn", false);
      }
    } else {
      // userId가 세션에 없는 경우, 로그인 상태가 아님
      model.addAttribute("isLoggedIn", false);
    }
    // 로직 처리
    return "/board/Bookpostwrite";
  }
}
