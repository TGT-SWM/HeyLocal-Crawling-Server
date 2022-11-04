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
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

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

  public Optional<Menu> findFirstByPlaceId(long placeId) {
    String jpql = "select m from Menu m" +
        " where m.id = :placeId";
    Menu menu;

    try {
      menu = em.createQuery(jpql, Menu.class)
          .setParameter("placeId", placeId)
          .setMaxResults(1)
          .getSingleResult();
    } catch (NoResultException e) {
      return Optional.empty();
    }

    return Optional.of(menu);
  }

  public void removeAllByPlaceId(String placeId) {
    String jpql = "delete from Menu m" +
        " where m.placeId = :placeId";

    em.createQuery(jpql)
        .setParameter("placeId", placeId)
        .executeUpdate();
  }
}
