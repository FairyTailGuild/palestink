package framework.sdk;

import java.io.File;
import java.util.ArrayList;

public class Framework {
        /*
         * 项目真实路径
         */
        public static String PROJECT_REAL_PATH = "";

        /*
         * 模块名称的列表
         */
        public static ArrayList<String> MODULE_NAME_LIST;

        /*
         * 声明全局日志对象
         */
        public static Log LOG = null;

        /*
         * 账号角色的session编号
         */
        public static final String USER_UUID = "user_uuid";

        /*
         * 账号角色的session名称
         */
        public static final String USER_ROLE = "user_role";

        /*
         * 调试模式（默认为关闭）
         */
        public static boolean DEBUG_ENABLE = false;

        /**
         * 初始化日志对象
         */
        public static void init(String path) {
                File f = new File(path);
                if (!f.exists()) {
                        f.mkdirs();
                }
        }
}