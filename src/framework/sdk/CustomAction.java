package framework.sdk;

import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.ibatis.session.SqlSession;

public abstract class CustomAction {
        public CustomAction(HttpServlet httpServlet, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, DbHandler dbHandler, HashMap<String, Object> parameter) {
        }

        public CustomAction(HttpServlet httpServlet, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, SqlSession sqlSession, HashMap<String, Object> parameter) {
        }
}