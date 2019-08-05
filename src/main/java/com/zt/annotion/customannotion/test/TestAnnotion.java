package com.zt.annotion.customannotion.test;

import com.zt.annotion.customannotion.Enums.MatchEnum;
import com.zt.annotion.customannotion.annotion.*;
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
    public ResultJson testObject(@CheckParam Person person){
        log.info(person.getName()+":"+person.getAge());
        return ResultJson.returnOK("成功了");
    }

    @RequestMapping("/match")
    @ValidateServiceData
    public ResultJson testMatch(@CheckMatch(matchType = MatchEnum.Identity) String idcard){
        log.info(idcard);
        return ResultJson.returnOK("成功了");
    }
    @RequestMapping("/sensitive")
    @RepeatSubmitLimiter
    public ResultJson testSensitive(){
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Person ps = new Person("张三", 78, "533526199506040218");
        return ResultJson.returnOK(ps,"成功了");
    }
    @RequestMapping("/lock")
    @GlobalLock(key = {"#person.name","#person.age"},waitTime = 2, leaseTime = 120)
    public ResultJson testglobalLock(Person person){
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return ResultJson.returnOK("完美","成功了");
    }
}
