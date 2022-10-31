/**
 * packageName    : kr.pe.heylocal.crawling.util.jpa
 * fileName       : UpperCaseNamingStrategy
 * author         : 우태균
 * date           : 2022/10/31
 * description    : JPA의 네이밍 전략 설정
 *                  대문자+언더바 조합
 */

package kr.pe.heylocal.crawling.util.jpa;

import org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy;
import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;

import java.util.Locale;

public class UpperCaseNamingStrategy extends CamelCaseToUnderscoresNamingStrategy {
  @Override
  protected Identifier getIdentifier(String name, boolean quoted, JdbcEnvironment jdbcEnvironment) {
    return new Identifier(name.toUpperCase(Locale.ROOT), quoted );
  }
}
