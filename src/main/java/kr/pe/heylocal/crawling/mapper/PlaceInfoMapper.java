package kr.pe.heylocal.crawling.mapper;

import kr.pe.heylocal.crawling.domain.BusinessTime;
import kr.pe.heylocal.crawling.domain.Menu;
import kr.pe.heylocal.crawling.domain.PlaceInfo;
import kr.pe.heylocal.crawling.dto.BusinessTimeDto;
import kr.pe.heylocal.crawling.dto.MenuDto;
import kr.pe.heylocal.crawling.dto.PlaceInfoDto;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(builder = @Builder(disableBuilder = true),
        uses = {MenuMapper.class, BusinessTimeMapper.class}
)
public interface PlaceInfoMapper {
  PlaceInfoMapper INSTANCE = Mappers.getMapper(PlaceInfoMapper.class);

  @Mapping(target = "businessTimes", source = "businessTimeList")
  @Mapping(target = "menus", source = "menuList")
  PlaceInfoDto toDto(PlaceInfo placeInfo);

  PlaceInfoDto toDto(List<MenuDto> menus, List<BusinessTimeDto> businessTimes);

  default PlaceInfo toEntity(PlaceInfoDto placeInfoDto, long id) {
    PlaceInfo placeInfo = new PlaceInfo();

    placeInfo.setId(id);

    List<Menu> menuList = placeInfoDto.getMenus().stream()
        .map(menuDto -> {
          Menu menu = MenuMapper.INSTANCE.toEntity(menuDto);
          menu.setPlaceInfo(placeInfo);
          return menu;
        }).collect(Collectors.toList());

    List<BusinessTime> businessTimeList = placeInfoDto.getBusinessTimes().stream()
        .map(businessTimeDto -> {
          BusinessTime businessTime = BusinessTimeMapper.INSTANCE.toEntity(businessTimeDto);
          businessTime.setPlaceInfo(placeInfo);
          return businessTime;
        }).collect(Collectors.toList());

    placeInfo.setMenuList(menuList);
    placeInfo.setBusinessTimeList(businessTimeList);

    return placeInfo;
  }
}
