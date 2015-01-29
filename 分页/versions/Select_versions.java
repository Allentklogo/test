package versions;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fenye.Versions;
import mhsql.JdbcUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Servlet implementation class Select_versions
 */
@WebServlet("/Select_versions")
public class Select_versions extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Select_versions() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession se = request.getSession();
		Versions versions = (Versions) se.getAttribute("fenye_verisons");
		//�õ�Ҫ��ѯҳ��
		String nowStr = request.getParameter("now");
		Integer now;
		if( nowStr == null ){
			now = 1;
		}else{
			now =Integer.valueOf( nowStr );
			if (now == 0){
				versions = new Versions();
				now = 1;
			}
		}
		JdbcUtils jdbc = new JdbcUtils();
		jdbc.getConnection();
		jdbc.getStatement();
		String sql = "select count(versions_id) as number from game_versions ";
		sql = jude_term(sql,versions.getVersions_id(),versions.getName());
		ResultSet res = jdbc.select_one(sql);
		try {
			//�õ���¼��
			Integer number = 0;
			if(res.next()){
				number = res.getInt("number");
			}
			//����������¼һҳ��������ҳ��
			Integer ye = number / 5;
			if( number%5 > 0 ){
				ye = ye + 1;
			}
			//���㵱ǰ����ת��ҳ��
			Integer y[] = gety(ye,now);
			
			//��ҳ��ѯ��¼
			sql = "select * from game_versions ";
			sql = jude_term(sql,versions.getVersions_id(),versions.getName());
			sql+=" limit "+(now-1)*5+",5";
			res = jdbc.select_one(sql);
			JSONArray jsarry = new JSONArray();
			if(res.next()){
				do{
					JSONObject jsobject = new JSONObject();
					jsobject.element("name", res.getString("name"));
					jsobject.element("versions_id", res.getInt("versions_id"));
					jsarry.add(jsobject);
				}while(res.next());
			}
			//��ֵ
			versions.setAmount(number);
			versions.setJson(jsarry);
			versions.setY(y);
			versions.setYe_conut(ye);
			versions.setYe_now(now);
			se.setAttribute("fenye_verisons", versions);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		jdbc.closeStatement();
		jdbc.releaseConn();
		response.sendRedirect("versions/select_all.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//�õ���ѯ����
	}
	
	//�ж϶�������ѯ�Ƿ����
	public String jude_term(String sql,String term1,String term2){
		int len = sql.length();
		if( term1.length() > 0 ){
			sql += " where versions_id="+term1;
		}
		if( len == sql.length() ){
			if( term2.length() > 0 ){
				sql += " where name='"+term2+"'";
			}
		}else{
			if( term2.length() > 0 ){
				sql += " and name='"+term2+"'";
			}
		}
		return sql;
	}
	
	//�õ�����תҳ������
	public Integer[] gety(int ye,int now){
		Integer y[];
		if( ye <= 5 ){
			y = new Integer[ye];
			int j=1;
			for( int i = 0;i < ye ;j++,i++ ){
				y[i] = j;
			}
		}else{
			y = new Integer[5];
			if( now - 3 >= 0 ){
				int j=-2;
				for( int i = 0;i < 5 ;j++,i++ ){
					y[i] = now + j;
				}
			}else{
				int j=1;
				for( int i = 0;i < 5 ;j++,i++ ){
					y[i] = j;
				}
			}
			
			if( y[4] > ye ){
				int j=4;
				for( int i = 0;i < 5 ;j--,i++ ){
					y[i] = ye - j;
				}
			}
		}
		return y;
	}
}
