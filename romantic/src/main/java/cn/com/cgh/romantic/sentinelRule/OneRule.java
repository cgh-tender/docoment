package cn.com.cgh.romantic.sentinelRule;


import cn.com.cgh.romantic.config.Constants;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class OneRule implements ApplicationRunner {
    static {
        log.info("OneRule:已启动");
    }
    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("OneRule:run");
        List<FlowRule> rules = new ArrayList<>();
        //新建一个规则
        FlowRule rule = new FlowRule();
        //保护的资源
        rule.setResource(Constants.ONE_RULE);
        //设置为QPS的规则类型
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        //值为2,则表示每秒只能访问此资源两次
        rule.setCount(1);
        rules.add(rule);
        //加载此资源
        FlowRuleManager.loadRules(rules);
    }
}
