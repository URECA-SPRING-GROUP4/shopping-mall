package ureca.shoppingmall.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ureca.shoppingmall.domain.Item.Item;

public interface AdminItemRepository extends JpaRepository<Item, Long> {

}
