package com.pets.mapper;

import com.pets.pojo.dto.EncyclopediaCreateInfo;
import com.pets.pojo.dto.EncyclopediaQueryDTO;
import com.pets.pojo.entity.PetEncyclopedia;
import com.pets.pojo.entity.PetEncyclopediaDetails;
import com.pets.pojo.vo.EncyclopediaVO;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface EncyclopediaMapper {
    List<String> queryAllPetBreed();

    List<PetEncyclopedia> getSpeciesSignAndName();

    List<PetEncyclopedia> oneSpeciesDetails(String id);

    PetEncyclopediaDetails onePetDetails(String id);

    PetEncyclopediaDetails queryOneEncyclopedia(Long id);

    List<EncyclopediaVO> queryAllEncyclopedia(EncyclopediaQueryDTO encyclopediaQueryDTO);

    int insertOneEncyclopedia(EncyclopediaCreateInfo encyclopediaCreateInfo);

    int updateEncyclopediaStatus(Long id, Integer status);

    int updateEncyclopedia(PetEncyclopediaDetails petEncyclopediaDetails);

    int deleteEncyclopediaImage(String id);

}
