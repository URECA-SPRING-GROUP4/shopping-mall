package ureca.shoppingmall.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class UserResultDto {
    private String result;
    private UserDto userDto;
    private String role;
    private List<UserDto> userList = new ArrayList<>();
}
