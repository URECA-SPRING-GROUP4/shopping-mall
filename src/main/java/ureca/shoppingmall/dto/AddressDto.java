package ureca.shoppingmall.dto;

import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import ureca.shoppingmall.domain.user.User;

@Data
public class AddressDto {
    private Long id;
    private String city;
    private String street;
    private String zipcode;
}
