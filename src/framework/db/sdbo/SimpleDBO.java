package framework.db.sdbo;

import java.util.List;
import java.lang.reflect.Method;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import framework.sdk.Message;
import framework.sdk.DbHandler;
import framework.sdk.Framework;
import library.database.DatabaseKit;
import org.json.JSONArray;
import org.json.JSONObject;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.ibatis.session.SqlSession;

/**
 * 基于mybatis的简单数据库操作
 */
public class SimpleDBO extends DbHandler {
        /*
         * 操作标记
         */
        public static final String SDBO_TYPE_INSERT = "insert";
        public static final String SDBO_TYPE_DELETE = "delete";
        public static final String SDBO_TYPE_UPDATE = "update";
        public static final String SDBO_TYPE_SELECT = "select";
        public static final String SDBO_TYPE_TRANSACTION = "transaction";

        /*
         * 服务端的HttpServlet
         */
        private HttpServlet httpServlet;

        /*
         * 模块的名称
         */
        private String moduleName;

        /*
         * mybatis配置中的namespace
         */
        private String namespace;

        /*
         * mybatis配置中的参数对象
         */
        private HashMap<String, Object> parameter;

        /*
         * 向客户端发送数据的HttpServletResponse
         */
        private HttpServletRequest request;

        /*
         * 向客户端发送数据的HttpServletResponse
         */
        private HttpServletResponse response;

        /**
         * 生成对象的构造函数
         */
        public SimpleDBO() {
                super();
        }

        /**
         * （配置）生成对象的构造函数
         * 
         * @param namespace （配置）mybatis配置中的namespace
         * @param parameter （配置）mybatis配置中的参数对象
         */
        public SimpleDBO(HttpServlet httpServlet, String moduleName, String namespace, HashMap<String, Object> parameter, HttpServletRequest request, HttpServletResponse response) {
                this.httpServlet = httpServlet;
                this.moduleName = moduleName;
                this.namespace = namespace;
                this.parameter = parameter;
                this.request = request;
                this.response = response;
        }

        /**
         * 配置操作中，检查配置的操作类型是否是SimpleDBO中的操作
         * 
         * @param type 操作类型
         * @return 如果是SDBO中的操作，返回true； 反之，返回false。
         */
        public static boolean isValidType(String type) {
                if (true == type.equalsIgnoreCase(SimpleDBO.SDBO_TYPE_INSERT)) {
                        return true;
                }
                if (true == type.equalsIgnoreCase(SimpleDBO.SDBO_TYPE_DELETE)) {
                        return true;
                }
                if (true == type.equalsIgnoreCase(SimpleDBO.SDBO_TYPE_UPDATE)) {
                        return true;
                }
                if (true == type.equalsIgnoreCase(SimpleDBO.SDBO_TYPE_SELECT)) {
                        return true;
                }
                if (true == type.equalsIgnoreCase(SimpleDBO.SDBO_TYPE_TRANSACTION)) {
                        return true;
                }
                return false;
        }

        /**
         * （配置）insert操作（配置操作无法实现事务操作）
         * 
         * @return 操作成功，向客户端输出字符串1<br />
         *         操作失败，向客户端输出字符串0<br />
         *         发生异常，向客户端输出字符串-1
         */
        public void insert() {
                SqlSession s = DbFactory.getSqlSession(true);
                try {
                        String str = null;
                        if (this.namespace.startsWith("@")) {
                                str = this.namespace.substring(1);
                        } else {
                                str = this.moduleName + "." + this.namespace;
                        }
                        int res = s.insert(str, this.parameter);
                        if (1 <= res) {
                                Message.send(request, response, Message.STATUS.SUCCESS, null, null);
                        } else {
                                Message.send(request, response, Message.STATUS.ERROR, null, null);
                        }
                } catch (Exception e) {
                        if (Framework.DEBUG_ENABLE) {
                                Message.send(request, response, Message.STATUS.EXCEPTION, null, e.toString());
                        } else {
                                Message.send(request, response, Message.STATUS.EXCEPTION, null, null);
                        }
                        DbFactory.LOG.warn(e.toString());
                } finally {
                        s.close();
                }
        }

