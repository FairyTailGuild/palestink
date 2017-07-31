package toolkit.sjdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.UUID;
import org.json.JSONArray;
import org.json.JSONObject;
import library.encrypt.Md5;

/*
 * Simple Jdbc Test
 */
public class SJT {
        private static final String DRV = "com.mysql.jdbc.Driver";
        // private static final String URL = "jdbc:mysql://127.0.0.1:3306/xiaovl?useUnicode=true&characterEncoding=utf-8&useSSL=true";
        private static final String URL = "jdbc:mysql://127.0.0.1:3306/mtest?useUnicode=true&characterEncoding=utf-8&useSSL=true";
        private static final String USER = "root";
        private static final String PWD = "5695234";

        public SJT() {
                try {
                        Class.forName(DRV);
                } catch (ClassNotFoundException e) {
                        System.out.println(e.toString());
                }
        }

        public int executeSimpleSql(String sql) {
                Connection c = null;
                PreparedStatement p = null;
                try {
                        c = DriverManager.getConnection(URL, USER, PWD);
                        p = c.prepareStatement(sql);
                        return p.executeUpdate();
                } catch (Exception e) {
                        System.out.println(e.toString());
                        return -1;
                } finally {
                        try {
                                p.close();
                                c.close();
                        } catch (Exception e) {
                                System.out.println(e.toString());
                        }
                }
        }

        // 自定义A
        // public void exeTest() {
        // Connection c = null;
        // PreparedStatement p = null;
        // PreparedStatement p2 = null;
        // try {
        // c = DriverManager.getConnection(URL, USER, PWD);
        // p = c.prepareStatement("SELECT id, name, sex, mobile FROM test");
        // ResultSet r = p.executeQuery();
        // int i = 0;
        // while (r.next()) {
        // p2 = c.prepareStatement("insert into test3 (id, content) values (?, ?)");
        // p2.setInt(1, r.getInt(1));
        // JSONObject jo = new JSONObject();
        // jo.put("name", r.getString(2));
        // jo.put("sex", r.getString(3));
        // jo.put("mobile", r.getString(4));
        // // String json = "{'name':'" + r.getString(2) + "','sex':'" + r.getString(3) + "', 'mobile':'" + r.getString(4) + "'}";
        // String json = jo.toString();
        // // System.out.println(json);
        // p2.setString(2, json);
        // if (1 != p2.executeUpdate()) {
        // System.out.println("插入出错");
        // break;
        // }
        // i++;
        // System.out.println("已经执行了" + i + "条语句");
        // }
        // System.out.println("执行结束");
        // } catch (Exception e) {
        // System.out.println(e.toString());
        // } finally {
        // try {
        // p.close();
        // c.close();
        // } catch (Exception e) {
        // System.out.println(e.toString());
        // }
        // }
        // }

