package Photo;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mysql.JdbcUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
/**
 * Servlet implementation class Photo
 */
@WebServlet("/Photo")
public class Photo extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Photo() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("utf-8");	//设置编码
		
		//获得磁盘文件条目工厂
		DiskFileItemFactory factory = new DiskFileItemFactory();
		
		//高水平的API文件上传处理
		ServletFileUpload upload = new ServletFileUpload(factory);
		
		try {
			//可以上传多个文件
			List<FileItem> list = (List<FileItem>)upload.parseRequest(request);
			
			for(FileItem item : list)
			{
				//获取表单的属性名字
				String name = item.getFieldName();
				
				//如果获取的 表单信息是普通的 文本 信息
				if(item.isFormField())
				{					
					//获取用户具体输入的字符串 ，名字起得挺好，因为表单提交过来的是 字符串类型的
					String value = item.getString() ;
					
					request.setAttribute(name, value);
				}
				//对传入的非 简单的字符串进行处理 ，比如说二进制的 图片，电影这些
				else
				{
					/**
					 * 以下三步，主要获取 上传文件的名字
					 */
					//获取路径名
					String value = item.getName() ;
					//索引到最后一个反斜杠
					int start = value.lastIndexOf("\\");
					//截取 上传文件的 字符串名字，加1是 去掉反斜杠，
					String filename = value.substring(start+1);

					JdbcUtils     DB = new JdbcUtils();    //负责连接MySQl数据库的类
					DB.getConnection();
					try {
						InputStream in = item.getInputStream() ;
						String sql = "insert into phototest(name,img) values(?,?)";
						List<Object> params = new ArrayList<Object>();
						params.add(filename);
						params.add(in);
						DB.updateByPreparedStatement(sql, params);
					} catch (SQLException e) {
						e.printStackTrace();
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					} finally{
						DB.releaseConn();
					}
				}
			}
			
			
			
		} catch (FileUploadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			
			//e.printStackTrace();
		}
		
		
		request.getRequestDispatcher("filedemo.jsp").forward(request, response);
	}

}
