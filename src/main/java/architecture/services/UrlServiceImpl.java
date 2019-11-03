package architecture.services;

import architecture.services.interfaces.UrlService;
import org.springframework.beans.factory.annotation.Autowired;
import javax.servlet.http.HttpServletRequest;

public class UrlServiceImpl implements UrlService {
    @Autowired
    private HttpServletRequest request;

    @Override
    public String getLocaleFromUrl(){
        String[] split = this.request.getRequestURI().split("/");
        System.out.println(split[1]);
        return split[1];
    }
}
