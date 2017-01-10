import com.netflix.zuul.ZuulFilter
import com.netflix.zuul.context.RequestContext

class RouteRequest extends ZuulFilter{

    @Override
    String filterType() {
        return "route"
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
        println("水电费水电费水电费")
       // RequestContext.currentContext.getResponse().sendRedirect("http://www.zbj.com")
        return null
    }
}
