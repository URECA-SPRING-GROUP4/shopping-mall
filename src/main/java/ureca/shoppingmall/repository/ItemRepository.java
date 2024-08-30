package ureca.shoppingmall.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ureca.shoppingmall.domain.Item.Item;
import ureca.shoppingmall.dto.ItemDto;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    Item findByName(String name);

    // 상품 조회
    List<ItemDto> findAllByOrderByIdDesc();
    Item findById(long id);

    // 재고 확인
    @Query("select i from Item i where i.id = :id and i.stockQuantity >= :quantity")
    List<Item> findByStockQuantity(@Param("id") long id, @Param("quantity") float quantity);

}
