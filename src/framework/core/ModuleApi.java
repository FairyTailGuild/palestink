package framework.core;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Iterator;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.dom4j.Element;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import framework.sdk.Message;
import library.io.InputOutput;

@SuppressWarnings("serial")
public class ModuleApi extends HttpServlet {
        /**
         * 模块的api路径
         */
        private String apiPath;

        /**
         * 构造函数
         */
        public ModuleApi() {
        }

        /**
         * 获取全局api内容
         * 
         * @return xml格式的全局api内容
         */
        private static String getGlobalApi() {
                Document doc = DocumentHelper.createDocument();
                doc.addComment("This Config File Made By PALESTINK Framework");
                Element config = doc.addElement("config");
                Element global = config.addElement("global");
                Iterator<Entry<String, String>> iter = Message.statusMap().entrySet().iterator();
                while (iter.hasNext()) {
                        Map.Entry<String, String> e = (Map.Entry<String, String>) iter.next();
                        Element returnValue = global.addElement("returnValue");
                        returnValue.addAttribute("code", e.getKey());
                        returnValue.addAttribute("description", e.getValue());
                }
                return doc.asXML();
        }

        /**
         * 获取模块的api
         */
        private String getModuleApi(String path) {
                try {
                        StringBuilder sb = InputOutput.simpleStringBuilderReadFile(path);
                        if (null == sb) {
                                throw new Exception("Can't Not Found Module Api File (" + path + ")");
                        }
                        return sb.toString();
                } catch (Exception e) {
                        return e.toString();
                }
        }

        /**
         * 初始化<br />
         * 从参数读取配置文件信息
         */
        @Override
        public void init() throws ServletException {
                super.init();
                this.apiPath = this.getInitParameter("path");
        }

        /**
         * 方便测试
         */
        @Override
        public void doGet(HttpServletRequest request, HttpServletResponse response) {
                doPost(request, response);
        }

        /**
         * doPost
         */
        @Override
        public void doPost(HttpServletRequest request, HttpServletResponse response) {
                String global = request.getParameter("global");
                try {
                        if ((null != global) && (global.equalsIgnoreCase("true"))) {
                                Message.responseToClient(response, ModuleApi.getGlobalApi());
                        } else {
                                Message.responseToClient(response, this.getModuleApi(this.apiPath));
                        }
                } catch (Exception e) {
                        Message.send(request, response, Message.STATUS.EXCEPTION, null, e.toString());
                }
        }
}