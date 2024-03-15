package cn.com.cgh.oasis.util;

import cn.com.cgh.romantic.util.Searcher;
import jakarta.annotation.PreDestroy;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class IpSearcher {
    private Searcher searcher;

    public String search(String ipStr) throws Exception {
        if (null == searcher) {
            ClassPathResource pathResource = new ClassPathResource("ip2region.xdb");
            Path path = Paths.get(pathResource.getURL().toURI());
            searcher = Searcher.newWithFileOnly(path.toString());
        }
        return searcher.search(ipStr);
    }

    @PreDestroy
    public void destory() throws IOException {
        if (null != searcher) {
            searcher.close();
        }
    }

}
