package jetwang.test;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.client.apache.ApacheHttpClient;
import com.sun.jersey.client.apache.config.DefaultApacheHttpClientConfig;
import com.sun.jersey.spi.spring.container.servlet.SpringServlet;
import com.sun.jersey.test.framework.AppDescriptor;
import com.sun.jersey.test.framework.JerseyTest;
import com.sun.jersey.test.framework.WebAppDescriptor;
import com.sun.jersey.test.framework.spi.container.TestContainer;
import com.sun.jersey.test.framework.spi.container.TestContainerException;
import org.codehaus.jackson.jaxrs.JacksonJsonProvider;
import org.springframework.web.context.ContextLoaderListener;

public class RestTest extends JerseyTest {
    public RestTest() throws TestContainerException {
        super(new WebAppDescriptor.Builder().contextPath("")
                .contextParam("contextConfigLocation", "classpath:spring.xml;classpath:spring-test-knight.xml")
                .contextListenerClass(ContextLoaderListener.class)
                .servletClass(SpringServlet.class).servletPath("/rs").initParam("com.sun.jersey.api.json.POJOMappingFeature", "true")
                .build());
    }

    @Override
    protected Client getClient(TestContainer tc, AppDescriptor ad) {
        DefaultApacheHttpClientConfig cfg = new DefaultApacheHttpClientConfig();
        cfg.getClasses().add(JacksonJsonProvider.class);
        return ApacheHttpClient.create(cfg);
    }

    public ApacheHttpClient client() {
        return (ApacheHttpClient) super.client();
    }
}
