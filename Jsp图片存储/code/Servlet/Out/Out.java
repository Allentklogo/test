package Out;

import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mysql.JdbcUtils;

/**
 * Servlet implementation class Out
 */
@WebServlet("/Out")
public class Out extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Out() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("image/jpeg");  
		ServletOutputStream outStream = response.getOutputStream();
		JdbcUtils     DB = new JdbcUtils();    //负责连接MySQl数据库的类
		DB.getConnection();
		try {
			String sql = "select img from phototest where id=?";
			java.sql.Connection con = DB.getConnection();
			
			java.sql.Statement statement = null;
		    ResultSet resultSet = null;
		    InputStream inputStream = null;
		    statement = con.createStatement();
            sql = "select img from phototest where id=2";
            resultSet = statement.executeQuery(sql);
            resultSet.next();
            inputStream = resultSet.getBinaryStream("img");
			byte[] b=new byte[inputStream.available()];    //新建保存图片数据的byte数组
			inputStream.read(b);
			outStream.write(b);
			outStream.flush();
			outStream.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			DB.releaseConn();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
