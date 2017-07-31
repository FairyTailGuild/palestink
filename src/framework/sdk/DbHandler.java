package framework.sdk;

import org.apache.ibatis.session.SqlSession;

public abstract class DbHandler {
        /**
         * 获取SqlSession
         * 
         * @param autoCommit 如果为true，直接提交不开启事务；如果为false则开启事务，需要执行commit操作才能提交数据。
         * @return 数据库工厂对象
         */
        public abstract SqlSession getSqlSession(boolean autoCommit);
}
