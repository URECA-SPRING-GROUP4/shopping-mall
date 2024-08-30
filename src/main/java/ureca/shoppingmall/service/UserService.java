package ureca.shoppingmall.service;

import ureca.shoppingmall.domain.user.Address;
import ureca.shoppingmall.domain.user.Phone;
import ureca.shoppingmall.domain.user.User;
import ureca.shoppingmall.dto.UserDto;
import ureca.shoppingmall.dto.UserResultDto;

public interface UserService {
    UserResultDto login(String email, String password);
    UserResultDto insertUser(User user);
    UserResultDto detailUser(Long id);

    UserResultDto updateUser(UserDto userDto);
    UserResultDto updateAddress(Address userAddress);
    UserResultDto updatePhone(Phone userPhone);

    UserResultDto insertAddress(Address userAddress, Long id);
    UserResultDto insertPhone(Phone userPhone, Long id);

    UserResultDto deleteAddress(Long id);
    UserResultDto deletePhone(Long id);

    UserResultDto deleteUser(Long userId);
}
