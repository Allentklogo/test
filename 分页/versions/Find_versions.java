package versions;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fenye.Versions;

/**
 * Servlet implementation class Find_versions
 */
@WebServlet("/Find_versions")
public class Find_versions extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Find_versions() {
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
		versions.setName("");
		versions.setVersions_id("");
		
		String id = new String(
				request.getParameter("id").getBytes("ISO8859_1"),
				"utf-8");
		String name = new String(
				request.getParameter("name").getBytes("ISO8859_1"),
				"utf-8");
		
		if( id.length() > 0 ){
			versions.setVersions_id(id);
		}
		if( name.length() > 0){
			versions.setName(name);
		}
		
		se.setAttribute("fenye_versions", versions);
		response.sendRedirect("Select_versions");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
