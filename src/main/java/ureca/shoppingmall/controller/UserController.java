package ureca.shoppingmall.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ureca.shoppingmall.domain.user.Address;
import ureca.shoppingmall.domain.user.Phone;
import ureca.shoppingmall.domain.user.User;
import ureca.shoppingmall.dto.AddressDto;
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

        if (userResultDto.getResult().equals("success")) {
            session.setAttribute("userDto", userResultDto.getUserDto());
        }

        return userResultDto;
    }

    // 회원가입
    @PostMapping("/register")
    public UserResultDto insertUser(User user, Address address ,Phone phone) {

        return userService.insertUser(user, address, phone);
    }

    // 회원 정보 조회하기
    @GetMapping("/detail")
    public UserResultDto detailUser(HttpSession session) {
        Long id = ((UserDto) session.getAttribute("userDto")).getId();
        return userService.detailUser(id);
    }

    // 유저 리스트 조회하기
    @GetMapping("/list")
    public UserResultDto listUser() {
        return userService.listUser();
    }

    // 유저 업데이트
    @PostMapping("/updateUser")
    public UserResultDto updateUser(UserDto userDto, HttpSession session) {
        Long id = ((UserDto) session.getAttribute("userDto")).getId();
        userDto.setId(id);
        return userService.updateUser(userDto);
    }

    // 주소 업데이트
    @PostMapping("/updateAddress")
    public UserResultDto updateAddress(Address address, HttpSession session) {
        Long id = ((UserDto) session.getAttribute("userDto")).getId();
        address.setId(id);
        return userService.updateAddress(address);
    }

    // 폰 업데이트
    @PostMapping("/updatePhone")
    public UserResultDto updatePhone(Phone phone, HttpSession session) {
        Long id = ((UserDto) session.getAttribute("userDto")).getId();
        phone.setId(id);
        return userService.updatePhone(phone);
    }

    // 주소 추가
    @PostMapping("/addAddress")
    public UserResultDto insertUserAddress(Address address, HttpSession session) {
        Long id = ((UserDto) session.getAttribute("userDto")).getId();
        return userService.insertAddress(address, id);
    }

    // 폰 추가
    @PostMapping("/addPhone")
    public UserResultDto insertPhone(Phone phone, HttpSession session) {
        Long id = ((UserDto) session.getAttribute("userDto")).getId();
        return userService.insertPhone(phone, id);
    }

    // 주소 삭제
    @GetMapping("/deleteAddress/{addressId}")
    public UserResultDto deleteAddress(@PathVariable Long addressId, HttpSession session) {
        Long id = ((UserDto) session.getAttribute("userDto")).getId();
        return userService.deleteAddress(addressId);
    }

    // 휴대폰 번호 삭제
    @GetMapping("/deletePhone/{phoneId}")
    public UserResultDto deletePhone(@PathVariable Long phoneId, HttpSession session) {
        Long id = ((UserDto) session.getAttribute("userDto")).getId();
        return userService.deletePhone(phoneId);
    }

    // 유저 삭제
    @DeleteMapping("/{userId}")
    public UserResultDto deleteUser(@PathVariable Long userId, HttpSession session) {
        Long id = ((UserDto) session.getAttribute("userDto")).getId();
        return userService.deleteUser(userId);
    }
}