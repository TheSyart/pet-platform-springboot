package com.pets.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pets.mapper.EncyclopediaMapper;
import com.pets.pojo.dto.EncyclopediaCreateInfo;
import com.pets.pojo.dto.EncyclopediaQueryDTO;
import com.pets.pojo.dto.PageRequestDto;
import com.pets.pojo.dto.UpdateStatusDTO;
import com.pets.pojo.entity.PetEncyclopedia;
import com.pets.pojo.entity.PetEncyclopediaDetails;
import com.pets.pojo.vo.CommonResponseVo;
import com.pets.pojo.vo.EncyclopediaVO;
import com.pets.service.EncyclopediaService;
import com.pets.utils.base.BaseErrorException;
import com.pets.utils.pic.HandlePic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.UnknownHostException;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class EncyclopediaServiceImpl implements EncyclopediaService {

    @Autowired
    EncyclopediaMapper encyclopediaMapper;

    @Autowired
    HandlePic handlePic;

    @Override
    public List<String> queryAllPetBreed() {
        return encyclopediaMapper.queryAllPetBreed();
    }

    public List<PetEncyclopedia> getEncyclopediaSpeciesSign() throws UnknownHostException {
        List<PetEncyclopedia> results = encyclopediaMapper.getSpeciesSignAndName();
        for (PetEncyclopedia result : results) {
            result.setImage(handlePic.fixPath(result.getImage()));
        }
        return results;
    }

    @Override
    public List<PetEncyclopedia> oneSpeciesDetails(String id) throws UnknownHostException {
        List<PetEncyclopedia> results = encyclopediaMapper.oneSpeciesDetails(id);
        for (PetEncyclopedia result : results) {
            result.setImage(handlePic.fixPath(result.getImage()));
        }
        return results;
    }

    @Override
    public PetEncyclopediaDetails onePetDetails(String id) throws UnknownHostException {
        PetEncyclopediaDetails result = encyclopediaMapper.onePetDetails(id);
        result.setImage(handlePic.fixPath(result.getImage()));
        return result;
    }

    @Override
    public Object queryOneEncyclopedia(Long id) throws UnknownHostException {
        PetEncyclopediaDetails result = encyclopediaMapper.queryOneEncyclopedia(id);
        result.setImage(handlePic.fixPath(result.getImage()));
        return result;
    }

    @Override
    public CommonResponseVo queryAllEncyclopedia(PageRequestDto<EncyclopediaQueryDTO> pageRequestDto) {
        PageHelper.startPage(pageRequestDto.getPagination().getPage(), pageRequestDto.getPagination().getSize());

        EncyclopediaQueryDTO encyclopediaQueryDTO = pageRequestDto.getConditions();
        CommonResponseVo commonResponseVo = new CommonResponseVo();

        List<EncyclopediaVO> encyclopediaList = encyclopediaMapper.queryAllEncyclopedia(encyclopediaQueryDTO);

        PageInfo<EncyclopediaVO> pageInfo = new PageInfo<>(encyclopediaList);

        commonResponseVo.setList(pageInfo.getList());
        commonResponseVo.setTotal(pageInfo.getTotal());
        return commonResponseVo;
    }

    @Override
    public String insertOneEncyclopedia(EncyclopediaCreateInfo encyclopediaCreateInfo) {
        if (encyclopediaMapper.insertOneEncyclopedia(encyclopediaCreateInfo) != 1){
            throw new BaseErrorException("新宠物百科录入失败!");
        }
        return "新宠物百科录入成功!";
    }

    @Override
    public String updateEncyclopediaStatus(UpdateStatusDTO updateStatusDTO) {
        if (encyclopediaMapper.updateEncyclopediaStatus(updateStatusDTO.getId(), updateStatusDTO.getStatus()) != 1){
            throw new BaseErrorException("宠物百科状态操作失败!");
        }
        return "宠物百科状态更改成功!";
    }

    @Override
    public String updateEncyclopedia(PetEncyclopediaDetails petEncyclopediaDetails) {
        if (encyclopediaMapper.updateEncyclopedia(petEncyclopediaDetails) != 1){
            throw new BaseErrorException("宠物百科信息编辑失败!");
        }
        return "宠物百科信息更改成功!";
    }

    @Override
    public String deleteEncyclopediaImage(String id) {
        if(encyclopediaMapper.deleteEncyclopediaImage(id) != 1){
            throw new BaseErrorException("数据库同步删除照片失败!");
        }
        return null;
    }
}
