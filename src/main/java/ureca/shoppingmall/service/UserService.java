package ureca.shoppingmall.service;

import ureca.shoppingmall.domain.user.User;
import ureca.shoppingmall.dto.UserResultDto;

public interface UserService {
    UserResultDto login(String email, String password);
    UserResultDto insertUser(User user);
//    UserResultDto detailUser(Long id);
//    UserResultDto updateUser(User user);
}
