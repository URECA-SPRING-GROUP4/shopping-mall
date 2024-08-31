package ureca.shoppingmall.domain.user.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class UserDto {
    private Long id;
    private String name;
    private String email;
    private String password;

    private Map<Long, String> roles = new HashMap<>();
    private List<AddressDto> addresses = new ArrayList<>();
    private List<PhoneDto> Phones = new ArrayList<>();
}