        /**
         * （配置）delete操作（配置操作无法实现事务操作）
         * 
         * @return 操作成功，向客户端输出字符串1<br />
         *         操作失败，向客户端输出字符串0<br />
         *         发生异常，向客户端输出字符串-1
         */
        public void delete() {
                SqlSession s = DbFactory.getSqlSession(true);
                try {
                        String str = null;
                        if (this.namespace.startsWith("@")) {
                                str = this.namespace.substring(1);
                        } else {
                                str = this.moduleName + "." + this.namespace;
                        }
                        int res = s.delete(str, this.parameter);
                        if (1 <= res) {
                                Message.send(request, response, Message.STATUS.SUCCESS, null, null);
                        } else {
                                Message.send(request, response, Message.STATUS.ERROR, null, null);
                        }
                } catch (Exception e) {
                        if (Framework.DEBUG_ENABLE) {
                                Message.send(request, response, Message.STATUS.EXCEPTION, null, e.toString());
                        } else {
                                Message.send(request, response, Message.STATUS.EXCEPTION, null, null);
                        }
                        DbFactory.LOG.warn(e.toString());
                } finally {
                        s.close();
                }
        }

        /**
         * （配置）update操作（配置操作无法实现事务操作）
         * 
         * @return 操作成功，向客户端输出字符串1<br />
         *         操作失败，向客户端输出字符串0<br />
         *         发生异常，向客户端输出字符串-1
         */
        public void update() {
                SqlSession s = DbFactory.getSqlSession(true);
                try {
                        String str = null;
                        if (this.namespace.startsWith("@")) {
                                str = this.namespace.substring(1);
                        } else {
                                str = this.moduleName + "." + this.namespace;
                        }
                        int res = s.update(str, this.parameter);
                        if (1 <= res) {
                                Message.send(request, response, Message.STATUS.SUCCESS, null, null);
                        } else {
                                Message.send(request, response, Message.STATUS.ERROR, null, null);
                        }
                } catch (Exception e) {
                        if (Framework.DEBUG_ENABLE) {
                                Message.send(request, response, Message.STATUS.EXCEPTION, null, e.toString());
                        } else {
                                Message.send(request, response, Message.STATUS.EXCEPTION, null, null);
                        }
                        DbFactory.LOG.warn(e.toString());
                } finally {
                        s.close();
                }
        }

        /**
         * （配置）select操作
         * 
         * @return 返回json对象<br />
         *         status 查询正确执行为success；查询发生异常为error。<br />
         *         count 查询影响的行数，若无查询结果则值为0。<br />
         *         rows 以列名为key结果为value的json对象。
         */
        public void select() {
                SqlSession s = DbFactory.getSqlSession(true);
                try {
                        String str = null;
                        if (this.namespace.startsWith("@")) {
                                str = this.namespace.substring(1);
                        } else {
                                str = this.moduleName + "." + this.namespace;
                        }
                        JSONArray a = new JSONArray();
                        List<HashMap<String, Object>> list = s.selectList(str, this.parameter);
                        Iterator<HashMap<String, Object>> iter = list.iterator();
                        while (iter.hasNext()) {
                                HashMap<String, Object> hm = iter.next();
                                Iterator<Entry<String, Object>> iter2 = hm.entrySet().iterator();
                                JSONObject row = new JSONObject();
                                while (iter2.hasNext()) {
                                        Entry<String, Object> e = iter2.next();
                                        row.put(e.getKey(), e.getValue());
                                }
                                a.put(row);
                        }
                        Message.send(request, response, Message.STATUS.SUCCESS, a.length(), a.toString());
                } catch (Exception e) {
                        if (Framework.DEBUG_ENABLE) {
                                Message.send(request, response, Message.STATUS.EXCEPTION, null, e.toString());
                        } else {
                                Message.send(request, response, Message.STATUS.EXCEPTION, null, null);
                        }
                        DbFactory.LOG.warn(e.toString());
                } finally {
                        s.close();
                }
        }

        /**
         * 
         * 获取事务操作中的参数
         * 
         * @param origParameter 原始参数
         * @param param 配置中要求的参数数组
         * @return 返回当前事务操作分组的参数
         */
        private HashMap<String, Object> getTransactionParameter(HashMap<String, Object> origParameter, String param[]) {
                HashMap<String, Object> hm = new HashMap<String, Object>();
                Iterator<Entry<String, Object>> iter = origParameter.entrySet().iterator();
                while (iter.hasNext()) {
                        Entry<String, Object> e = iter.next();
                        String name = e.getKey();
                        for (int i = 0; i < param.length; i++) {
                                // 拆分参数别名
                                if (-1 == param[i].indexOf("->")) {
                                        // 没有别名
                                        if (param[i].equalsIgnoreCase(name)) {
                                                hm.put(e.getKey(), e.getValue());
                                                break;
                                        }
                                } else {
                                        // 拥有别名
                                        String arr[] = param[i].split("->");
                                        if (arr[0].equalsIgnoreCase(name)) {
                                                hm.put(arr[1], e.getValue());
                                                break;
                                        }
                                }
                        }
                }
                return hm;
        }

