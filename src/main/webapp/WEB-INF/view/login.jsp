<%@ page import="codeu.model.store.basic.UserStore" %>
<%@ page import="codeu.model.data.User" %>
<!DOCTYPE html>
<html>
<head>
 <title>Login</title>
 <link rel="stylesheet" href="/css/main.css">
 <style>
   label {
     display: inline-block;
     width: 100px;
   }
 </style>
</head>
<body>

 <nav>
   <a id="navTitle" href="/">CodeU Chat App</a>
   <a href="/about.jsp">About</a>
   <a href="/conversations">Conversations</a>
   <% if(request.getSession().getAttribute("user") != null){ %>
     <a>Hello <%= request.getSession().getAttribute("user") %>!</a>
   <% } else{ %>
     <a href="/login">Login</a>
     <a href="/register">Register</a>
   <% } %>
   <% if(request.getSession().getAttribute("user") != null){ %>
		<% if(UserStore.getInstance().getUser((String)request.getSession().getAttribute("user")).isAdmin()){%>
		  <a href="/testdata">Administration</a>
		<% } %>
	<% } %>
 </nav>

 <div id="container">
   <h1 style="font-size: 175%">Login</h1>

   <% if(request.getAttribute("error") != null){ %>
       <h2 style="color:red"><%= request.getAttribute("error") %></h2>
   <% } %>

   <form action="/login" method="POST">
     <label for="username">Username: </label>
     <input type="text" name="username" id="username">
     <br/>
     <label for="password">Password: </label>
     <input type="password" name="password" id="password">
     <br/><br/>
     <button type="submit">Login</button>
   </form>
 </div>
</body>
</html>
