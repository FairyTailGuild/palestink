package framework.db.sdbo;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Driver;
import java.sql.DriverManager;
import java.util.Enumeration;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.mysql.jdbc.AbandonedConnectionCleanupThread;

import framework.sdk.Log;

/*
 * 调用Demo
 * 1、配置DbFactory的日志对象LOG，否则无法打印相关信息。
 * 2、配置mybatis数据库配置文件的路径MYBATIS_DB_CONFIG_FILE_PATH。
 * 3、按照SimpleDBO的构造函数生成对象调用即可。
 */

/**
 * 基于mybatis的数据库工厂
 */
public class DbFactory {
        /*
         * 数据库需的日志对象（必须）
         */
        public static Log LOG = null;

        /*
         * mybatis的数据库配置文件路径（必须）
         */
        public static String MYBATIS_DB_CONFIG_FILE_PATH = null;

        /*
         * 全局单例数据库工厂对象
         */
        private static SqlSessionFactory SQL_SESSION_FACTORY;

        /**
         * 初始化操作
         * 
         * @param LOG 日志对象
         * @param path 数据库mybatis的配置文件
         */
        public static void init(Log LOG, String path) {
                DbFactory.LOG = LOG;
                DbFactory.MYBATIS_DB_CONFIG_FILE_PATH = path;
                FileInputStream fis = null;
                try {
                        fis = new FileInputStream(new File(MYBATIS_DB_CONFIG_FILE_PATH));
                        SQL_SESSION_FACTORY = new SqlSessionFactoryBuilder().build(fis);
                } catch (Exception e) {
                        LOG.fatal(e.toString());
                } finally {
                        try {
                                fis.close();
                        } catch (Exception e) {
                                LOG.fatal(e.toString());
                        }
                }
        }

        /**
         * 释放数据库资源，包括：加载的驱动、连接的线程。
         */
        public static void releaseResource() {
                try {
                        Enumeration<Driver> drivers = DriverManager.getDrivers();
                        while (drivers.hasMoreElements()) {
                                Driver driver = drivers.nextElement();
                                DriverManager.deregisterDriver(driver);
                        }
                        // 注意，这里是mysql专用的shutdown方法，如果更换数据库，需要做相应修改。
                        AbandonedConnectionCleanupThread.shutdown();
                } catch (Exception e) {
                        LOG.warn(e.toString());
                }
        }

        /**
         * 获取数据库工厂对象
         * 
         * @param autoCommit 如果为true，直接提交不开启事务；如果为false则开启事务，需要执行commit操作才能提交数据。
         * @return 数据库工厂对象
         */
        public static SqlSession getSqlSession(boolean autoCommit) {
                return SQL_SESSION_FACTORY.openSession(autoCommit);
        }
}