        // 自定义B
        public void exeTest() {
                Connection c = null;
                PreparedStatement p = null;
                String jsonStr = "[{\"id\":\"web_app_icon\", \"name\":\"UI/UX\", \"col\":1, \"nav_link\":\"/favorite/web_app_icon/\"}, {\"id\":\"design\", \"name\":\"平面\", \"col\":1, \"nav_link\":\"/favorite/design/\"}, {\"id\":\"illustration\", \"name\":\"插画/漫画\", \"col\":1, \"nav_link\":\"/favorite/illustration/\"}, {\"id\":\"home\", \"name\":\"家居/家装\", \"col\":1, \"nav_link\":\"/favorite/home/\"}, {\"id\":\"apparel\", \"name\":\"女装/搭配\", \"col\":1, \"nav_link\":\"/favorite/apparel/\"}, {\"id\":\"men\", \"name\":\"男士/风尚\", \"col\":2, \"nav_link\":\"/favorite/men/\"}, {\"id\":\"wedding_events\", \"name\":\"婚礼\", \"col\":2, \"nav_link\":\"/favorite/wedding_events/\"}, {\"id\":\"industrial_design\", \"name\":\"工业设计\", \"col\":2, \"nav_link\":\"/favorite/industrial_design/\"}, {\"id\":\"photography\", \"name\":\"摄影\", \"col\":2, \"nav_link\":\"/favorite/photography/\"}, {\"id\":\"modeling_hair\", \"name\":\"造型/美妆\", \"nav_link\":\"/favorite/modeling_hair/\"}, {\"id\":\"food_drink\", \"name\":\"美食\", \"nav_link\":\"/favorite/food_drink/\"}, {\"id\":\"travel_places\", \"name\":\"旅行\", \"nav_link\":\"/favorite/travel_places/\"}, {\"id\":\"diy_crafts\", \"name\":\"手工/布艺\", \"nav_link\":\"/favorite/diy_crafts/\"}, {\"id\":\"fitness\", \"name\":\"健身/舞蹈\", \"nav_link\":\"/favorite/fitness/\"}, {\"id\":\"kids\", \"name\":\"儿童\", \"nav_link\":\"/favorite/kids/\"}, {\"id\":\"pets\", \"name\":\"宠物\", \"nav_link\":\"/favorite/pets/\"}, {\"id\":\"quotes\", \"name\":\"美图\", \"nav_link\":\"/favorite/quotes/\"}, {\"id\":\"people\", \"name\":\"明星\", \"nav_link\":\"/favorite/people/\"}, {\"id\":\"beauty\", \"name\":\"美女\", \"nav_link\":\"/favorite/beauty/\"}, {\"id\":\"desire\", \"name\":\"礼物\", \"nav_link\":\"/favorite/desire/\"}, {\"id\":\"geek\", \"name\":\"极客\", \"nav_link\":\"/favorite/geek/\"}, {\"id\":\"anime\", \"name\":\"动漫\", \"nav_link\":\"/favorite/anime/\"}, {\"id\":\"architecture\", \"name\":\"建筑设计\", \"nav_link\":\"/favorite/architecture/\"}, {\"id\":\"art\", \"name\":\"人文艺术\", \"nav_link\":\"/favorite/art/\"}, {\"id\":\"data_presentation\", \"name\":\"数据图\", \"nav_link\":\"/favorite/data_presentation/\"}, {\"id\":\"games\", \"name\":\"游戏\", \"nav_link\":\"/favorite/games/\"}, {\"id\":\"cars_motorcycles\", \"name\":\"汽车/摩托\", \"nav_link\":\"/favorite/cars_motorcycles/\"}, {\"id\":\"film_music_books\", \"name\":\"电影/图书\", \"nav_link\":\"/favorite/film_music_books/\"}, {\"id\":\"tips\", \"name\":\"生活百科\", \"nav_link\":\"/favorite/tips/\"}, {\"id\":\"education\", \"name\":\"教育\", \"nav_link\":\"/favorite/education/\"}, {\"id\":\"sports\", \"name\":\"运动\", \"nav_link\":\"/favorite/sports/\"}, {\"id\":\"funny\", \"name\":\"搞笑\", \"nav_link\":\"/favorite/funny/\"}]";
                try {
                        c = DriverManager.getConnection(URL, USER, PWD);
                        JSONArray ja = new JSONArray(jsonStr);
                        for (int i = 0; i < ja.length(); i++) {
                                JSONObject jo = ja.getJSONObject(i);
                                p = c.prepareStatement("insert into user (name, info) values (?, ?)");
                                p.setString(1, Md5.encode(UUID.randomUUID().toString().getBytes()));
                                p.setString(2, jo.toString());
                                if (1 != p.executeUpdate()) {
                                        System.out.println("插入出错");
                                        break;
                                }
                        }
                        System.out.println("执行结束");
                } catch (Exception e) {
                        System.out.println(e.toString());
                } finally {
                        try {
                                p.close();
                                c.close();
                        } catch (Exception e) {
                                System.out.println(e.toString());
                        }
                }
        }

        // 因为是自定义sql，所以这里在需要用的时候，再取消注释并且做相应的修改。
        // public int executeCustomSql() {
        // Connection c = null;
        // PreparedStatement p = null;
        // try {
        // c = DriverManager.getConnection(URL, USER, PWD);
        // p = c.prepareStatement("insert into app_cash_flow_log (uid, type, amount, pubDateTime) values (?, ?, ?, ?)");
        // p.setInt(1, 1);
        // p.setInt(2, 1);
        // p.setInt(3, 1);
        // // p.setTimestamp(4, java.sql.Timestamp.valueOf("2015-09-20 21:09:10:11"));
        // p.setTimestamp(4, new java.sql.Timestamp(new java.util.Date().getTime()));
        // return p.executeUpdate();
        // } catch (Exception e) {
        // System.out.println(e.toString());
        // return -1;
        // } finally {
        // try {
        // p.close();
        // c.close();
        // } catch (Exception e) {
        // System.out.println(e.toString());
        // }
        // }
        // }
}