package codeu.controller;

import codeu.model.data.Conversation;
import codeu.model.data.Message;
import codeu.model.data.User;
import codeu.model.store.basic.ConversationStore;
import codeu.model.store.basic.MessageStore;
import codeu.model.store.basic.UserStore;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.ZoneId;


public class BackgroundJobManagerServlet extends HttpServlet{
	
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT).withZone(ZoneId.systemDefault());
	
	@Override
	public void init() throws ServletException {
		super.init();
	}
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) {
			Message message;
			if(ConversationStore.getInstance().numConversations() > 0) {	
				//insert messages into first conversation for now
				Conversation conversation = ConversationStore.getInstance().getAllConversations().get(0);
				for(User user : UserStore.getInstance().getBots()) {
					message =
						new Message(
								UUID.randomUUID(),
								conversation.getId(),
								user.getId(),
								"This message generated at: " + formatter.format(Instant.now()),
								Instant.now());
					MessageStore.getInstance().addMessage(message);
				}
			}
			
	}
	
}