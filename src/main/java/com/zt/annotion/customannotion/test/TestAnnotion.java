package com.zt.annotion.customannotion.test;

import com.zt.annotion.customannotion.Enums.MatchEnum;
import com.zt.annotion.customannotion.annotion.CheckMatch;
import com.zt.annotion.customannotion.annotion.CheckParam;
import com.zt.annotion.customannotion.annotion.ValidateServiceData;
import com.zt.annotion.customannotion.entity.Person;
import com.zt.annotion.customannotion.entity.ResultJson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 功能描述:
 *
 * @author: MR.zt
 * @date: 2019/7/30 13:05
 */
@RestController
@RequestMapping
@Slf4j
public class TestAnnotion {

    @RequestMapping("/param")
    @ValidateServiceData
    public ResultJson testParam(@CheckParam String name, @CheckParam Integer age ){
        log.info(name+":"+age);
        return ResultJson.returnOK("成功了");
    }
    @RequestMapping("/obj")
    @ValidateServiceData
    public ResultJson testObject(@CheckParam(attributes = {"name","age","idCard"}) Person person){
        log.info(person.getName()+":"+person.getAge());
        return ResultJson.returnOK("成功了");
    }

    @RequestMapping("/match")
    @ValidateServiceData
    public ResultJson testMatch(@CheckMatch(expression = MatchEnum.identity) String idcard){
        log.info(idcard);
        return ResultJson.returnOK("成功了");
    }
    @RequestMapping("/sensitive")
    public ResultJson testSensitive(){
        Person ps = new Person("张三", 78, "533526199506040218");
        return ResultJson.returnOK(ps,"成功了");
    }
}
