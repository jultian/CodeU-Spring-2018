<%@ page import="java.util.List" %>
<%@ page import="codeu.model.data.Conversation" %>
<%@ page import="codeu.model.data.Message" %>
<%@ page import="codeu.model.store.basic.UserStore" %>

<%
Conversation conversation = (Conversation) request.getAttribute("conversation");
List<Message> messages = (List<Message>) request.getAttribute("messages");
%>

<!DOCTYPE html>
<html>
<head>
  <title>Profile Page</title>
  <link rel="stylesheet" href="/css/main.css">
 <style>
   label {
     display: inline-block;
     width: 100px;
   }
   
 </style>
 <!-- Import JQuery-->
 <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
</head>

  <body>
      <nav>
        <style type="text/css">
          a {text-decoration: none;}
          a:hover {text-decoration: underline;}
        </style>
        <a id="navTitle" href="/">CodeU Chat App</a>
        <a href="/about.jsp">About</a>
        <div style="float: right; text-align: right;">
        <a href="/conversations">Conversations</a>
        <% if(request.getSession().getAttribute("user") != null){ %>
          <a href="/users/<%=request.getSession().getAttribute("user")%>">Hello <%= request.getSession().getAttribute("user") %>!</a>
        <% } else{ %>
          <a href="/login">Login</a>
          <a href="/register">Register</a>
        <% } %>
      <% if(request.getSession().getAttribute("user") != null){ %>
        <% if(UserStore.getInstance().getUser((String)request.getSession().getAttribute("user")).isAdmin()){%>
          <a href="/testdata">Administration</a>
        <% } else if(request.getSession().getAttribute("user") !=  null) {%>
          <a href="/testdata">App Statistics</a>
        <%}%>
      <% } %>
      </div>
      </nav>

  <!-- gets profile page owner's username-->
  <script>
    var url = window.location.pathname;
    var profileName = url.substring(url.lastIndexOf('/')+1);
  </script>

  <div id="container">
    <h1 style="font-size: 175%"><script>
      document.write(profileName)
      </script>'s Profile Page</h1>
      <p><%=UserStore.getInstance().getUser((String)request.getSession().getAttribute("user")).getBio()%></p>

    <!-- class that holds everything only owning user can see-->
    <div class="own_page">Edit your About Me (only you can see this)
          <form action="/users" method = "POST">
            <input type="text">
            <button type ="submit">Update</button>
          </form>
          <br/>
    </div>

  </div>

  <div id="container">
    <h1 style = "font-size: 175%"><script>document.write(profileName)
      </script>'s Sent Messages
    </h1>
    <p>
      <% List<Message> messagesSent = UserStore.getInstance().getUser((String)request.getSession().getAttribute("user")).getMessagesSent(); %>
      <textarea
        rows = "<%=messagesSent.size()%>"
        cols = "1">
          <% for(int i = 0; i < messagesSent.size(); i++){ %>
            <%=messagesSent.get(i).getTimeStamp() + " " + messagesSent.get(i).getContent()%>
        <% } %>
      </textarea>
    </p>
  </div>

  <script>
  console.log("profile belongs to:" + profileName);
  if(profileName == '<%=request.getSession().getAttribute("user")%>'){
     $(".own_page").show();
  }
  </script>

</body>
</html>
