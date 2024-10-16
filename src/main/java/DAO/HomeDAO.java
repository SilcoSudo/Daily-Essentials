package DAO;

import Model.UserProfile;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Yin Kenna
 */
public class HomeDAO {
   Connection conn;
   ResultSet rs;
   PreparedStatement ps;
           
   public List<UserProfile> getUserProfile() throws SQLException{
       List<UserProfile> result = new ArrayList<>(); 
       conn = DB.DBConnect.getConnection();
       String query = "SELECT a.username, a.password, a.major, a.school FROM user_profile a";
       ps = conn.prepareStatement(query);
       rs = ps.executeQuery();
       while(rs.next()){
           String useranme = rs.getString("username");
           String password = rs.getString("password");
           String major = rs.getString("major");
           String school = rs.getString("school");
           UserProfile user = new UserProfile(useranme, password, major, school);
           result.add(user);
       }
       return result;
   }
}
