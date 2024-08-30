package ureca.shoppingmall.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ureca.shoppingmall.domain.user.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