        /**
         * 遍历查询
         * 
         * @param s SqlSession对象
         * @param namespace 查询数据的名空间
         * @param param 原始查询参数
         * @param idColumnName 标记编号名称（如：id）
         * @param pidColumnName 标记父级编号名称（如：parent_id）
         * @param al 最终返回的数据对象
         */
        private void foreachSelect(SqlSession s, String namespace, HashMap<String, Object> param, String idColumnName, String pidColumnName, ArrayList<Object> al) {
                List<HashMap<String, Object>> list = s.selectList(namespace, param);
                Iterator<HashMap<String, Object>> iter = list.iterator();
                while (iter.hasNext()) {
                        HashMap<String, Object> hm = iter.next();
                        Object id = hm.get(idColumnName);
                        HashMap<String, Object> p = new HashMap<String, Object>();
                        p.put(pidColumnName, id);
                        this.foreachSelect(s, namespace, p, idColumnName, pidColumnName, al);
                        al.add(id);
                }
        }

        /**
         * （配置）transaction操作
         * 
         * @return 操作成功，向客户端输出字符串1<br />
         *         操作失败，向客户端输出字符串0<br />
         *         发生异常，向客户端输出字符串-1
         */
        public void transaction() {
                SqlSession s = DbFactory.getSqlSession(false);
                try {
                        HashMap<String, ArrayList<Object>> dataArrayMap = new HashMap<String, ArrayList<Object>>();
                        String namespaceList[] = this.namespace.split(";");
                        for (int i = 0; i < namespaceList.length; i++) {
                                String type = namespaceList[i].split(":")[0].split(",")[0];
                                String id = namespaceList[i].split(":")[0].split(",")[1];
                                String param[] = null;
                                HashMap<String, Object> p = null;
                                if (2 == namespaceList[i].split(":").length) {
                                        // 指定参数
                                        param = namespaceList[i].split(":")[1].split(",");
                                        p = this.getTransactionParameter(this.parameter, param);
                                } else {
                                        // 所有参数
                                        p = this.parameter;
                                }
                                String str = null;
                                if (type.equalsIgnoreCase("insert")) {
                                        if (id.startsWith("@")) {
                                                str = id.substring(1);
                                        } else {
                                                str = this.moduleName + "." + id;
                                        }
                                        int res = s.insert(str, p);
                                        if (1 > res) {
                                                s.rollback();
                                                Message.send(request, response, Message.STATUS.ERROR, null, "Transaction (" + str + ") Insert Roll Back");
                                                return;
                                        }
                                } else if (type.equalsIgnoreCase("non-insert")) {
                                        if (id.startsWith("@")) {
                                                str = id.substring(1);
                                        } else {
                                                str = this.moduleName + "." + id;
                                        }
                                        int res = s.insert(str, p);
                                        if (1 <= res) {
                                                s.rollback();
                                                Message.send(request, response, Message.STATUS.ERROR, null, "Transaction (" + str + ") Non-Insert Roll Back");
                                                return;
                                        }
                                } else if (type.equalsIgnoreCase("insist-insert")) {
                                        if (id.startsWith("@")) {
                                                str = id.substring(1);
                                        } else {
                                                str = this.moduleName + "." + id;
                                        }
                                        s.insert(str, p);
                                } else if (type.equalsIgnoreCase("delete")) {
                                        if (id.startsWith("@")) {
                                                str = id.substring(1);
                                        } else {
                                                str = this.moduleName + "." + id;
                                        }
                                        int res = s.delete(str, p);
                                        if (1 > res) {
                                                s.rollback();
                                                Message.send(request, response, Message.STATUS.ERROR, null, "Transaction (" + str + ") Delete Roll Back");
                                                return;
                                        }
                                } else if (type.equalsIgnoreCase("non-delete")) {
                                        if (id.startsWith("@")) {
                                                str = id.substring(1);
                                        } else {
                                                str = this.moduleName + "." + id;
                                        }
                                        int res = s.delete(str, p);
                                        if (1 <= res) {
                                                s.rollback();
                                                Message.send(request, response, Message.STATUS.ERROR, null, "Transaction (" + str + ") Non-Delete Roll Back");
                                                return;
                                        }
                                } else if (type.equalsIgnoreCase("insist-delete")) {
                                        if (id.startsWith("@")) {
                                                str = id.substring(1);
                                        } else {
                                                str = this.moduleName + "." + id;
                                        }
                                        s.delete(str, p);
                                } else if (type.equalsIgnoreCase("update")) {
                                        if (id.startsWith("@")) {
                                                str = id.substring(1);
                                        } else {
                                                str = this.moduleName + "." + id;
                                        }
                                        int res = s.update(str, p);
                                        if (1 > res) {
                                                s.rollback();
                                                Message.send(request, response, Message.STATUS.ERROR, null, "Transaction (" + str + ") Update Roll Back");
                                                return;
                                        }
                                } else if (type.equalsIgnoreCase("non-update")) {
                                        if (id.startsWith("@")) {
                                                str = id.substring(1);
                                        } else {
                                                str = this.moduleName + "." + id;
                                        }
                                        int res = s.update(str, p);
                                        if (1 <= res) {
                                                s.rollback();
                                                Message.send(request, response, Message.STATUS.ERROR, null, "Transaction (" + str + ") Non-Update Roll Back");
                                                return;
                                        }
                                } else if (type.equalsIgnoreCase("insist-update")) {
                                        if (id.startsWith("@")) {
                                                str = id.substring(1);
                                        } else {
                                                str = this.moduleName + "." + id;
                                        }
                                        s.update(str, p);
                                } else if (type.equalsIgnoreCase("select")) {
                                        if (id.startsWith("@")) {
                                                str = id.substring(1);
                                        } else {
                                                str = this.moduleName + "." + id;
                                        }
                                        JSONArray a = new JSONArray();
                                        List<HashMap<String, Object>> list = s.selectList(str, p);
                                        if (!DatabaseKit.hasData(list)) {
                                                if ((i + 1) == namespaceList.length) {
                                                        s.commit();
                                                        Message.send(request, response, Message.STATUS.SUCCESS, a.length(), a.toString());
                                                        return;
                                                }
                                                s.rollback();
                                                Message.send(request, response, Message.STATUS.MODULE_NO_DATA, null, "Transaction (" + str + ") Select Roll Back");
                                                return;
                                        }
                                        // 如果select是最后一个执行的namespace，那么返回搜索结果
                                        if ((i + 1) == namespaceList.length) {
                                                Iterator<HashMap<String, Object>> iter = list.iterator();
                                                while (iter.hasNext()) {
                                                        p = iter.next();
                                                        Iterator<Entry<String, Object>> iter2 = p.entrySet().iterator();
                                                        JSONObject row = new JSONObject();
                                                        while (iter2.hasNext()) {
                                                                Entry<String, Object> e = iter2.next();
                                                                row.put(e.getKey(), e.getValue());
                                                        }
                                                        a.put(row);
                                                }
                                                s.commit();
                                                Message.send(request, response, Message.STATUS.SUCCESS, a.length(), a.toString());
                                                return;
                                        }
                                } else if (type.toLowerCase().startsWith("result-select->")) {
                                        if (id.startsWith("@")) {
                                                str = id.substring(1);
                                        } else {
                                                str = this.moduleName + "." + id;
                                        }
                                        // 结果集存放的别名
                                        String aliasName = type.split("->")[1].split("\\|")[0];
                                        // 获取数据的列名
                                        String columnName = type.split("->")[1].split("\\|")[1];
                                        ArrayList<Object> al = new ArrayList<Object>();
                                        List<HashMap<String, Object>> list = s.selectList(str, p);
                                        Iterator<HashMap<String, Object>> iter = list.iterator();
                                        while (iter.hasNext()) {
                                                HashMap<String, Object> hm = iter.next();
                                                al.add(hm.get(columnName));
                                        }
                                        dataArrayMap.put(aliasName, al);
                                } else if (type.toLowerCase().startsWith("result-foreach-select->")) {
                                        if (id.startsWith("@")) {
                                                str = id.substring(1);
                                        } else {
                                                str = this.moduleName + "." + id;
                                        }
                                        // 结果集存放的别名
                                        String aliasName = type.split("->")[1].split("\\|")[0];
                                        // 遍历的列名
                                        String idColumnName = type.split("->")[1].split("\\|")[1];
                                        // 遍历的父级列名
                                        String pidColumnName = type.split("->")[1].split("\\|")[2];
                                        ArrayList<Object> al = new ArrayList<Object>();
                                        this.foreachSelect(s, str, p, idColumnName, pidColumnName, al);
                                        dataArrayMap.put(aliasName, al);
                                } else if (type.toLowerCase().startsWith("foreach-delete->")) {
                                        if (id.startsWith("@")) {
                                                str = id.substring(1);
                                        } else {
                                                str = this.moduleName + "." + id;
                                        }
                                        // 结果集存放的别名
                                        String aliasName = type.split("->")[1].split("\\|")[0];
                                        // 传入的参数名
                                        String paramName = type.split("->")[1].split("\\|")[1];
                                        ArrayList<Object> al = dataArrayMap.get(aliasName);
                                        Iterator<Object> iter = al.iterator();
                                        while (iter.hasNext()) {
                                                Object obj = iter.next();
                                                p.put(paramName, obj);
                                                int res = s.delete(str, p);
                                                if (1 > res) {
                                                        s.rollback();
                                                        Message.send(request, response, Message.STATUS.ERROR, null, "Transaction (" + str + ") Foreach Delete Roll Back");
                                                        return;
                                                }
                                        }
                                } else if (type.toLowerCase().startsWith("foreach-insist-delete->")) {
                                        if (id.startsWith("@")) {
                                                str = id.substring(1);
                                        } else {
                                                str = this.moduleName + "." + id;
                                        }
                                        // 结果集存放的别名
                                        String aliasName = type.split("->")[1].split("\\|")[0];
                                        // 传入的参数名
                                        String paramName = type.split("->")[1].split("\\|")[1];
                                        ArrayList<Object> al = dataArrayMap.get(aliasName);
                                        Iterator<Object> iter = al.iterator();
                                        while (iter.hasNext()) {
                                                Object obj = iter.next();
                                                p.put(paramName, obj);
                                                s.delete(str, p);
                                        }
                                } else if (type.toLowerCase().startsWith("foreach-update->")) {
                                        if (id.startsWith("@")) {
                                                str = id.substring(1);
                                        } else {
                                                str = this.moduleName + "." + id;
                                        }
                                        // 结果集存放的别名
                                        String aliasName = type.split("->")[1].split("\\|")[0];
                                        // 传入的参数名
                                        String paramName = type.split("->")[1].split("\\|")[1];
                                        ArrayList<Object> al = dataArrayMap.get(aliasName);
                                        Iterator<Object> iter = al.iterator();
                                        while (iter.hasNext()) {
                                                Object obj = iter.next();
                                                p.put(paramName, obj);
                                                int res = s.update(str, p);
                                                if (1 > res) {
                                                        s.rollback();
                                                        Message.send(request, response, Message.STATUS.ERROR, null, "Transaction (" + str + ") Foreach Update Roll Back");
                                                        return;
                                                }
                                        }
                                } else if (type.toLowerCase().startsWith("foreach-insist-update->")) {
                                        if (id.startsWith("@")) {
                                                str = id.substring(1);
                                        } else {
                                                str = this.moduleName + "." + id;
                                        }
                                        // 结果集存放的别名
                                        String aliasName = type.split("->")[1].split("\\|")[0];
                                        // 传入的参数名
                                        String paramName = type.split("->")[1].split("\\|")[1];
                                        ArrayList<Object> al = dataArrayMap.get(aliasName);
                                        Iterator<Object> iter = al.iterator();
                                        while (iter.hasNext()) {
                                                Object obj = iter.next();
                                                p.put(paramName, obj);
                                                s.update(str, p);
                                        }
                                } else if (type.toLowerCase().startsWith("foreach-select->")) {
                                        if (id.startsWith("@")) {
                                                str = id.substring(1);
                                        } else {
                                                str = this.moduleName + "." + id;
                                        }
                                        // 结果集存放的别名
                                        String aliasName = type.split("->")[1].split("\\|")[0];
                                        // 传入的参数名
                                        String paramName = type.split("->")[1].split("\\|")[1];
                                        // 获取数据的列名
                                        String columnName = null;
                                        // 新结果集存放的别名
                                        String newAliasName = null;
                                        if (2 < type.split("->")[1].split("\\|").length) {
                                                columnName = type.split("->")[1].split("\\|")[2];
                                                newAliasName = type.split("->")[1].split("\\|")[3];
                                        }
                                        ArrayList<Object> al = dataArrayMap.get(aliasName);
                                        ArrayList<Object> newAl = new ArrayList<Object>();
                                        Iterator<Object> iter = al.iterator();
                                        JSONArray a = new JSONArray();
                                        while (iter.hasNext()) {
                                                Object obj = iter.next();
                                                p.put(paramName, obj);
                                                List<HashMap<String, Object>> list = s.selectList(str, p);
                                                Iterator<HashMap<String, Object>> secIter = list.iterator();
                                                while (secIter.hasNext()) {
                                                        HashMap<String, Object> hm = secIter.next();
                                                        if (null == columnName) {
                                                                // 如果列名为空，那么添加搜索结果，稍后直接返回。
                                                                Iterator<Entry<String, Object>> hmIter = hm.entrySet().iterator();
                                                                JSONObject row = new JSONObject();
                                                                while (hmIter.hasNext()) {
                                                                        Entry<String, Object> e = hmIter.next();
                                                                        row.put(e.getKey(), e.getValue());
                                                                }
                                                                a.put(row);
                                                        } else {
                                                                newAl.add(hm.get(columnName));
                                                        }
                                                }
                                        }
                                        if (null == columnName) {
                                                s.commit();
                                                Message.send(request, response, Message.STATUS.SUCCESS, a.length(), a.toString());
                                                return;
                                        } else {
                                                dataArrayMap.put(newAliasName, newAl);
                                        }
                                } else if (type.toLowerCase().startsWith("foreach-non-select->")) {
                                        if (id.startsWith("@")) {
                                                str = id.substring(1);
                                        } else {
                                                str = this.moduleName + "." + id;
                                        }
                                        // 结果集存放的别名
                                        String aliasName = type.split("->")[1].split("\\|")[0];
                                        // 传入的参数名
                                        String paramName = type.split("->")[1].split("\\|")[1];
                                        ArrayList<Object> al = dataArrayMap.get(aliasName);
                                        Iterator<Object> iter = al.iterator();
                                        while (iter.hasNext()) {
                                                Object obj = iter.next();
                                                p.put(paramName, obj);
                                                List<HashMap<String, Object>> list = s.selectList(str, p);
                                                if (DatabaseKit.hasData(list)) {
                                                        s.rollback();
                                                        Message.send(request, response, Message.STATUS.MODULE_NO_DATA, null, "Transaction (" + str + ") Foreach Non-Select Roll Back");
                                                        return;
                                                }
                                        }
                                } else if (type.toLowerCase().startsWith("foreach-custom->")) {
                                        Class<?> businessClass = null;
                                        Method classMethod = null;
                                        String classNameArr[] = null;
                                        String packageName = null;
                                        String className = null;
                                        String methodName = null;
                                        if (id.startsWith("@")) {
                                                // 可以指定自定义类（格式为：包名.类名.方法名）
                                                classNameArr = id.substring(1).split("\\.");
                                                packageName = classNameArr[0];
                                                className = classNameArr[1];
                                                methodName = classNameArr[2];
                                        } else {
                                                // 默认为当前模块名称
                                                packageName = this.moduleName;
                                                // 默认为Custom
                                                className = "Custom";
                                                // 默认为id
                                                methodName = id;
                                        }
                                        // 结果集存放的别名
                                        String aliasName = type.split("->")[1].split("\\|")[0];
                                        // 传入的参数名
                                        String paramName = type.split("->")[1].split("\\|")[1];
                                        ArrayList<Object> al = dataArrayMap.get(aliasName);
                                        Iterator<Object> iter = al.iterator();
                                        while (iter.hasNext()) {
                                                Object obj = iter.next();
                                                p.put(paramName, obj);
                                                Object[] params = { this.httpServlet, this.request, this.response, s, p };
                                                Class<?>[] paramsType = { HttpServlet.class, HttpServletRequest.class, HttpServletResponse.class, SqlSession.class, HashMap.class };
                                                try {
                                                        businessClass = Class.forName(packageName + "." + className);
                                                        classMethod = businessClass.getMethod(methodName);
                                                        Constructor<?> c = businessClass.getConstructor(paramsType);
                                                        Object o = c.newInstance(params);
                                                        Integer res = (Integer) classMethod.invoke(o);
                                                        if (1 != res.intValue()) {
                                                                s.rollback();
                                                                Message.send(request, response, Message.STATUS.ERROR, null, "Transaction " + packageName + "." + className + "." + methodName + " Foreach Custom Roll Back");
                                                                return;
                                                        }
                                                } catch (Exception e) {
                                                        s.rollback();
                                                        if (Framework.DEBUG_ENABLE) {
                                                                Message.send(request, response, Message.STATUS.EXCEPTION, null, e.toString());
                                                        } else {
                                                                Message.send(request, response, Message.STATUS.EXCEPTION, null, null);
                                                        }
                                                        DbFactory.LOG.warn(e.toString());
                                                        return;
                                                }
                                        }
                                } else if (type.equalsIgnoreCase("non-select")) {
                                        if (id.startsWith("@")) {
                                                str = id.substring(1);
                                        } else {
                                                str = this.moduleName + "." + id;
                                        }
                                        List<HashMap<String, Object>> list = s.selectList(str, p);
                                        if (DatabaseKit.hasData(list)) {
                                                s.rollback();
                                                Message.send(request, response, Message.STATUS.MODULE_NO_DATA, null, "Transaction (" + str + ") Non-Select Roll Back");
                                                return;
                                        }
                                } else if (type.equalsIgnoreCase("custom")) {
                                        Object[] params = { this.httpServlet, this.request, this.response, s, p };
                                        Class<?>[] paramsType = { HttpServlet.class, HttpServletRequest.class, HttpServletResponse.class, SqlSession.class, HashMap.class };
                                        try {
                                                Class<?> businessClass = null;
                                                Method classMethod = null;
                                                String classNameArr[] = null;
                                                String packageName = null;
                                                String className = null;
                                                String methodName = null;
                                                if (id.startsWith("@")) {
                                                        // 可以指定自定义类（格式为：包名.类名.方法名）
                                                        classNameArr = id.substring(1).split("\\.");
                                                        packageName = classNameArr[0];
                                                        className = classNameArr[1];
                                                        methodName = classNameArr[2];
                                                } else {
                                                        // 默认为当前模块名称
                                                        packageName = this.moduleName;
                                                        // 默认为Custom
                                                        className = "Custom";
                                                        // 默认为id
                                                        methodName = id;
                                                }
                                                businessClass = Class.forName(packageName + "." + className);
                                                classMethod = businessClass.getMethod(methodName);
                                                Constructor<?> c = businessClass.getConstructor(paramsType);
                                                Object o = c.newInstance(params);
                                                Integer res = (Integer) classMethod.invoke(o);
                                                if (1 != res.intValue()) {
                                                        s.rollback();
                                                        Message.send(request, response, Message.STATUS.ERROR, null, "Transaction " + packageName + "." + className + "." + methodName + " Roll Back");
                                                        return;
                                                }
                                        } catch (Exception e) {
                                                s.rollback();
                                                if (Framework.DEBUG_ENABLE) {
                                                        Message.send(request, response, Message.STATUS.EXCEPTION, null, e.toString());
                                                } else {
                                                        Message.send(request, response, Message.STATUS.EXCEPTION, null, null);
                                                }
                                                DbFactory.LOG.warn(e.toString());
                                                return;
                                        }
                                } else {
                                        s.rollback();
                                        Message.send(request, response, Message.STATUS.ERROR, null, "Transaction Type Error");
                                        return;
                                }
                        }
                        s.commit();
                        Message.send(request, response, Message.STATUS.SUCCESS, null, null);
                } catch (Exception e) {
                        s.rollback();
                        if (Framework.DEBUG_ENABLE) {
                                Message.send(request, response, Message.STATUS.EXCEPTION, null, e.toString());
                        } else {
                                Message.send(request, response, Message.STATUS.EXCEPTION, null, null);
                        }
                        DbFactory.LOG.warn(e.toString());
                } finally {
                        s.close();
                }
        }

        @Override
        public SqlSession getSqlSession(boolean autoCommit) {
                return DbFactory.getSqlSession(autoCommit);
        }
}