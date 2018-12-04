package control;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.List;
import model.User;
import model.UserDAO;

/**
 * Servlet implementation class UserControl
 */
@WebServlet("/UserServlet")
public class UserServlet extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserServlet() 
    {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
		String mode = request.getParameter("mode");
		
		if("add".equals(mode))
		{
			add(request, response);		
//			queryAll(request, response);
			response.sendRedirect("UserServlet?mode=queryAll");
		}
		else if("queryAll".equals(mode))
		{
			queryAll(request, response);
		}
		else if("delete".equals(mode))
		{
			delete(request, response);
//			queryAll(request, response);
			response.sendRedirect("UserServlet?mode=queryAll");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	private void add(HttpServletRequest request, HttpServletResponse response)
	{
		User user = new User();
		user.setName(request.getParameter("u_name"));
		user.setAge(Integer.parseInt(request.getParameter("u_age")));
		user.setWeight(Float.parseFloat(request.getParameter("u_weight")));
		new UserDAO().addUser(user);
	}
	
	private void delete(HttpServletRequest request, HttpServletResponse response)
	{
		User user = new User();
		user.setId(Integer.parseInt(request.getParameter("id")));
		new UserDAO().deleteUser(user);
	}
	
	private void queryAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		List<User> users = new UserDAO().getAllUsers();
		request.setAttribute("users", users);
		request.getRequestDispatcher("/index.jsp").forward(request, response);
	}

}
