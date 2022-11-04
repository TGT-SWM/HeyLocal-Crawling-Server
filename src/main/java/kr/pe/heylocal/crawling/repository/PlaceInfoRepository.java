package kr.pe.heylocal.crawling.repository;

import kr.pe.heylocal.crawling.domain.PlaceInfo;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Repository
public class PlaceInfoRepository {
  @PersistenceContext
  private EntityManager em;

  public void save(PlaceInfo placeInfo) {
    em.persist(placeInfo);
  }

  public Optional<PlaceInfo> findById(long id) {
    return Optional.ofNullable(
        em.find(PlaceInfo.class, id)
    );
  }

  public void deleteById(long id) {
    PlaceInfo placeInfo = em.find(PlaceInfo.class, id);
    em.remove(placeInfo);
  }
}
