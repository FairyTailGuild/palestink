package framework.sdk;

import java.util.Iterator;
import java.util.ArrayList;
import javax.servlet.ServletContext;

public abstract class DaemonAction {
        /**
         * 构造函数
         * 
         * @param servletContext Servlet环境
         * @param dbHandler 数据库操作句柄
         */
        public DaemonAction(ServletContext servletContext, DbHandler dbHandler) {
        }

        public static ArrayList<Thread> DAEMON_THREAD_LIST = new ArrayList<Thread>();

        /**
         * 释放守护线程资源（注意，守护进程的一般是for无线循环sleep的线程，若要结束需要调用线程的interrupt方法）
         */
        public static void releaseDaemonThreadResource() {
                Iterator<Thread> iter = DaemonAction.DAEMON_THREAD_LIST.iterator();
                while (iter.hasNext()) {
                        Thread t = iter.next();
                        t.interrupt();
                }
        }

        /**
         * 初始化完成后调用的执行函数
         */
        public abstract void run();
}