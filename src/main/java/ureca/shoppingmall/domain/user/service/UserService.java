package ureca.shoppingmall.domain.user.service;

import ureca.shoppingmall.domain.user.Address;
import ureca.shoppingmall.domain.user.Phone;
import ureca.shoppingmall.domain.user.User;
import ureca.shoppingmall.domain.user.dto.AddressDto;
import ureca.shoppingmall.domain.user.dto.PhoneDto;
import ureca.shoppingmall.domain.user.dto.UserDto;
import ureca.shoppingmall.domain.user.dto.UserResultDto;

public interface UserService {
    UserResultDto login(String email, String password);
    UserResultDto insertUser(User user, Address address ,Phone phone);
    UserResultDto detailUser(Long id);
    UserResultDto listUser();

    UserResultDto updateUser(UserDto userDto);
    UserResultDto updateAddress(AddressDto userAddressDto);
    UserResultDto updatePhone(PhoneDto userPhoneDto);

    UserResultDto insertAddress(Address userAddress, Long id);
    UserResultDto insertPhone(Phone userPhone, Long id);

    UserResultDto deleteAddress(Long id);
    UserResultDto deletePhone(Long id);

    UserResultDto deleteUser(Long userId);
}
