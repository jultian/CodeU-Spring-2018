<%@ page import="codeu.model.store.basic.UserStore" %>
<%@ page import="codeu.model.data.User" %>
<!DOCTYPE html>
<html>
<head>
 <title>Register</title>
 <link rel="stylesheet" href="/css/main.css">
 <style>
   label {
     display: inline-block;
     width: 100px;
   }
 </style>
</head>
<body>

    <nav style="background-color: #eeeeee">
        <style type="text/css">
          a {text-decoration: none;}
          a:hover {text-decoration: underline;}
        </style>
        <a style="color: #444" id="navTitle" href="/">CodeU Chat App</a>
        <a style="color: #444" href="/about.jsp">About</a>
        <div style="float: right; text-align: right;">
        <a style="color: #444" href="/conversations">Conversations</a>
        <% if(request.getSession().getAttribute("user") != null){ %>
          <a style="color: #444" href="/users/<%=request.getSession().getAttribute("user")%>">Hello <%= request.getSession().getAttribute("user") %>!</a>
        <% } else{ %>
          <a style="color: #444" href="/login">Login</a>
          <a style="color: #444" href="/register">Register</a>
        <% } %>
     <% if(request.getSession().getAttribute("user") != null){ %>
      <% if(UserStore.getInstance().getUser((String)request.getSession().getAttribute("user")).isAdmin()){%>
        <a style="color: #444" href="/testdata">Administration</a>
      <% } else if(request.getSession().getAttribute("user") !=  null) {%>
        <a style="color: #444" href="/testdata">App Statistics</a>
      <%}%>
    <% } %>
      </div>
      </nav>
  
 <div id="container">
   <h1 style="font-size: 175%">Register</h1>
   <% if (request.getAttribute("error") != null){ %>
	 <h2 style="color:red"><%= request.getAttribute("error") %></h2>
   <% } %>

   <form action="/register" method="POST">
     <label for="username">Username: </label>
     <input type="text" name="username" id="username">
     <br/>
     <label for="password">Password: </label>
     <input type="password" name="password" id="password">
     <br/><br/>
     <button type="submit">Submit</button>
   </form>
 </div>
</body>
</html>
