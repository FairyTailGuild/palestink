package framework.sdk;

import java.util.HashMap;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;

public class Message {
        /*
         * 状态枚举代码
         */
        public static enum STATUS {
                SUCCESS/* 操作成功 */, ERROR/* 操作失败 */, EXCEPTION/* 操作异常 */, PARAM_INVALID/* 非法参数 */, PARAM_IS_NULL/* 参数为空 */, PARAM_FORMAT_ERROR/* 参数格式错误 */, PARAM_TRANSFORM_ERROR/* 参数转换错误 */, PARAM_HANDLE_EXCEPTION/* 参数处理异常 */, FILE_IS_NULL/* 文件为空 */, FILE_OVERSIZE/* 文件超出尺寸 */, FILE_SUFFIX_INVALID/* 非法文件后缀 */, FILE_UPLOAD_EXCEPTION/* 文件上传异常 */, MODULE_NO_PERMISSION/* 没有操作权限 */, MODULE_NO_DATA/* 没有数据 */, MODULE_DUPLICATE_DATA/* 重复数据 */, UNKNOWN/* 未知错误 */
        }

        /**
         * 将status转换成对应的数字<br />
         * 这里定义的其实就是框架的规范，自定义业务错误代码的返回从-10000开始计算。
         * 
         * @param status 状态
         * @return 对应的数字
         */
        private static int transformStatus(STATUS status) {
                switch (status) {
                        /* 操作成功 */
                        case SUCCESS:
                                return 1;
                        /* 操作失败 */
                        case ERROR:
                                return 10000;
                        /* 操作异常 */
                        case EXCEPTION:
                                return 10001;
                        /* 非法参数 */
                        case PARAM_INVALID:
                                return 20000;
                        /* 参数为空 */
                        case PARAM_IS_NULL:
                                return 20001;
                        /* 参数格式错误 */
                        case PARAM_FORMAT_ERROR:
                                return 20002;
                        /* 参数转换错误 */
                        case PARAM_TRANSFORM_ERROR:
                                return 20003;
                        /* 参数处理异常 */
                        case PARAM_HANDLE_EXCEPTION:
                                return 20004;
                        /* 文件为空 */
                        case FILE_IS_NULL:
                                return 30000;
                        /* 文件超出尺寸 */
                        case FILE_OVERSIZE:
                                return 30001;
                        /* 非法文件后缀 */
                        case FILE_SUFFIX_INVALID:
                                return 30002;
                        /* 文件上传异常 */
                        case FILE_UPLOAD_EXCEPTION:
                                return 30003;
                        /* 没有操作权限 */
                        case MODULE_NO_PERMISSION:
                                return 40000;
                        /* 没有数据 */
                        case MODULE_NO_DATA:
                                return 40001;
                        /* 重复数据 */
                        case MODULE_DUPLICATE_DATA:
                                return 40002;
                        /* 未知错误 */
                        default:
                                return -1;
                }
        }

        /**
         * 返回与status对应的文字解释
         * 
         * @return 返回值的文字解释
         */
        public static HashMap<String, String> statusMap() {
                HashMap<String, String> hm = new HashMap<String, String>();
                hm.put("1", "操作成功");
                hm.put("10000", "操作失败");
                hm.put("10001", "操作异常");
                hm.put("20000", "非法参数");
                hm.put("20001", "参数为空");
                hm.put("20002", "参数格式错误");
                hm.put("20003", "参数转换错误");
                hm.put("20004", "参数处理异常");
                hm.put("30000", "文件为空");
                hm.put("30001", "文件超出尺寸");
                hm.put("30002", "非法文件后缀");
                hm.put("30003", "文件上传异常");
                hm.put("40000", "没有操作权限");
                hm.put("40001", "没有数据");
                hm.put("40002", "重复数据");
                hm.put("-1", "未知错误");
                return hm;
        }

        /**
         * 向客户端发送消息
         * 
         * @param request HttpServletRequest对象
         * @param response HttpServletResponse对象
         * @param status 状态
         * @param count 数量（如果为null，则不体现）
         * @param result 结果（如果为null，则不体现）
         */
        public static void send(HttpServletRequest request, HttpServletResponse response, STATUS status, Integer count, String result) {
                try {
                        JSONObject o = new JSONObject();
                        o.put("status", transformStatus(status));
                        if (null != count)
                                o.put("count", count);
                        if (null != result)
                                o.put("result", result);
                        /*
                         * 接收参数如果包括callback参数（jsonp请求），需要特殊处理。
                         */
                        String callback = request.getParameter("callback");
                        if ((null != callback) && (callback.length() > 0)) {
                                Message.responseToClient(response, callback + "(" + o.toString() + ")");
                        } else {
                                Message.responseToClient(response, o.toString());
                        }
                } catch (Exception e) {
                        Framework.LOG.error(e.toString());
                }
        }

        /**
         * 向HttpServlet的客户端输出
         * 
         * @param response 待输出的response
         * @param msg 待输出的字符串数据
         */
        public static void responseToClient(HttpServletResponse response, String msg) throws Exception {
                PrintWriter pw = null;
                try {
                        pw = response.getWriter();
                        pw.write(msg);
                } finally {
                        pw.flush();
                        pw.close();
                }
        }
}