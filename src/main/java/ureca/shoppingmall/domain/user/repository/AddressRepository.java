package ureca.shoppingmall.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ureca.shoppingmall.domain.user.Address;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Long> {
    List<Address> findByUserId(Long id);
}
