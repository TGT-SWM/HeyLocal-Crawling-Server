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

  MenuDto toDto(String name, String price, String photo);
  MenuDto toDto(Menu menu);
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "placeInfo", ignore = true)
  @Mapping(target = "createdDate", ignore = true)
  @Mapping(target = "modifiedDate", ignore = true)
  Menu toEntity(MenuDto menuDto);

  default List<MenuDto> toDtoList(Map<String, List<String>> menuInfoMap) {
    List<MenuDto> result = menuInfoMap.entrySet().stream().map((entry) -> {
      String menuName;
      String menuPrice;
      String menuPhoto;
      List<String> menuInfo = entry.getValue();

      if (menuInfo.size() == 1)
        menuPhoto = null;
      else
        menuPhoto = menuInfo.get(1);

      menuPrice = menuInfo.get(0);
      menuName = entry.getKey();
      return MenuMapper.INSTANCE.toDto(menuName, menuPrice, menuPhoto);
    }).collect(Collectors.toList());

    return result;
  }

  default List<MenuDto> toDtoList(List<Menu> menuInfoList) {
    return menuInfoList.stream()
        .map(MenuMapper.INSTANCE::toDto)
        .collect(Collectors.toList());
  }
}
