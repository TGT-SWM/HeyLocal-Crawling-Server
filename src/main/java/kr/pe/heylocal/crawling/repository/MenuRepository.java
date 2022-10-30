package kr.pe.heylocal.crawling.repository;

import kr.pe.heylocal.crawling.domain.Menu;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuRepository extends MongoRepository<Menu, String> {
  List<Menu> findAllByPlaceId(String placeId);
}
