package com.pets.service.impl;

import com.github.pagehelper.PageInfo;
import com.pets.pojo.dto.*;
import com.pets.pojo.entity.Emp;
import com.pets.pojo.entity.PageBean;
import com.pets.mapper.EmpMapper;
import com.pets.pojo.vo.CommonResponseVo;
import com.pets.pojo.vo.EmpVO;
import com.pets.service.EmpService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pets.utils.common.RandomNumberGenerator;
import com.pets.utils.common.TransformPinYin;
import com.pets.utils.base.BaseErrorException;
import com.pets.utils.pic.HandlePic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.UnknownHostException;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Random;


@Service
@Transactional(rollbackFor = Exception.class)
public class EmpServiceImpl implements EmpService {
    @Autowired
    private EmpMapper empMapper;

    @Autowired
    HandlePic handlePic;

    @Override
    public Emp queryOneEmp(Long id) throws UnknownHostException {
        Emp emp = empMapper.queryOneEmp(id);
        emp.setImage(handlePic.fixPath(emp.getImage()));
        return emp;
    }

    @Override
    public CommonResponseVo pageQueryAllEmp(PageRequestDto<EmpQueryDTO> pageRequestDto) {
        PageHelper.startPage(pageRequestDto.getPagination().getPage(), pageRequestDto.getPagination().getSize());

        EmpQueryDTO empQueryDTO = pageRequestDto.getConditions();
        CommonResponseVo commonResponseVo = new CommonResponseVo();

        List<EmpVO> empList = empMapper.pageQueryAllEmp(empQueryDTO);
        PageInfo<EmpVO> pageInfo = new PageInfo<>(empList);

        commonResponseVo.setList(pageInfo.getList());
        commonResponseVo.setTotal(pageInfo.getTotal());
        return commonResponseVo;
    }

    @Override
    public String insertNewEmp(EmpCreateInfo empCreateInfo) {
        TransformPinYin transformPinYin = new TransformPinYin();
        RandomNumberGenerator randomNumberGenerator = new RandomNumberGenerator();
        Emp emp = new Emp();


        String username = transformPinYin.transformPinYin(empCreateInfo.getName());
        int length = 14 - username.length();
        int num = randomNumberGenerator.random(length);
        String result = username + num;

        // 查看新增生成账号是否有重复
        int counter = 1;  // 用于避免重复的计数器
        while (empMapper.checkAccountUniqueness(result) != 0) {
            // 如果有重复，计数器递增并直接将计数器加到时间戳上
            result = username + (num + counter);
            counter++;
        }





        //查看电话是否有重复
        checkPhone(empCreateInfo.getPhone());

        emp.setUsername(result);
        emp.setName(empCreateInfo.getName());
        emp.setGender(empCreateInfo.getGender());
        emp.setImage(empCreateInfo.getImage());
        emp.setBirth(empCreateInfo.getBirth());
        emp.setJob(empCreateInfo.getJob());
        emp.setPhone(empCreateInfo.getPhone());
        if (empMapper.insertNewEmp(emp) != 1){
            throw new BaseErrorException("新员工添加失败!");
        }
        return "员工注册成功!";
    }

    @Override
    public List<String> queryAllJob() {
        return empMapper.queryAllJob();
    }

    @Override
    public List<Emp> queryAllEmpByJob(EmpQueryByJobDTO empQueryByJobDTO) throws UnknownHostException {
        List<Emp> empList = empMapper.queryAllEmpByJob(empQueryByJobDTO.getJob());
        for (Emp emp : empList) {
            emp.setImage(handlePic.fixPath(emp.getImage()));
        }
        return empList;
    }

    @Override
    public String updateEmpStatus(UpdateStatusDTO updateStatusDTO) {
        if (empMapper.updateEmpStatus(updateStatusDTO.getId(), updateStatusDTO.getStatus()) != 1){
            throw new BaseErrorException("员工状态操作失败!");
        }
        return "员工状态更改成功!";
    }

    @Override
    public String updateEmp(Emp emp) {
        //看电话是否与历史记录相同
        //如果不同查看是否重复
        Emp history = empMapper.queryOneEmp(emp.getId());
        if (!history.getPhone().equals(emp.getPhone())){
            //查看电话是否有重复
            checkPhone(emp.getPhone());
        }

        if (empMapper.updateEmp(emp) != 1){
            throw new BaseErrorException("员工信息编辑失败!");
        }
        return "员工信息更改成功!";
    }

    @Override
    public String deleteEmpImage(String id) {
        if(empMapper.deleteEmpImage(id) != 1){
            throw new BaseErrorException("数据库同步删除照片失败!");
        }
        return null;
    }

    @Override
    public Boolean updatePassword(UpdatePasswordDTO updatePasswordDTO) {
        String id = updatePasswordDTO.getId();
        Emp emp = empMapper.queryOneEmp(Long.valueOf(id));

        if (null == emp) {
            throw new BaseErrorException("修改信息错误，请刷新!");
        }

        //ack历史密码
        if (updatePasswordDTO.getStepId() == 0){
            return Objects.equals(emp.getPassword(), updatePasswordDTO.getPassword());

        //update新密码
        }else if (updatePasswordDTO.getStepId() == 1){
            if (empMapper.updatePassword(updatePasswordDTO) != 1){
                throw new BaseErrorException("密码修改失败!");
            }
        }else {
            throw new BaseErrorException("未知步骤!");
        }
        return null;
    }


    //查看电话是否有重复
    public void checkPhone(String phone){
        if (empMapper.checkPhoneUniqueness(phone) != 0) {
            throw new BaseErrorException("注册电话已重复!");
        }
    }
}
