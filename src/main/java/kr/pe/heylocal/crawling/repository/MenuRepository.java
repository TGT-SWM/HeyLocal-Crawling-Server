/**
 * packageName    : kr.pe.heylocal.crawling.repository
 * fileName       : MenuRepository
 * author         : 우태균
 * date           : 2022/10/31
 * description    : 메뉴 엔티티 레포지토리
 */

package kr.pe.heylocal.crawling.repository;

import kr.pe.heylocal.crawling.domain.Menu;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class MenuRepository {
  @PersistenceContext
  private EntityManager em;

  public void save(Menu menu) {
    em.persist(menu);
  }

  public List<Menu> findByPlaceId(String placeId) {
    String jpql = "select m from Menu m" +
        " where m.placeId = :placeId";

    return em.createQuery(jpql, Menu.class)
        .setParameter("placeId", placeId)
        .getResultList();
  }
}
