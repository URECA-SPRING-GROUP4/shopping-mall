package ureca.shoppingmall.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ureca.shoppingmall.domain.user.Address;
import ureca.shoppingmall.domain.user.Phone;
import ureca.shoppingmall.domain.user.Role;
import ureca.shoppingmall.domain.user.User;
import ureca.shoppingmall.dto.AddressDto;
import ureca.shoppingmall.dto.PhoneDto;
import ureca.shoppingmall.dto.UserDto;
import ureca.shoppingmall.dto.UserResultDto;
//import ureca.shoppingmall.repository.AddressRepository;
import ureca.shoppingmall.repository.AddressRepository;
import ureca.shoppingmall.repository.PhoneRepository;
import ureca.shoppingmall.repository.RoleRepository;
import ureca.shoppingmall.repository.UserRepository;
import ureca.shoppingmall.service.UserService;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final AddressRepository addressRepository;
    private final PhoneRepository phoneRepository;

    @Override
    public UserResultDto login(String email, String password) {
        UserResultDto userResultDto = new UserResultDto();

        User user = userRepository.findByEmail(email);

        if (user != null && user.getPassword().equals(password)) {
            UserDto userDto = new UserDto();
            userDto.setId(user.getId());
            userDto.setName(user.getName());
            userDto.setEmail(user.getEmail());

            Set<Role> roles = roleRepository.findByUserId(user.getId());

            Map<Long, String> userRoles = userDto.getRoles();
            roles.forEach(role -> userRoles.put(role.getId(), role.getStatus().name()));

            userResultDto.setUserDto(userDto);
            userResultDto.setResult("success");
        } else {
            userResultDto.setResult("fail");
        }

        return userResultDto;
    }

    @Override
    public UserResultDto insertUser(User user) {
        UserResultDto userResultDto = new UserResultDto();

        try {
            userRepository.save(user);
            userResultDto.setResult("success");
        }catch (Exception e){
            e.printStackTrace();
            userResultDto.setResult("fail");
        }

        return userResultDto;
    }

    @Override
    public UserResultDto detailUser(Long id) {
        UserResultDto userResultDto = new UserResultDto();

        // 사용자 정보를 가져옴
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            // 회원 정보
            UserDto userDto = new UserDto();
            userDto.setId(user.getId());
            userDto.setName(user.getName());
            userDto.setEmail(user.getEmail());
            userDto.setPassword(user.getPassword());

            // 주소
            List<Address> addressList = addressRepository.findByUserId(id);
            List<AddressDto> addresses = userDto.getAddresses(); // userDto에서 리스트를 가져옴

            addressList.forEach(userAddress -> {
                AddressDto userAddressDto = new AddressDto();
                userAddressDto.setId(userAddress.getId());
                userAddressDto.setCity(userAddress.getCity());
                userAddressDto.setStreet(userAddress.getStreet());
                userAddressDto.setZipcode(userAddress.getZipcode());

                addresses.add(userAddressDto);
            });

            // 휴대폰 번호
            List<Phone> phonesList = phoneRepository.findByUserId(id);
            List<PhoneDto> phones = userDto.getPhones();

            phonesList.forEach(userPhones -> {
                PhoneDto phoneDto = new PhoneDto();
                phoneDto.setId(userPhones.getId());
                phoneDto.setPhoneNumber(userPhones.getPhoneNumber());

                phones.add(phoneDto);
            });

            userResultDto.setUserDto(userDto);
            userResultDto.setResult("success");
        } else {
            userResultDto.setResult("fail");
        }

        return userResultDto;
    }

    @Override
    public UserResultDto insertAddress(Address address, Long id) {
        UserResultDto userResultDto = new UserResultDto();

        address.setId(id);
        addressRepository.save(address);
        userResultDto.setResult("success");
        return userResultDto;
    }

    @Override
    public UserResultDto insertPhone(Phone userPhone, Long id) {
        UserResultDto userResultDto = new UserResultDto();

        userPhone.setId(id);
        phoneRepository.save(userPhone);
        userResultDto.setResult("success");
        return userResultDto;
    }

    @Override
    public UserResultDto deleteAddress(Long id) {
        UserResultDto userResultDto = new UserResultDto();

        try {
            // ID로 주소를 찾고 삭제
            Optional<Address> addressOptional = addressRepository.findById(id);
            if (addressOptional.isPresent()) {
                addressRepository.deleteById(id);
                userResultDto.setResult("success");
            } else {
                userResultDto.setResult("fail"); // 주소가 존재하지 않으면 실패 처리
            }
        } catch (Exception e) {
            e.printStackTrace();
            userResultDto.setResult("fail"); // 예외 발생 시 실패 처리
        }

        return userResultDto;
    }

    @Override
    public UserResultDto deletePhone(Long id) {
        UserResultDto userResultDto = new UserResultDto();

        try {
            // ID로 휴대폰 번호를 찾고 삭제
            Optional<Phone> phoneOptional = phoneRepository.findById(id);
            if (phoneOptional.isPresent()) {
                phoneRepository.deleteById(id);
                userResultDto.setResult("success");
            } else {
                userResultDto.setResult("fail"); // 휴대폰 번호가 존재하지 않으면 실패 처리
            }
        } catch (Exception e) {
            e.printStackTrace();
            userResultDto.setResult("fail"); // 예외 발생 시 실패 처리
        }

        return userResultDto;
    }

    @Override
    @Transactional
    public UserResultDto updateUser(UserDto userDto) {
        UserResultDto userResultDto = new UserResultDto();

        try {
            // Fetch existing user
            Optional<User> optionalUser = userRepository.findById(userDto.getId());
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();

                user.setName(userDto.getName());
                user.setEmail(userDto.getEmail());

                userResultDto.setResult("success");
            } else {
                userResultDto.setResult("fail");
            }
        } catch (Exception e) {
            e.printStackTrace();
            userResultDto.setResult("fail");
        }

        return userResultDto;
    }

}
