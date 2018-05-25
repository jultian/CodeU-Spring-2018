<%--
  Copyright 2017 Google Inc.

  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
--%>
<%@ page import="codeu.model.store.basic.UserStore" %>
<%@ page import="codeu.model.data.User" %>
<!DOCTYPE html>
<html>
<head>
  <title>CodeU Chat App</title>
  <link rel="stylesheet" href="/css/main.css">
</head>
<body></body>
  <nav>
    <style type="text/css">
      a {text-decoration: none;}
      a:hover {opacity: 0.5;}
    </style>
    <a id="navTitle">
      <a href="/about.jsp">About</a>
      <div style="float: right;text-align: right;">
        <a href="/conversations">Conversations</a>
        <% if(request.getSession().getAttribute("user") != null){ %>
    			<a href="/users/<%=request.getSession().getAttribute("user")%>">Hello <%= request.getSession().getAttribute("user") %>!</a>
        <% } else{ %>
    			<a href="/login">Login</a>
    			<a href="/register">Register</a>
        <% } %>
    	<% if(request.getSession().getAttribute("user") != null){ %>
    		<% if(UserStore.getInstance().getUser((String)request.getSession().getAttribute("user")).isAdmin()){%>
    		  <a href="/testdata"> Administration</a>
    		<% }else if(request.getSession().getAttribute("user") !=  null) { %>
			  <a href="/testdata"> App statistics</a>
			<%}%>
    	<% } %>
    </div>
    </a>
  </nav>

  <div id="title">
    <div id = "CodeU">
      <h1>
        <span id = "C_E">C</span><span id = "O">o</span><span id = "D">d</span><span id = "C_E">e</span><span id = "U">U</span>
        <span style="color: #444444; font-size:90%">Chat</span>
      </h1>
    </div>

    <div id="container"
      style="margin-top: 75px; text-align: left"><center>
      <h2 style="line-height: .2">Welcome!</h2>
      <p>
      <% if(request.getSession().getAttribute("user") == null){ %>
        <a href="/login">Login</a> to get started.
      <% } %>
      </p>
    </div></center>

  </div>
</body>
</html>
