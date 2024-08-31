package ureca.shoppingmall.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ureca.shoppingmall.domain.user.Address;
import ureca.shoppingmall.domain.user.Phone;

import java.util.List;

public interface PhoneRepository extends JpaRepository<Phone, Long> {
    List<Phone> findByUserId(Long id);
}
