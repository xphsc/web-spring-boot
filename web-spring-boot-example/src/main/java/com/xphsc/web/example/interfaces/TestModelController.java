package com.xphsc.web.example.interfaces;

import cn.xphsc.web.log.annotation.SysOperationLog;
import cn.xphsc.web.log.context.OperationLogContext;
import cn.xphsc.web.response.annotation.ResponseResult;
import com.xphsc.web.example.application.service.TestModelDictTansApplicationService;
import com.xphsc.web.example.domain.model.TestModel;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:
 * @since 1.0.0
 */
@RestController
@RequestMapping("/")
@ResponseResult
@Validated
public class TestModelController {

   private  TestModelDictTansApplicationService testModelDictTansApplicationService;

    @GetMapping("/test5")
    public  Object   test5()  {
        TestModel testModel=new TestModel();
        testModel.setAge(4);
        return testModelDictTansApplicationService.DictTansEntity(testModel);
    }

    @GetMapping("/get")
    @ResponseResult
    public Object get(){
        TestModel testModel=new TestModel();
        testModel.setAge(1);
        testModel.setName("小熊");
        return testModel;
    }

    @GetMapping("/get1")
    @ResponseResult
    public String get1(){
        TestModel testModel=new TestModel();
        testModel.setAge(1);
        testModel.setName("小熊");
        return "111";
    }

    @GetMapping("/get2/{name}")
    @ResponseResult
    public Object get2(@Valid @PathVariable("name") @Length(min = 5, max = 16, message = "账号长度为 5-16 位") String name){
        TestModel testModel=new TestModel();
        testModel.setAge(1);
        testModel.setName("小熊");
        return  name;
    }


    @GetMapping("/get3")
    public Object get3(){
        List list=new ArrayList();
        return  list.get(0);
    }

    @PostMapping("/list")
    @SysOperationLog(content = "#name")
    public Object list(@Valid @RequestBody TestModel testModel/*, LogContext logContext*/){
        TestModel testMode=new TestModel();
        testMode.setName("qqww");
        OperationLogContext.originObject(testMode);
        //   OperationLogContext.putVariable("name",66666);
        return testModel;
    }
    @PostMapping("/list2")
    //"'设置' + #id + '的密码为' + #password"
    @SysOperationLog(content = "#name")
    public Object list2(@Valid @RequestBody TestModel testModel/*, LogContext logContext*/){
        TestModel testMode=new TestModel();
        testMode.setName("556677878");
        OperationLogContext.originObject(testMode);
        //   OperationLogContext.putVariable("name",66666);
        return testModel;
    }


}
