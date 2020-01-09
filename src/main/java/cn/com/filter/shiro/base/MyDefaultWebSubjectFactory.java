package cn.com.filter.shiro.base;

import lombok.extern.log4j.Log4j;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.SubjectContext;
import org.apache.shiro.web.mgt.DefaultWebSubjectFactory;

@Log4j
public class MyDefaultWebSubjectFactory extends DefaultWebSubjectFactory {

    public MyDefaultWebSubjectFactory() {
        super();
    }

    @Override
    public Subject createSubject(SubjectContext context) {
        log.info(">>>>>>>>>>>>>>>createSubject<<<<<<<<<<<<<");
        return super.createSubject(context);
    }
}
