/**
 * packageName    : kr.pe.heylocal.crawling.mapper
 * fileName       : MenuMapper
 * author         : 우태균
 * date           : 2022/10/30
 * description    : 메뉴 정보 DTO 관 매퍼
 */

package kr.pe.heylocal.crawling.mapper;

import kr.pe.heylocal.crawling.domain.Menu;
import kr.pe.heylocal.crawling.dto.MenuDto;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Mapper(builder = @Builder(disableBuilder = true))
public interface MenuMapper {
  MenuMapper INSTANCE = Mappers.getMapper(MenuMapper.class);

  MenuDto toMenuDto(String name, String price);
  MenuDto toMenuDto(Menu menu);
  @Mapping(target = "placeId", source = "placeId")
  @Mapping(target = "id", ignore = true)
  Menu toMenuEntity(MenuDto menuDto, String placeId);

  default List<MenuDto> toMenuDtoList(Map<String, String> menuInfoMap) {
    List<MenuDto> result = menuInfoMap.entrySet().stream().map((entry) -> {
      String name = entry.getKey();
      String price = entry.getValue();
      return MenuMapper.INSTANCE.toMenuDto(name, price);
    }).collect(Collectors.toList());

    return result;
  }

  default List<MenuDto> toMenuDtoList(List<Menu> menuInfoList) {
    return menuInfoList.stream()
        .map(MenuMapper.INSTANCE::toMenuDto)
        .collect(Collectors.toList());
  }
}
