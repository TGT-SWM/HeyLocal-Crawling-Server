/**
 * packageName    : kr.pe.heylocal.crawling.mapper
 * fileName       : MenuMapper
 * author         : 우태균
 * date           : 2022/10/30
 * description    : 메뉴 정보 DTO 관 매퍼
 */

package kr.pe.heylocal.crawling.mapper;

import kr.pe.heylocal.crawling.dto.MenuDto;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Mapper(builder = @Builder(disableBuilder = true))
public interface MenuMapper {
  MenuMapper INSTANCE = Mappers.getMapper(MenuMapper.class);

  MenuDto toMenuDto(String name, String price);

  default List<MenuDto> toMenuDtoList(Map<String, String> menuInfoMap) {
    List<MenuDto> result = menuInfoMap.entrySet().stream().map((entry) -> {
      String name = entry.getKey();
      String price = entry.getValue();
      return MenuMapper.INSTANCE.toMenuDto(name, price);
    }).collect(Collectors.toList());

    return result;
  }
}
