import com.netflix.zuul.ZuulFilter
import com.netflix.zuul.context.RequestContext

import javax.servlet.http.HttpServletRequest

class PostRequest extends ZuulFilter{

    @Override
    String filterType() {
        return "post"
    }

    @Override
    int filterOrder() {
        return 1000
    }

    @Override
    boolean shouldFilter() {
        return true
    }

    @Override
    Object run() {
        println("sdfsdf")

        return null
    }
}
