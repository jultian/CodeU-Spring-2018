package codeu.controller;

import codeu.model.data.Conversation;
import codeu.model.data.Message;
import codeu.model.data.User;
import codeu.model.store.basic.ConversationStore;
import codeu.model.store.basic.MessageStore;
import codeu.model.store.basic.UserStore;
import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

/** Servlet class responsible for the users profile pages. */
public class UsersServlet extends HttpServlet {

  /** Store class that gives access to Conversations. */
  private ConversationStore conversationStore;

  /** Store class that gives access to Messages. */
  private MessageStore messageStore;

  /** Store class that gives access to Users. */
  private UserStore userStore;

  /** Set up state for handling chat requests. */
  @Override
  public void init() throws ServletException {
    super.init();
    setUserStore(UserStore.getInstance());
  }

  /**
   * Sets the UserStore used by this servlet. This function provides a common setup method for use
   * by the test framework or the servlet's init() function.
   */
  void setUserStore(UserStore userStore) {
    this.userStore = userStore;
  }

  /**
   * This function fires when a user navigates to the chat page. It gets the conversation title from
   * the URL, finds the corresponding Conversation, and fetches the messages in that Conversation.
   * It then forwards to chat.jsp for rendering.
   */
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {
    String requestUrl = request.getRequestURI();
    String profileName = requestUrl.substring("/users/".length()); //username of profile's owner
    String user = (String) request.getSession().getAttribute("user"); // username of user viewing page

	// if the user is viewing their own profile page
    if (profileName.equals(user)) {
      System.out.println(profileName + " has editing permissions!");
    }

    //request.setAttribute("isSameUser", isSameUser(user, profileName));

    request.getRequestDispatcher("/WEB-INF/view/users.jsp").forward(request, response);
  }

  /**
   * This function fires when a user submits the form on the chat page. It gets the logged-in
   * username from the session, the conversation title from the URL, and the chat message from the
   * submitted form data. It creates a new Message from that data, adds it to the model, and then
   * redirects back to the chat page.
   */
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {

    String username = (String) request.getSession().getAttribute("user");
    
    // INSERT LOGOUT LOGIC HERE
//    if (request.getParameter("Logout") == "Logout") {
//    		response.sendRedirect("/users");
//    }
    
    String bio = (String)request.getParameter("bio");
    userStore.updateProfile(username, bio); // add bio to User in User Store
    
    // redirect to a GET request
    response.sendRedirect("/users/" + username);
  }

  /* Input: name of user viewing and name of user that owns the profile page
  * Returns true if user is viewing their own profile page, false otherwise
  * Checks if given user (profile page) is equal to user who is viewing the page

  public boolean isSameUser(String user, String profileName){
    return profileName.equals(user);
  }
  */
}
