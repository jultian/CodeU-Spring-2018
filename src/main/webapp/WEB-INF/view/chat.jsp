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
<%@ page import="java.util.List" %>
<%@ page import="codeu.model.data.Conversation" %>
<%@ page import="codeu.model.data.Message" %>
<%@ page import="codeu.model.store.basic.UserStore" %>
<%@ page import="codeu.model.data.User" %>
<%
Conversation conversation = (Conversation) request.getAttribute("conversation");
List<Message> messages = (List<Message>) request.getAttribute("messages");
%>

<!DOCTYPE html>
<html>
<head>
  <title><%= conversation.getTitle() %></title>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
  <link rel="stylesheet" href="/css/main.css" type="text/css">

  <style>
    #chat {
      background-color: white;
      height: 500px;
      overflow-y: scroll
    }
  </style>
</head>
 <script>
    // scroll the chat div to the bottom
    function scrollChat() {
      var chatDiv = document.getElementById('chat');
      chatDiv.scrollTop = chatDiv.scrollHeight;
    };

	$(document).ready(function (){
		$('li').click(function(){
			$(this).find(":button").toggle();
		});
	});
	var urlString = window.location.pathname.toString();
	$(document).ready(function() {
		$('button.delete').click(function(e) {
			var messageId = $(this).val();
			document.getElementById(messageId).remove();
			e.preventDefault();
			$.ajax({
				type: 'post',
				url: urlString,
				data: {
					"messageId": messageId,
					"redirectURL": urlString,
					"id": "delete"
				},
			});
		});
	});
  </script>
<body onload="scrollChat()">

    <nav>
        <style type="text/css">
          a {transition-duration: 0.5s; text-decoration: none;}
          a:hover {opacity: 0.5;}
        </style>
        <a id="navTitle" href="/">
          <span id = "C_E">C</span><span id = "O">o</span><span id = "D">d</span><span id = "C_E">e</span><span id = "U">U</span>
        </a>
        <a href="/about.jsp">About</a>
        <div style="float: right; text-align: right;">
        <a href="/conversations">Conversations</a>
        <% if(request.getSession().getAttribute("user") != null){ %>
          <a href="/users/<%=request.getSession().getAttribute("user")%>">Hello <%= request.getSession().getAttribute("user") %>!</a>
        <% } else { %>
          <a href="/login">Login</a>
          <a href="/register">Register</a>
        <% } %>
      <% if(request.getSession().getAttribute("user") != null){ %>
        <% if(UserStore.getInstance().getUser((String)request.getSession().getAttribute("user")).isAdmin()){%>
          <a href="/testdata">Administration</a>
        <% } else if(request.getSession().getAttribute("user") !=  null) { %>
          <a href="/testdata">App Statistics</a>
      <% } %>
      <% } %>
      </div>
      </nav>

  <div id="container">

    <h1 style="font-size: 175%"><%= conversation.getTitle() %>
      <a href="" style="float: right">&#8635;</a></h1>

    <hr/>

    <div id="chat">
      <ul>
    <% for (Message message : messages) {
        String author = UserStore.getInstance()
          .getUser(message.getAuthorId()).getName(); %>
      <li id = "<%= message.getId().toString()%>"><strong><a href="/users/<%=author%>"><%= author %>:</a></strong> <%= message.getContent() %> <% if(request.getSession().getAttribute("user").equals(UserStore.getInstance().getUser(message.getAuthorId()).getName())){ %><button value = "<%= message.getId().toString() %>" class = "delete" type = "button" style = "display : none;">Delete</button><% } %></li>
    <% } %>
      </ul>
    </div>

    <hr/>

    <% if (request.getSession().getAttribute("user") != null) { %>
    <form action="/chat/<%= conversation.getId().toString() %>" method="POST" id="myForm">
		<input type="hidden" name="id" value="newMessage"/>
        <input type="text" name="message">
        <button type="submit">Send</button>
        <br/>
    </form>
    <% } else { %>
      <p><a href="/login">Login</a> to send a message.</p>
    <% } %>

    <hr/>

  </div>

</body>
</html>
