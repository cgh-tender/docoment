package cn.com.cgh.gateway.config;

import cn.com.cgh.gateway.error.MyBlockRequestHandler;
import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayFlowRule;
import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayRuleManager;
import com.alibaba.csp.sentinel.adapter.gateway.sc.SentinelGatewayFilter;
import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.BlockRequestHandler;
import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.GatewayCallbackManager;
import com.alibaba.csp.sentinel.adapter.gateway.sc.exception.SentinelGatewayBlockExceptionHandler;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.reactive.result.view.ViewResolver;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Configuration
public class GatewayConfiguration implements ApplicationRunner {
    private final List<ViewResolver> viewResolvers;
    private final ServerCodecConfigurer serverCodecConfigurer;

    public GatewayConfiguration(ObjectProvider<List<ViewResolver>> viewResolversProvider,
                                ServerCodecConfigurer serverCodecConfigurer) {
        this.viewResolvers = viewResolversProvider.getIfAvailable(Collections::emptyList);
        this.serverCodecConfigurer = serverCodecConfigurer;
    }

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SentinelGatewayBlockExceptionHandler sentinelGatewayBlockExceptionHandler() {
        // Register the block exception handler for Spring Cloud Gateway.
        return new SentinelGatewayBlockExceptionHandler(viewResolvers, serverCodecConfigurer);
    }

    @Bean
    @Order(-1)
    public GlobalFilter sentinelGatewayFilter() {
        return new SentinelGatewayFilter();
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        initGateWatGroup();
        initGatewayRules();
        initBlockHandlers();
    }

    private static void initGateWatGroup() {
//        Set<ApiDefinition> definitions = new HashSet<>();
//        ApiDefinition api1 = new ApiDefinition("login")
//                .setPredicateItems(new HashSet<ApiPredicateItem>() {{
//                    add(new ApiPathPredicateItem().setPattern("/login/**")
//                            /**
//                             * 匹配策略：
//                             * URL_MATCH_STRATEGY_EXACT：url精确匹配
//                             * URL_MATCH_STRATEGY_PREFIX：url前缀匹配
//                             * URL_MATCH_STRATEGY_REGEX：url正则匹配
//                             */
//                            .setMatchStrategy(SentinelGatewayConstants.URL_MATCH_STRATEGY_PREFIX));
//                }});
//        definitions.add(api1);
//        GatewayApiDefinitionManager.loadApiDefinitions(definitions);
    }

    private void initGatewayRules() {
        Set<GatewayFlowRule> rules = new HashSet<>();
//        rules.add(new GatewayFlowRule("hailtaxi-driver") // 资源名称，可以是网关中的 routeid或者用户自定义的 API分组名称
//                .setCount(2) // 限流阈值
//                .setIntervalSec(10) // 统计时间窗口默认1s
//                .setGrade(RuleConstant.FLOW_GRADE_QPS) // 限流模式
//                /**
//                 * 限流行为:
//                 * CONTROL_BEHAVIOR_RATE_LIMITER 匀速排队
//                 * CONTROL_BEHAVIOR_DEFAULT 快速失败(默认)
//                 * CONTROL_BEHAVIOR_WARM_UP：
//                 * CONTROL_BEHAVIOR_WARM_UP_RATE_LIMITER：
//                 */
//                .setControlBehavior(RuleConstant.CONTROL_BEHAVIOR_RATE_LIMITER)
//                //匀速排队模式下的最长排队时间，单位是毫秒，仅在匀速排队模式下生效
//                .setMaxQueueingTimeoutMs(1000)
//                /**
//                 * 热点参数限流配置
//                 * 若不设置,该网关规则将会被转换成普通流控规则；否则会转换成热点规则
//                 */
//                .setParamItem(new GatewayParamFlowItem()
//                        /**
//                         * 从请求中提取参数的策略:
//                         * PARAM_PARSE_STRATEGY_CLIENT_IP
//                         * PARAM_PARSE_STRATEGY_HOST
//                         * PARAM_PARSE_STRATEGY_HEADER
//                         * PARAM_PARSE_STRATEGY_URL_PARAM
//                         */
//                        .setParseStrategy(SentinelGatewayConstants.PARAM_PARSE_STRATEGY_URL_PARAM)
//                        /**
//                         * 若提取策略选择 Header 模式或 URL 参数模式，
//                         * 则需要指定对应的 header 名称或 URL 参数名称。
//                         */
//                        .setFieldName("token")
//                        /**
//                         * 参数的匹配策略：
//                         * PARAM_MATCH_STRATEGY_EXACT
//                         * PARAM_MATCH_STRATEGY_PREFIX
//                         * PARAM_MATCH_STRATEGY_REGEX
//                         * PARAM_MATCH_STRATEGY_CONTAINS
//                         */
//                        .setMatchStrategy(SentinelGatewayConstants.PARAM_MATCH_STRATEGY_EXACT)
//                        //参数值的匹配模式，只有匹配该模式的请求属性值会纳入统计和流控
//                        .setPattern("123456") // token=123456 10s内qps达到2次会被限流
//                )
//        );

        rules.add(new GatewayFlowRule("login")
                /**
                 * 规则是针对 API Gateway 的 route（RESOURCE_MODE_ROUTE_ID）
                 * 还是用户在 Sentinel 中定义的 API 分组（RESOURCE_MODE_CUSTOM_API_NAME），默认是 route。
                 */
//                .setResourceMode(SentinelGatewayConstants.RESOURCE_MODE_ROUTE_ID)
                .setCount(1)
                .setIntervalSec(2)
                .setGrade(RuleConstant.FLOW_GRADE_QPS)
        );
        GatewayRuleManager.loadRules(rules);
    }

    public void initBlockHandlers() {    // 限流后的响应
        BlockRequestHandler blockRequestHandler = (serverWebExchange, throwable) -> {
            MyBlockRequestHandler.ErrorResult result = new MyBlockRequestHandler.ErrorResult("1","xxxxxx");
            return ServerResponse.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON_UTF8).body(BodyInserters.fromObject(result));
        };
        GatewayCallbackManager.setBlockHandler(blockRequestHandler);    // 设置限流响应
    }
}
