package ureca.shoppingmall.domain.user.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ureca.shoppingmall.common.enums.RoleStatus;
import ureca.shoppingmall.domain.user.Address;
import ureca.shoppingmall.domain.user.Phone;
import ureca.shoppingmall.domain.user.Role;
import ureca.shoppingmall.domain.user.User;
import ureca.shoppingmall.domain.user.dto.AddressDto;
import ureca.shoppingmall.domain.user.dto.PhoneDto;
import ureca.shoppingmall.domain.user.dto.UserDto;
import ureca.shoppingmall.domain.user.dto.UserResultDto;
//import ureca.shoppingmall.repository.AddressRepository;
import ureca.shoppingmall.domain.user.repository.AddressRepository;
import ureca.shoppingmall.domain.user.repository.PhoneRepository;
import ureca.shoppingmall.domain.user.repository.RoleRepository;
import ureca.shoppingmall.domain.user.repository.UserRepository;
import ureca.shoppingmall.domain.user.service.UserService;

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
            List<User> users = userRepository.findAllWithAddressesAndPhones();
            List<UserDto> userDtos = new ArrayList<>();

            for (User user : users) {
                UserDto userDto = new UserDto();
                userDto.setId(user.getId());
                userDto.setName(user.getName());
                userDto.setEmail(user.getEmail());

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
            address.setUser(user);
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
            phone.setUser(user);
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

                if(userDto.getName() != null){
                    user.setName(userDto.getName());
                }
                if(userDto.getEmail() != null){
                    user.setEmail(userDto.getEmail());
                }
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
    public UserResultDto updateAddress(AddressDto userAddressDto) {
        UserResultDto userResultDto = new UserResultDto();

        try {
            Optional<Address> addressOptional = addressRepository.findById(userAddressDto.getId());
            if (addressOptional.isPresent()) {
                Address address = addressOptional.get();

                if (userAddressDto.getCity() != null) {
                    address.setCity(userAddressDto.getCity());
                }
                if (userAddressDto.getStreet() != null) {
                    address.setStreet(userAddressDto.getStreet());
                }
                if (userAddressDto.getZipcode() != null) {
                    address.setZipcode(userAddressDto.getZipcode());
                }
                addressRepository.save(address);

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
    public UserResultDto updatePhone(PhoneDto phoneDto) {
        UserResultDto userResultDto = new UserResultDto();
        try {
            Optional<Phone> phoneOptional = phoneRepository.findById(phoneDto.getId());
            if (phoneOptional.isPresent()) {
                Phone phone = phoneOptional.get();

                phone.setPhoneNumber(phoneDto.getPhoneNumber());
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