package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO 
{
	private Connection conn = null;
	private PreparedStatement ps = null;
	static 
	{
		try 
		{
			Class.forName("com.mysql.jdbc.Driver");
		} 
		catch (ClassNotFoundException e) 
		{
			e.printStackTrace();
		}
	}

	private void prepareConnection() 
	{
		try 
		{
			if (conn == null || conn.isClosed()) 
			{
				conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/db_user", "root", "root");
			}
		} 
		catch (SQLException e) 
		{
			throw new RuntimeException("���ݿ������쳣    :" + e.getMessage());
		}
	}

	private void close() 
	{
		try 
		{
			if (ps != null) 
			{
				ps.close();
			}
			if (conn != null) 
			{
				conn.close();
			}
		} 
		catch(SQLException e) 
		{
			throw new RuntimeException("�ر������쳣:" + e.getMessage());
		}
	}

	private void rollback() 
	{
		try 
		{
			conn.rollback();
		} 
		catch(SQLException e) {
			throw new RuntimeException("����ع�ʧ��:" + e.getMessage());
		}
	}

	public int addUser(User user) 
	{
		int rowCount = 0;
		try 
		{
			prepareConnection();
			conn.setAutoCommit(false);
			ps = conn.prepareStatement("insert into t_user(u_name, u_age, u_weight) values(?, ?, ?)");
			ps.setString(1, user.getName());
			ps.setInt(2, user.getAge());
			ps.setFloat(3, user.getWeight());
			rowCount = ps.executeUpdate();
			conn.commit();
		} 
		catch (SQLException e) 
		{
			rollback();
			e.printStackTrace();
		} 
		finally 
		{
			close();
		}
		return rowCount;
	}

	public int deleteUser(User user) 
	{
		int rowCount = 0;
		try 
		{
			prepareConnection();
			conn.setAutoCommit(false);
			ps = conn.prepareStatement("delete from t_user where id = ?");
			ps.setInt(1, user.getId());
			rowCount = ps.executeUpdate();
			conn.commit();
		} 
		catch (SQLException e) 
		{
			rollback();
			e.printStackTrace();
		} 
		finally 
		{
			close();
		}
		return rowCount;
	}

	public int updateUser(User user) 
	{
		int rowCount = 0;
		try 
		{
			prepareConnection();
			conn.setAutoCommit(false);
			ps = conn.prepareStatement("update t_user set u_name=?, u_age=?, u_weight=? where id=?");
			ps.setString(1, user.getName());
			ps.setInt(2, user.getAge());
			ps.setFloat(3, user.getWeight());
			ps.setInt(4, user.getId());
			rowCount = ps.executeUpdate();
			conn.commit();
		} 
		catch (SQLException e) 
		{
			rollback();
			e.printStackTrace();
		} 
		finally 
		{
			close();
		}
		return rowCount;
	}

	public List<User> getAllUsers() 
	{
		List<User> users = new ArrayList<User>();
		try 
		{
			prepareConnection();
			ps = conn.prepareStatement("select * from t_user ");
			ResultSet resultSet = ps.executeQuery();
			while (resultSet.next()) //���������к��м�¼���ͽ���¼��װ��Ϊһ��User������ӵ�����List��
			{
				User user = new User();
				user.setId(resultSet.getInt(1));
				user.setName(resultSet.getString(2));
				user.setAge(resultSet.getInt(3));
				user.setWeight(resultSet.getFloat(4));
				users.add(user);
				
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		} 
		finally 
		{
			close();
		}
		return users;
	}

	public User getUserById(Integer id) 
	{
		User user = null;
		try 
		{
			prepareConnection();
			ps = conn.prepareStatement("select * from t_user where id=?");
			ps.setInt(1, id);
			ResultSet resultSet = ps.executeQuery();
			if (resultSet.next())//���������к��м�¼���ͽ���¼��װ��Ϊһ��User���� 
			{
				user = new User();
				user.setId(resultSet.getInt(1));
				user.setName(resultSet.getString(2));
				user.setAge(resultSet.getInt(3));
				user.setWeight(resultSet.getFloat(4));
				
			}
		} 
		catch(SQLException e) 
		{
			e.printStackTrace();
		} 
		finally
		{
			close();
		}
		return user;
	}
}
