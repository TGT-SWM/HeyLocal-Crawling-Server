package kr.pe.heylocal.crawling.mapper;

import kr.pe.heylocal.crawling.domain.BusinessTime;
import kr.pe.heylocal.crawling.domain.PlaceInfo;
import kr.pe.heylocal.crawling.dto.BusinessTimeDto;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(builder = @Builder(disableBuilder = true))
public interface BusinessTimeMapper {
  BusinessTimeMapper INSTANCE = Mappers.getMapper(BusinessTimeMapper.class);

  BusinessTimeDto toDto(BusinessTime businessTime);
  BusinessTimeDto toDto(String openTime);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "placeInfo", ignore = true)
  @Mapping(target = "createdDate", ignore = true)
  @Mapping(target = "modifiedDate", ignore = true)
  BusinessTime toEntity(BusinessTimeDto businessTimeDto);

  default List<BusinessTimeDto> toDtoList(List<String> btInfoList) {
    if (btInfoList == null) return null;
    return btInfoList.stream().map(this::toDto).collect(Collectors.toList());
  }
}
