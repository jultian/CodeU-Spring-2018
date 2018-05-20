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
  <nav style="background-color: #eeeeee">
    <style type="text/css">
      a {text-decoration: none;}
      a:hover {text-decoration: underline;}
    </style>
    <a id="navTitle">
      <a style="color: #444" href="/about.jsp">About</a>
      <div style="float: right;text-align: right;">
        <a style="color: #444" href="/conversations">Conversations</a>
        <% if(request.getSession().getAttribute("user") != null){ %>
    			<a style="color: #444">Hello <%= request.getSession().getAttribute("user") %>!</a>
          <a style="color: #444" href="/users/<%=request.getSession().getAttribute("user")%>">Profile</a>
        <% } else{ %>
    			<a style="color: #444" href="/login">Login</a>
    			<a style="color: #444" href="/register">Register</a>
        <% } %>
    	<% if(request.getSession().getAttribute("user") != null){ %>
    		<% if(UserStore.getInstance().getUser((String)request.getSession().getAttribute("user")).isAdmin()){%>
    		  <a style="color: #444" href="/testdata"> Administration</a>
    		<% }else if(request.getSession().getAttribute("user") !=  null) { %>
			  <a style="color: #444" href="/testdata"> App statistics</a>
			<%}%>
    	<% } %>
    </div>
    </a>
  </nav>

  <div id="title">
    <div
      style="left: 50%;margin-left:auto; margin-right:auto; margin-top: 75px; text-align: center;">
      <h1 style="color: #757575;font-size: 450%;line-height: 1">
        <span style="color: #4285F4">C</span><span style="color: #EA4335">o</span><span style="color: #FBBC05">d</span><span style="color: #4285F4">e</span><span style="color: #34A853">U</span>
        <br/><span style="font-size:90%">Chat</span>
      </h1>
    </div>

    <div id="container"
      style="margin-left:auto; margin-right:auto; margin-top: 75px; width: 800px; text-align: left">
      <h2 style="line-height: .2">Welcome!</h2>
      <p>
        <a href="/login">Login</a> to get started.
      </p>

    <br/>

	  <h2 style="line-height: .2">Meet Team Metapod (11):</h2>
		<b>Kevin Wang</b>
		<p>
			Hey everyone! I'm Kevin, the project advisor for team Metapod(11).
			:D I've been working at Google for a little over three years now and
			work on Google Flights frontend.
		</p>
		<b>Julia Tiang</b>
		<p>
			Hey Guys!I'm a sophomore at UNC-Chapel Hill and I'm majoring in
			Computer Science and pursuing the pre-medical track. I'm also super
			interested in game development (I've been playing Pokemon since I was
			like 5, but now my DS is stowed away somewhere so I don't get too
			carried away with it). I also have a bit of experience with graphic
			design, so I like playing around with typography, layouts, colors etc.

		</p>
		<b>Eda Zhou</b>
		<p>
			I'm Eda and a sophomore at Worcester Polytechnic Institute (WPI for short).
			I am studying computer science although I am not sure what specifically I enjoy
			the most yet and want to pursue. This makes me happy to try new things!
		</p>
		<b>Philip Cori</b>
		<p>
			 I'm a sophomore at Santa Clara University studying Computer Science and
			 Engineering. As of now, I'm probably most interested in Data Science
			 and back-end web development, though I'm super open to learning basically
			 anything computer science related! I'm really looking forward to working
			 with you all!
		</p>
		<b>Maximillian George 'Oche'</b>
		<p>
			Hey everyone, My first name is Maximillian but I go by Oche. I am a sophomore
			at Washington and Jefferson and I'm majoring in computer and information
			studies. I'm really interested in working on game programs maybe or something
			to do with AI. I'm really not sure yet but I'm not really picky so I'm happy
			to work on whatever.
		</p>
    </div>
  </div>
</body>
</html>
