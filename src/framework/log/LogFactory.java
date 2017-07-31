package framework.log;

import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.ByteArrayInputStream;
import java.io.BufferedOutputStream;
import java.util.LinkedList;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import framework.sdk.Log;
import library.io.InputOutput;
import library.string.CharacterString;

class CompressLogThread extends Thread {
        private String logFileName;
        private String zipFilePath;
        private LinkedList<String> logMessageList;
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;

        public CompressLogThread(LinkedList<String> logMessageList, String logFileName, String zipFilePath) {
                this.logMessageList = logMessageList;
                this.logFileName = logFileName;
                this.zipFilePath = zipFilePath;
        }

        @Override
        public void run() {
                StringBuilder sb = null;
                ByteArrayInputStream bais = null;
                try {
                        sb = new StringBuilder();
                        for (int i = 0; i < this.logMessageList.size(); i++) {
                                sb.append(this.logMessageList.get(i));
                        }
                        // 压缩zip
                        bais = new ByteArrayInputStream(sb.toString().getBytes("utf-8"));
                        InputOutput.compressDataToZipFile(bais, this.logFileName, this.zipFilePath);
                } catch (Exception e) {
                        System.out.println(e.toString());
                } finally {
                        try {
                                if (null != bais) {
                                        bais.close();
                                }
                        } catch (Exception e) {
                                System.out.println(e.toString());
                        }
                }
        }
}

public class LogFactory extends Log {
        // 日志输出线程的最大数量
        private final int threadPoolMaxNum = 10;

        // 日志输出线程池（固定）
        private ExecutorService logThreadPool;

        // 当前日志文件尺寸
        private long currentLogFileSize;

        // 日志文件名
        private String logFileName;

        // 日志输出路径
        private String logOutputPath;

        // 日志文件压缩成zip的尺寸（单位：MB）
        private int logZipSize;

        // 日志File
        private File logFile;

        // 日志消息列表
        private LinkedList<String> logMessageList;

        // 文件读取对象
        FileReader fr = null;

        // 文件读取缓存对象
        BufferedReader br = null;

        // 文件输出流
        FileOutputStream fos = null;

        // 文件缓存输出流
        BufferedOutputStream bos = null;

        // 日志消息输出格式
        private final String msgFormat = "[%s]-[%s]%s.%s (%s:%d) -> %s";

        public LogFactory(String logFileName, String logOutputPath, int logZipSize) {
                this.logFileName = logFileName;
                this.logOutputPath = logOutputPath;
                this.logZipSize = logZipSize;
        }

        /**
         * 初始化函数
         * 
         * @return 1: 初始化成功<br />
         *         -1: 日志文件错误<br />
         *         0: 发生异常<br />
         */
        @Override
        public int init() {
                try {
                        if (null == this.logOutputPath) {
                                return 1;
                        }
                        // 初始化线程池
                        this.logThreadPool = Executors.newFixedThreadPool(threadPoolMaxNum);
                        // 初始化消息列表
                        this.logMessageList = new LinkedList<String>();
                        // 初始化日志File
                        this.logFile = new File(this.logOutputPath + this.logFileName);
                        if (!this.logFile.exists()) {
                                this.currentLogFileSize = 0L;
                                this.logFile.createNewFile();
                        } else {
                                if (!this.logFile.isFile()) {
                                        return -1;
                                }
                                this.currentLogFileSize = this.logFile.length();
                        }
                        this.fr = new FileReader(this.logFile);
                        this.br = new BufferedReader(this.fr);
                        String oldRecord = "";
                        while (null != (oldRecord = this.br.readLine())) {
                                oldRecord += System.getProperty("line.separator");
                                this.logMessageList.add(oldRecord);
                        }
                        this.fos = new FileOutputStream(this.logFile);
                        this.bos = new BufferedOutputStream(fos);
                        for (int i = 0; i < this.logMessageList.size(); i++) {
                                this.bos.write(this.logMessageList.get(i).getBytes("utf-8"));
                        }
                        return 1;
                } catch (Exception e) {
                        System.out.println(e.toString());
                        return 0;
                } finally {
                        try {
                                if (null != this.br) {
                                        this.br.close();
                                }
                                if (null != this.fr) {
                                        this.fr.close();
                                }
                        } catch (Exception e) {
                                System.out.println(e.toString());
                                return 0;
                        }
                }
        }

        @Override
        public void releaseLogResource() {
                try {
                        if (null != this.bos) {
                                this.bos.close();
                        }
                        if (null != this.fos) {
                                this.fos.close();
                        }
                } catch (Exception e) {
                        System.out.println(e.toString());
                }
        }

        /**
         * 获取日志消息<br />
         * 这里自定义了一个“调用层级”<br />
         * 调用的第一层是“getLogMsg”<br />
         * 调用的第二层是“调用getLogMsg的方法”<br />
         * 调用的第三层是“调用Log4j对象输出信息的地方”<br />
         * 所以这里的index设置为2（数组从0开始计算）
         * 
         * @param msg 消息内容
         * @return
         */
        private String getLogMsg(String level, String msg) {
                StackTraceElement ste[] = (new Throwable()).getStackTrace();
                int index = 0;
                if (3 < ste.length) {
                        index = 3;
                }
                StackTraceElement s = ste[index];
                String r = String.format(msgFormat, level, CharacterString.getCurrentFormatDateTime("yyyy-MM-dd HH:mm:ss:SSS"), s.getClassName(), s.getMethodName(), s.getFileName(), s.getLineNumber(), msg);
                return r;
        }

        @SuppressWarnings("unchecked")
        private void appendToLog(String level, String msg) {
                try {
                        String content = this.getLogMsg(level, msg) + System.getProperty("line.separator");
                        System.out.print(content);
                        if (null == this.logOutputPath) {
                                return;
                        }
                        this.logMessageList.add(content);
                        this.currentLogFileSize += content.getBytes().length;
                        // 判断是否超过log文件尺寸限制
                        if ((this.logZipSize * 1024 * 1024) <= this.currentLogFileSize) {
                                String currentTimestamp = CharacterString.getCurrentFormatDateTime("yyyyMMddHHmmssSSS");
                                String zipFilePath = logOutputPath + logFileName.split("\\.")[0] + "_" + currentTimestamp + "." + "zip";
                                CompressLogThread clt = new CompressLogThread((LinkedList<String>) this.logMessageList.clone(), this.logFileName, zipFilePath);
                                this.logMessageList.clear();
                                this.logThreadPool.execute(clt);
                                // 关闭原有文件流
                                if (null != this.bos) {
                                        this.bos.close();
                                }
                                if (null != this.fos) {
                                        this.fos.close();
                                }
                                // 日志文件大小置0
                                this.currentLogFileSize = 0;
                                // 重新生成文件流对象
                                this.fos = new FileOutputStream(this.logFile);
                                this.bos = new BufferedOutputStream(fos);
                        }
                        this.bos.write(content.getBytes("utf-8"));
                        this.bos.flush();
                } catch (Exception e) {
                        System.out.println(e.toString());
                }
        }

        @Override
        public void debug(String msg) {
                this.appendToLog("debug", msg);
        }

        @Override
        public void info(String msg) {
                this.appendToLog("info", msg);
        }

        @Override
        public void warn(String msg) {
                this.appendToLog("warn", msg);
        }

        @Override
        public void error(String msg) {
                this.appendToLog("error", msg);
        }

        @Override
        public void fatal(String msg) {
                this.appendToLog("fatal", msg);
        }
}