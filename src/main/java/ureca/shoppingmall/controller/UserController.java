package ureca.shoppingmall.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ureca.shoppingmall.domain.user.User;
import ureca.shoppingmall.dto.UserDto;
import ureca.shoppingmall.dto.UserResultDto;
import ureca.shoppingmall.service.UserService;

@Controller
@ResponseBody
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 로그인
    @PostMapping("/login")
    public UserResultDto login(
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            HttpSession session
    ) {

        UserResultDto userResultDto = userService.login(email, password);

        if( userResultDto.getResult().equals("success")) {
            session.setAttribute("userDto", userResultDto.getUserDto());
        }

        return userResultDto;
    }

    // 회원가입
    @PostMapping("/register")
    public UserResultDto insertUser(User user) {
        return userService.insertUser(user);
    }

    // 회원 정보 조회하기
//    @GetMapping("/detail")
//    public UserResultDto detailUser(HttpSession session) {
//        Long id =((UserDto)session.getAttribute("userDto")).getId();
//        return userService.detailUser(id);
//    }
}
