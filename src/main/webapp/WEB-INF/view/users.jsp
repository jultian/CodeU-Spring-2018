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
  </div>

<!-- class that holds everything only owning user can see-->
  <div class="own_page"><center>
    Edit your About Me (only you can see this)
      <form action="/users" method = "POST">
        <input type="text" name = "bio" id = "bio"></center>
        <button type ="submit" style="display: block; margin: 0 auto;">Update</button>
      </form>
      <br/><br/>

  </div>

  <div id="messages"><center>
    <h1 style="font-size: 175%"><script>
      document.write(profileName)
    </script>'s Sent Messages
    </h1>
    <p>
      <textarea id = "myMessages"
        rows = "<%=UserStore.getInstance().getUser((String)request.getSession().getAttribute("user")).getMessagesSent().size()%>"
        cols = "1">
          <% for(int i = 0; i < UserStore.getInstance().getUser((String)request.getSession().getAttribute("user")).getMessagesSent().size(); i++){ %>
            <%=UserStore.getInstance().getUser((String)request.getSession().getAttribute("user")).getMessagesSent().get(i).getTimeStamp()%>
            <%=UserStore.getInstance().getUser((String)request.getSession().getAttribute("user")).getMessagesSent().get(i).getContent()%>
        <% } %>
        </textarea>
    </p>
  </div></center>

  <script>
  console.log("profile belongs to:" + profileName);
  if(profileName == '<%=request.getSession().getAttribute("user")%>'){
     $(".own_page").show();
  }
  </script>

</body>
</html>
