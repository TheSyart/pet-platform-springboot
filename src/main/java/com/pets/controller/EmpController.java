package com.pets.controller;

import com.pets.pojo.dto.*;
import com.pets.pojo.entity.Emp;
import com.pets.service.EmpService;
import com.pets.utils.base.ResponseData;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.net.UnknownHostException;

@Slf4j
@RequestMapping("/emp")
@RestController
public class EmpController {

    @Autowired
    private EmpService empService;

    @GetMapping( "/queryOneEmp")
    public ResponseData queryAllEmp(@RequestParam Long id) throws UnknownHostException {
        return ResponseData.OK(empService.queryOneEmp(id));
    }

    @PostMapping( "/pageQueryAllEmp")
    public ResponseData pageQueryAllEmp(@RequestBody PageRequestDto<EmpQueryDTO> pageRequestDto){
        return ResponseData.OK(empService.pageQueryAllEmp(pageRequestDto));
    }

    @PostMapping("/insertNewEmp")
    public ResponseData insertNewEmp(@RequestBody @Valid EmpCreateInfo empCreateInfo){
        return ResponseData.OK(empService.insertNewEmp(empCreateInfo));
    }

    @PostMapping( "/queryAllEmpByJob")
    public ResponseData queryAllEmpByJob(@RequestBody EmpQueryByJobDTO empQueryByJobDTO) throws UnknownHostException {
        return ResponseData.OK(empService.queryAllEmpByJob(empQueryByJobDTO));
    }

    //获取工作的全部分类
    @GetMapping("/allJob")
    public ResponseData queryAllJob() {
        return ResponseData.OK(empService.queryAllJob());
    }

    //更新员工状态
    @PostMapping("/updateEmpStatus")
    public ResponseData updateEmpStatus(@RequestBody UpdateStatusDTO updateStatusDTO) {
        return ResponseData.OK(empService.updateEmpStatus(updateStatusDTO));
    }

    //更新员工的数据
    @PostMapping("/updateEmp")
    public ResponseData updateEmp(@RequestBody Emp emp) {
        return ResponseData.OK(empService.updateEmp(emp));
    }

    //删除员工照片
    @GetMapping("/deleteEmpImage")
    public ResponseData deleteEmpImage(@RequestParam String id) {
        return ResponseData.OK(empService.deleteEmpImage(id));
    }

    //员工修改密码
    @PostMapping("/updatePassword")
    public ResponseData updatePassword(@RequestBody @Valid UpdatePasswordDTO updatePasswordDTO) {
        return ResponseData.OK(empService.updatePassword(updatePasswordDTO));
    }
}
