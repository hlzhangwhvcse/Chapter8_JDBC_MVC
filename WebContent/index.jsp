<%@ page language="java" import="java.util.*, model.User" pageEncoding="UTF-8"%>
<jsp:useBean id="users" scope="request" class="java.util.ArrayList"></jsp:useBean>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"+ request.getServerName() + ":" + request.getServerPort()+ path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">
		<title>一个基于MVC模式的数据库访问应用程序体验</title>
	</head>

	<body>
		<!-- <form action="UserServlet?mode=add" method="post">-->
		<form action="UserServlet" method="post">
			<input type="hidden" name="mode" value="add">
			<table>
				<tr>
					<td>
						姓名 :
					</td>
					<td>
						<input type="text" name="u_name" required="required"/>
					</td>
					<td>
						年龄 :
					</td>
					<td>
						<select name="u_age">
							<option value="18">18</option>
							<option value="19">19</option>
							<option value="20">20</option>
							<option value="21">21</option>
							<option value="22">22</option>
						</select>
					</td>
				</tr>
				<tr>
					<td>
						体重：
					</td>
					<td>
						<input type="number" name="u_weight" required="required"/>KG
					</td>
					<td colspan="2">
						<input type="submit" value="提交" />
					</td>
				</tr>

			</table>
		</form>
		<hr />
			<a href="UserServlet?mode=queryAll">查看全部记录</a>
		<%
			if (users.size() > 0) 
			{
		%>
		<table border="1" width="60%" borderColor="#c0c0c0" align="center">
			<tr>
				<td>
					用户ID
				</td>
				<td>
					姓名
				</td>
				<td>
					年龄
				</td>
				<td>
					体重(KG)
				</td>
				<td>
					操作
				</td>
			</tr>
			<%
				Iterator iter = users.iterator();
					while (iter.hasNext()) 
					{
						User user = (User) iter.next();
			%>
			<tr>
				<td>
					<%=user.getId()%>
				</td>
				<td>
					<%=user.getName()%>
				</td>
				<td>
					<%=user.getAge()%>
				</td>
				<td>
					<%=user.getWeight()%>
				</td>
				<td>
					<a href="<%="UserServlet?mode=delete&id=" + user.getId() %>">删除</a>
				</td>
			</tr>
			<%
				}
			%>
		</table>
		<%
			} 
		
		%>
	</body>
</html>
