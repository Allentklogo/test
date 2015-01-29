<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="net.sf.json.JSONArray,net.sf.json.JSONObject,fenye.Versions;"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%response.setHeader("Pragma", "No-cache");
response.setHeader("Cache-Control", "no-cache");
response.setDateHeader("Expires", 0); %>
<%
HttpSession se = request.getSession();
Versions versions;
versions = (Versions) se.getAttribute("fenye_verisons");

Integer now = versions.getYe_now();
Integer ye = versions.getYe_conut();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">    
<title>版本查询</title>
</head>
<body>
<%
if( versions.getVersions_id().length() > 0 ){
	out.print("版本编号："+versions.getVersions_id());
}
if( versions.getName().length() > 0 ){
	out.print("版本名称："+versions.getName());
}
%>
<table>
	<tr>
		<th>版本编号</th>
		<th>版本名称</th>
	</tr>
	<%
	JSONArray js = versions.getJson();
	for(int i = 0 ;i < js.size() ;i++){
		JSONObject j = js.getJSONObject(i);
	%>
	<tr>
	<td><%=j.get("versions_id") %></td>
	<td><%=j.get("name") %></td>
	</tr>
	<%}%>
</table> 
当前页：<%=now %>/共<%=ye %>页<%=versions.getAmount() %>条记录<br>
<%if( (now-1) >= 1 ){
	int number=now-1;
	out.print("<a href='"+path+"/Select_versions?now=1'>首页</a>");
	out.print("<a href='"+path+"/Select_versions?now="+number+"'>上一页</a>");
}
%>
<%
Integer[] y = versions.getY();
for(int i:y){
	if(i != now)
		out.print("<a href='"+path+"/Select_versions?now="+i+"'>"+i+"</a>");
	else
		out.print("<a>"+i+"</a>");
}  
%>
<%if( (now+1) <= ye ){
	int number=now+1;
	out.print("<a href='"+path+"/Select_versions?now="+number+"'>下一页</a>");
	out.print("<a href='"+path+"/Select_versions?now="+ye+"'>末页</a>");
}
%>
<form action="<%=path %>/Find_versions" method="get">
	<table>
		<tr>
			<td>版本编号</td>
			<td>
				<input name="id" type="text" />
			</td>
		</tr>
		<tr>
			<td>版本名称</td>
			<td>
				<input name="name" type="text" />
			</td>
		</tr>
		<tr>
			<td></td>
			<td>
				<input type="submit" onclick="" value="查询" />
			</td>
		</tr>
	</table>
</form>
</body>
</html>