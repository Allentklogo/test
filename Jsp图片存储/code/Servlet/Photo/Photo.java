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
		
		request.setCharacterEncoding("utf-8");	//���ñ���
		
		//��ô����ļ���Ŀ����
		DiskFileItemFactory factory = new DiskFileItemFactory();
		
		//��ˮƽ��API�ļ��ϴ�����
		ServletFileUpload upload = new ServletFileUpload(factory);
		
		try {
			//�����ϴ�����ļ�
			List<FileItem> list = (List<FileItem>)upload.parseRequest(request);
			
			for(FileItem item : list)
			{
				//��ȡ������������
				String name = item.getFieldName();
				
				//�����ȡ�� ����Ϣ����ͨ�� �ı� ��Ϣ
				if(item.isFormField())
				{					
					//��ȡ�û�����������ַ��� ���������ͦ�ã���Ϊ���ύ�������� �ַ������͵�
					String value = item.getString() ;
					
					request.setAttribute(name, value);
				}
				//�Դ���ķ� �򵥵��ַ������д��� ������˵�����Ƶ� ͼƬ����Ӱ��Щ
				else
				{
					/**
					 * ������������Ҫ��ȡ �ϴ��ļ�������
					 */
					//��ȡ·����
					String value = item.getName() ;
					//���������һ����б��
					int start = value.lastIndexOf("\\");
					//��ȡ �ϴ��ļ��� �ַ������֣���1�� ȥ����б�ܣ�
					String filename = value.substring(start+1);

					JdbcUtils     DB = new JdbcUtils();    //��������MySQl���ݿ����
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
