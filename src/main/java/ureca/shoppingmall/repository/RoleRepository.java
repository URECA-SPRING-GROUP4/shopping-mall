package ureca.shoppingmall.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ureca.shoppingmall.common.enums.RoleStatus;
import ureca.shoppingmall.domain.user.Role;

import java.util.Set;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    Set<Role> findByUserId(Long userId);

}
