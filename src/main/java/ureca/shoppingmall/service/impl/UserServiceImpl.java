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

        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            UserDto userDto = new UserDto();
            userDto.setId(user.getId());
            userDto.setName(user.getName());
            userDto.setEmail(user.getEmail());

            List<Address> addressList = addressRepository.findByUserId(id);
            List<AddressDto> addresses = userDto.getAddresses();
            addressList.forEach(userAddress -> {
                AddressDto userAddressDto = new AddressDto();
                userAddressDto.setId(userAddress.getId());
                userAddressDto.setCity(userAddress.getCity());
                userAddressDto.setStreet(userAddress.getStreet());
                userAddressDto.setZipcode(userAddress.getZipcode());

                addresses.add(userAddressDto);
            });

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
            Optional<Address> addressOptional = addressRepository.findById(addressId);
            if (addressOptional.isPresent()) {
                addressRepository.deleteById(addressId);
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
    public UserResultDto deletePhone(Long phoneId) {
        UserResultDto userResultDto = new UserResultDto();

        try {
            Optional<Phone> phoneOptional = phoneRepository.findById(phoneId);
            if (phoneOptional.isPresent()) {
                phoneRepository.deleteById(phoneId);
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
    public UserResultDto updatePhone(Phone userPhone) {
        UserResultDto userResultDto = new UserResultDto();
        try {
            Optional<Phone> phoneOptional = phoneRepository.findById(userPhone.getId());
            if (phoneOptional.isPresent()) {
                Phone phone = phoneOptional.get();

                phone.setPhoneNumber(userPhone.getPhoneNumber());

                phoneRepository.save(phone);
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