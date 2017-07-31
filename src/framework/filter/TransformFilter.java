package framework.filter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import framework.sdk.Framework;

/*
 * servlet模块配置<br />
 * 注意：使用servlet的时候，除了配置dispatch之外，还需要在web.xml里配置module.servlet.filter.TransformFilter过滤器，用来统一转换编码。配置信息如下：<br />
 * <filter><br />
 * <filter-name>transformFilter</filter-name><br />
 * <filter-class>module.servlet.filter.TransformFilter</filter-class><br />
 * <init-param><br />
 * <param-name>encoding</param-name><br />
 * <param-value>utf-8</param-value><br />
 * </init-param><br />
 * </filter><br />
 * <filter-mapping><br />
 * <filter-name>transformFilter</filter-name><br />
 * <url-pattern>/*</url-pattern><br />
 * </filter-mapping><br />
 */

/**
 * 编码过滤器<br />
 * 从web.xml中读取字符集
 */
public class TransformFilter implements Filter {
        private String encoding;

        public void init(FilterConfig filterConfig) {
                try {
                        this.encoding = filterConfig.getInitParameter("encoding");
                } catch (Exception e) {
                        Framework.LOG.error(e.toString());
                }
        }

        public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) {
                /*
                 * 实现跨域（开始）
                 */
                HttpServletResponse hsr = (HttpServletResponse) response;
                /*
                 * 允许全部域名
                 */
                // hsr.addHeader("Access-Control-Allow-Origin", "*");
                /*
                 * 允许空
                 */
                hsr.addHeader("Access-Control-Allow-Origin", "null");
                /*
                 * 允许证书
                 */
                hsr.addHeader("Access-Control-Allow-Credentials", "true");
                /*
                 * 允许请求的方法
                 */
                hsr.addHeader("Access-Control-Allow-Methods", "POST, PUT, OPTIONS");
                /*
                 * 实现跨域（结束）
                 */
                /*
                 * 编码转换（开始）
                 */
                try {
                        if (null != this.encoding) {
                                request.setCharacterEncoding(this.encoding);
                                response.setCharacterEncoding(this.encoding);
                                response.setContentType("text/html;charset=" + this.encoding);
                        }
                        chain.doFilter(request, response);
                } catch (Exception e) {
                        StackTraceElement ste = e.getStackTrace()[0];
                        Framework.LOG.error("[File] " + ste.getFileName());
                        Framework.LOG.error("[Line Number] " + ste.getLineNumber());
                        Framework.LOG.error("[Method] " + ste.getMethodName());
                }
                /*
                 * 编码转换（结束）
                 */
        }

        public void destroy() {
                this.encoding = null;
        }
}