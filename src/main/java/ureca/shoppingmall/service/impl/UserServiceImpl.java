package ureca.shoppingmall.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ureca.shoppingmall.domain.user.Role;
import ureca.shoppingmall.domain.user.User;
import ureca.shoppingmall.dto.UserDto;
import ureca.shoppingmall.dto.UserResultDto;
//import ureca.shoppingmall.repository.AddressRepository;
import ureca.shoppingmall.repository.RoleRepository;
import ureca.shoppingmall.repository.UserRepository;
import ureca.shoppingmall.service.UserService;

import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
//    private final AddressRepository addressRepository;

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

//    @Override
//    public UserResultDto detailUser(Long id) {
//        UserResultDto userResultDto = new UserResultDto();
//        Optional<User> optionalUser = userRepository.findById(id);
//        optionalUser.ifPresentOrElse(
//                user -> {
//                    UserDto userDto = new UserDto();
//                    userDto.setId(user.getId());
//                    userDto.setName(user.getName());
//                    userDto.setEmail(user.getEmail());
//                    userDto.setPassword(user.getPassword());
//
//                    userResultDto.setUserDto(userDto);
//                    userResultDto.setResult("success");
//                },
//                () -> {
//                    userResultDto.setResult("fail");
//                }
//        );
//        return userResultDto;
//    }
//
//    @Override
//    public UserResultDto updateUser(User user) {
//        UserResultDto userResultDto = new UserResultDto();
//
//
//        userRepository.save(user);
//        return userResultDto;
//    }
}
