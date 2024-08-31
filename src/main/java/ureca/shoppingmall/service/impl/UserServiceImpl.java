package ureca.shoppingmall.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ureca.shoppingmall.common.enums.RoleStatus;
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

import java.util.*;
import java.util.stream.Collectors;

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

            String userRole = "USER"; // 기본값 설정

            if (!roles.isEmpty()) {
                userRole = roles.iterator().next().getStatus().name();
            }

            userResultDto.setUserDto(userDto);
            userResultDto.setResult("success");
            userResultDto.setRole(userRole);
        } else {
            userResultDto.setResult("fail");
        }

        return userResultDto;
    }


    @Override
    @Transactional
    public UserResultDto insertUser(User user, Address address, Phone phone) {
        UserResultDto userResultDto = new UserResultDto();

        try {
            User savedUser = userRepository.save(user);

            address.setUser(savedUser);
            phone.setUser(savedUser);

            addressRepository.save(address);
            phoneRepository.save(phone);

            Role userRole = new Role(savedUser, RoleStatus.USER);
            roleRepository.save(userRole);

            userResultDto.setResult("success");
        } catch (Exception e) {
            e.printStackTrace();
            userResultDto.setResult("fail");
        }

        return userResultDto;
    }

    @Override
    public UserResultDto detailUser(Long id) {
        UserResultDto userResultDto = new UserResultDto();

        Optional<User> optionalUser = userRepository.findByIdWithAddressesAndPhones(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            UserDto userDto = new UserDto();
            userDto.setId(user.getId());
            userDto.setName(user.getName());
            userDto.setEmail(user.getEmail());

            List<AddressDto> addresses = userDto.getAddresses();
            user.getAddresses().forEach(userAddress -> {
                AddressDto userAddressDto = new AddressDto();
                userAddressDto.setId(userAddress.getId());
                userAddressDto.setCity(userAddress.getCity());
                userAddressDto.setStreet(userAddress.getStreet());
                userAddressDto.setZipcode(userAddress.getZipcode());

                addresses.add(userAddressDto);
            });

            List<PhoneDto> phones = userDto.getPhones();
            user.getPhones().forEach(userPhones -> {
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
    public UserResultDto listUser() {
        UserResultDto userResultDto = new UserResultDto();

        try {
            // 모든 유저를 조회하면서, 주소와 전화번호를 함께 조회합니다.
            List<User> users = userRepository.findAllWithAddressesAndPhones();
            List<UserDto> userDtos = new ArrayList<>();

            for (User user : users) {
                UserDto userDto = new UserDto();
                userDto.setId(user.getId());
                userDto.setName(user.getName());
                userDto.setEmail(user.getEmail());

                // 해당 유저의 주소 정보를 설정합니다.
                List<AddressDto> addresses = new ArrayList<>();
                user.getAddresses().forEach(userAddress -> {
                    AddressDto userAddressDto = new AddressDto();
                    userAddressDto.setId(userAddress.getId());
                    userAddressDto.setCity(userAddress.getCity());
                    userAddressDto.setStreet(userAddress.getStreet());
                    userAddressDto.setZipcode(userAddress.getZipcode());
                    addresses.add(userAddressDto);
                });
                userDto.setAddresses(addresses);

                // 해당 유저의 전화번호 정보를 설정합니다.
                List<PhoneDto> phones = new ArrayList<>();
                user.getPhones().forEach(userPhone -> {
                    PhoneDto phoneDto = new PhoneDto();
                    phoneDto.setId(userPhone.getId());
                    phoneDto.setPhoneNumber(userPhone.getPhoneNumber());
                    phones.add(phoneDto);
                });
                userDto.setPhones(phones);

                userDtos.add(userDto);
            }

            userResultDto.setUserList(userDtos);
            userResultDto.setResult("success");
        } catch (Exception e) {
            e.printStackTrace();
            userResultDto.setResult("fail");
        }

        return userResultDto;
    }



    @Override
    public UserResultDto insertAddress(Address address, Long userId) {
        UserResultDto userResultDto = new UserResultDto();

        try {
            User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
            address.setUser(user);  // 사용자와 연결
            addressRepository.save(address);
            userResultDto.setResult("success");
        } catch (Exception e) {
            e.printStackTrace();
            userResultDto.setResult("fail");
        }

        return userResultDto;
    }

    @Override
    public UserResultDto insertPhone(Phone phone, Long userId) {
        UserResultDto userResultDto = new UserResultDto();
        try {
            User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
            phone.setUser(user);  // 사용자와 연결
            phoneRepository.save(phone);
            userResultDto.setResult("success");
        } catch (Exception e) {
            e.printStackTrace();
            userResultDto.setResult("fail");
        }

        return userResultDto;
    }

    @Override
    public UserResultDto deleteAddress(Long addressId) {
        UserResultDto userResultDto = new UserResultDto();

        try {
            addressRepository.deleteById(addressId);
            userResultDto.setResult("success");
        } catch (Exception e) {
            e.printStackTrace();
            userResultDto.setResult("fail");
        }

        return userResultDto;
    }

    @Override
    public UserResultDto deletePhone(Long phoneId) {
        UserResultDto userResultDto = new UserResultDto();

        try {
            phoneRepository.deleteById(phoneId);
            userResultDto.setResult("success");
        } catch (Exception e) {
            e.printStackTrace();
            userResultDto.setResult("fail");
        }

        return userResultDto;
    }

    @Override
    @Transactional
    public UserResultDto updateUser(UserDto userDto) {
        UserResultDto userResultDto = new UserResultDto();

        try {
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

    @Override
    @Transactional
    public UserResultDto updateAddress(Address userAddress) {
        UserResultDto userResultDto = new UserResultDto();

        try {
            Optional<Address> addressOptional = addressRepository.findById(userAddress.getId());
            if (addressOptional.isPresent()) {
                Address address = addressOptional.get();

                address.setCity(userAddress.getCity());
                address.setStreet(userAddress.getStreet());
                address.setZipcode(userAddress.getZipcode());

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

    @Override
    @Transactional
    public UserResultDto updatePhone(Phone userPhone) {
        UserResultDto userResultDto = new UserResultDto();
        try {
            Optional<Phone> phoneOptional = phoneRepository.findById(userPhone.getId());
            if (phoneOptional.isPresent()) {
                Phone phone = phoneOptional.get();

                phone.setPhoneNumber(userPhone.getPhoneNumber());
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

    @Override
    @Transactional
    public UserResultDto deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("no such user"));
        userRepository.delete(user);
        UserResultDto userResultDto = new UserResultDto();
        userResultDto.setResult("success");
        return userResultDto;
    }
}