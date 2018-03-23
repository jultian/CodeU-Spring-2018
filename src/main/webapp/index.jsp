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
<body>

  <nav>
    <a id="navTitle" href="/">CodeU Chat App</a>
    <a href="/conversations">Conversations</a>
    <% if(request.getSession().getAttribute("user") != null){ %>
      <a>Hello <%= request.getSession().getAttribute("user") %>!</a>
    <% } else{ %>
			<a href="/login">Login</a>
			<a href="/register">Register</a>
    <% } %>
    <a href="/about.jsp">About</a>
	<% if(request.getSession().getAttribute("user") != null){ %>
		<% if(UserStore.getInstance().getUser((String)request.getSession().getAttribute("user")).isAdmin()){%>
		  <a href="/testdata"> Test Data</a>
		<% } %>
	<% } %>
  </nav>

  <div id="container">
    <div
      style="width:75%; margin-left:auto; margin-right:auto; margin-top: 50px;">

      <h1>CodeU Chat App</h1>
      <h2>Welcome!</h2>

      <ul>
        <li><a href="/login">Login</a> to get started.</li>
        <li>Go to the <a href="/conversations">conversations</a> page to
            create or join a conversation.</li>
        <li>View the <a href="/about.jsp">about</a> page to learn more about the
            project.</li>
        <li>You can <a href="/testdata">load test data</a> to fill the site with
            example data.</li>
      </ul>
	  
	  <h3>Meet the team!</h3>
	  
	  <ul>
		<li><b>Kevin Wang</b></li>
		<p>
			Hey everyone! I'm Kevin, the project advisor for team Metapod(11). 
			:D I've been working at Google for a little over three years now and
			work on Google Flights frontend. 
		</p>
		<li><b>Julia Tiang</b></li>
		<p>
			Hey Guys!I'm a sophomore at UNC-Chapel Hill and I'm majoring in
			Computer Science and pursuing the pre-medical track. I'm also super 
			interested in game development (I've been playing Pokemon since I was
			like 5, but now my DS is stowed away somewhere so I don't get too 
			carried away with it). I also have a bit of experience with graphic
			design, so I like playing around with typography, layouts, colors etc.
		
		</p>
		<li><b>Eda Zhou</b></li>
		<p>
			I'm Eda and a sophomore at Worcester Polytechnic Institute (WPI for short).
			I am studying computer science although I am not sure what specifically I enjoy
			the most yet and want to pursue. 
		</p>
		<li><b>Philip Cori</b></li>
		<p>
			 I'm a sophomore at Santa Clara University studying Computer Science and
			 Engineering. As of now, I'm probably most interested in Data Science
			 and back-end web development, though I'm super open to learning basically
			 anything computer science related! I'm really looking forward to working
			 with you all!
		</p>
		<li><b>Maximillian George 'Oche'</b></li>
		<p>
			Hey Kevin, My first name is Maximillian but I go by Oche. I am a sophomore
			at Washington and Jefferson and I'm majoring in computer and information
			studies. I'm really interested in working on game programs maybe or something
			to do with AI. I'm really not sure yet but I'm not really picky so I'm happy
			to work on whatever.
		</p>
	  </ul>
	  
    </div>	
  </div>
</body>
</html>
