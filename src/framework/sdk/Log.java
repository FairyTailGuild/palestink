package framework.sdk;

/**
 * 调用Demo<br />
 * 1、配置Log的信息（包括：LOG_FILE_OUTPUT_PATH、LOG_FILE_NAME、LOG_FILE_PACKAGE_SIZE），若不配置使用默认值。<br />
 * 2、声明对象Log log = new Log4j();。
 * 3、直接通过log对象调用方法。
 */

/**
 * 日志的抽象类
 */
public abstract class Log {
        /**
         * 日志对象初始化
         */
        public abstract int init();

        /**
         * 日志对象释放资源
         */
        public abstract void releaseLogResource();

        /**
         * 日志消息输出（debug级别）
         * 
         * @param msg 消息内容
         */
        public abstract void debug(String msg);

        /**
         * 日志消息输出（info级别）
         * 
         * @param msg 消息内容
         */
        public abstract void info(String msg);

        /**
         * 日志消息输出（warn级别）
         * 
         * @param msg 消息内容
         */
        public abstract void warn(String msg);

        /**
         * 日志消息输出（error级别）
         * 
         * @param msg 消息内容
         */
        public abstract void error(String msg);

        /**
         * 日志消息输出（fatal级别）
         * 
         * @param msg 消息内容
         */
        public abstract void fatal(String msg);
}