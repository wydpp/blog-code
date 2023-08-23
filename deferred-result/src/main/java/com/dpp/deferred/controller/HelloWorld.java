package com.dpp.deferred.controller;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName HelloWorld.java
 * @Author duanpengpeng
 * @Version 1.0.0
 * @Description
 * @CreateTime 2023/08/23 09:41:00
 */
@RestController
public class HelloWorld {

    static {
        List<FlowRule> rules = new ArrayList<>();
        FlowRule rule = new FlowRule();
        rule.setResource("sayHello");
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        // Set limit QPS to 1.
        rule.setCount(1);
        rules.add(rule);
        FlowRuleManager.loadRules(rules);
    }

    @RequestMapping("/hello")
    public String sayHello() {
        try (Entry entry = SphU.entry("sayHello")) {
            System.out.println("this is sayHello method");
        } catch (BlockException exception) {
            System.out.println("限流触发！！");
            exception.printStackTrace();
            return exception.getMessage();
        }
        return "hello " + System.currentTimeMillis();
    }
}
