package ureca.shoppingmall.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ureca.shoppingmall.domain.user.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

    @Query("SELECT u FROM User u " +
            "LEFT JOIN FETCH u.addresses " +
            "LEFT JOIN FETCH u.phones")
    List<User> findAllWithAddressesAndPhones();

    @Query("SELECT u FROM User u " +
            "LEFT JOIN FETCH u.addresses " +
            "LEFT JOIN FETCH u.phones " +
            "WHERE u.id = :userId")
    Optional<User> findByIdWithAddressesAndPhones(@Param("userId") Long userId);
}
