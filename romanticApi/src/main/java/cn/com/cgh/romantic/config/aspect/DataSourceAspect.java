package cn.com.cgh.romantic.config.aspect;

import cn.com.cgh.romantic.config.db.DynamicDataSourceHolder;
import cn.com.cgh.romantic.config.insterfaced.Ds;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

/**
 * @author cgh
 */
@Aspect
public class DataSourceAspect {
    // 设置Ds注解的切点表达式，所有Ds都会触发当前环绕通知
    @Pointcut("@annotation(cn.com.cgh.romantic.config.insterfaced.Ds)")
    public void dynamicDataSourcePointCut(){

    }

    //环绕通知
    @Around("dynamicDataSourcePointCut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable{
        //获取数据源的key
        String key = getDefineAnnotation(joinPoint).value();
        //将数据源设置为该key的数据源
        DynamicDataSourceHolder.setDynamicDataSourceKey(key);
        try {
            return joinPoint.proceed();
        } finally {
            //使用完成后切回master
            DynamicDataSourceHolder.removeDynamicDataSourceKey();
        }
    }

    /**
     * 先判断方法的注解，后判断类的注解，以方法的注解为准
     * @return Ds
     */
    private Ds getDefineAnnotation(ProceedingJoinPoint joinPoint){
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        return methodSignature.getMethod().getAnnotation(Ds.class);
    }
}
