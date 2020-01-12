package cn.com.filter.shiro.base;

import cn.com.SpringContextUtil;
import lombok.extern.log4j.Log4j;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.SubjectContext;
import org.apache.shiro.web.mgt.DefaultWebSubjectFactory;
import org.apache.shiro.web.subject.WebSubject;
import org.apache.shiro.web.subject.WebSubjectContext;
import org.springframework.context.annotation.Configuration;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Log4j
public class MyDefaultWebSubjectFactory extends DefaultWebSubjectFactory {

    private final DefaultSessionStorageEvaluator storageEvaluator = new DefaultSessionStorageEvaluator();

    @Override
    public Subject createSubject(SubjectContext context) {
        boolean isNotBasedOnWebSubject = context.getSubject() != null && !(context.getSubject() instanceof WebSubject);
        if (!(context instanceof WebSubjectContext) || isNotBasedOnWebSubject) {
            return super.createSubject(context);
        }
        WebSubjectContext webSubjectContext = (WebSubjectContext)context;
        HttpServletRequest request = (HttpServletRequest) webSubjectContext.getServletRequest();
        HttpServletResponse response = (HttpServletResponse) webSubjectContext.getServletResponse();
        boolean separation = false;
        if (!context.isAuthenticated()) {
            try {
                separation = SpringContextUtil.isSeparation(request,response);
                this.storageEvaluator.setSessionStorageEnabled(separation);
                context.setSessionCreationEnabled(separation);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        log.info(">>>>>>>>>>>>>>>createSubject 是否创建 Session: [" + separation + "]<<<<<<<<<<<<<");
        return super.createSubject(context);
    }
}
