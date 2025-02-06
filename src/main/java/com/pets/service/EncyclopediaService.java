package com.pets.service;

import com.pets.pojo.dto.EncyclopediaCreateInfo;
import com.pets.pojo.dto.EncyclopediaQueryDTO;
import com.pets.pojo.dto.PageRequestDto;
import com.pets.pojo.dto.UpdateStatusDTO;
import com.pets.pojo.entity.PetEncyclopedia;
import com.pets.pojo.entity.PetEncyclopediaDetails;
import com.pets.pojo.vo.CommonResponseVo;
import org.springframework.stereotype.Service;

import java.net.UnknownHostException;
import java.util.List;

@Service
public interface EncyclopediaService {

    List<String> queryAllPetBreed();

    List<PetEncyclopedia> getEncyclopediaSpeciesSign() throws UnknownHostException;

    List<PetEncyclopedia> oneSpeciesDetails(String id) throws UnknownHostException;

    PetEncyclopediaDetails onePetDetails(String id) throws UnknownHostException;

    Object queryOneEncyclopedia(Long id) throws UnknownHostException;

    CommonResponseVo queryAllEncyclopedia(PageRequestDto<EncyclopediaQueryDTO> pageRequestDto);

    String insertOneEncyclopedia(EncyclopediaCreateInfo encyclopediaCreateInfo);

    String updateEncyclopediaStatus(UpdateStatusDTO updateStatusDTO);

    String updateEncyclopedia(PetEncyclopediaDetails petEncyclopediaDetails);

    String deleteEncyclopediaImage(String id);
}
