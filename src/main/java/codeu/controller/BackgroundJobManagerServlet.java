package codeu.controller;

import codeu.model.data.Conversation;
import codeu.model.data.Message;
import codeu.model.data.User;
import codeu.model.store.basic.ConversationStore;
import codeu.model.store.basic.MessageStore;
import codeu.model.store.basic.UserStore;
import java.time.Instant;
import java.util.ArrayList;
import java.util.UUID;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.ZoneId;
import java.util.Collections;
import java.lang.Math;



public class BackgroundJobManagerServlet extends HttpServlet{
	
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT).withZone(ZoneId.systemDefault());
	final double CONVERSATION_CREATION_PROB = 0.05;
    ArrayList<Conversation> shuffledConversations;
    int conversationIndex;
	
	@Override
	public void init() throws ServletException {
		super.init();
		shuffledConversations = (ArrayList<Conversation>) ConversationStore.getInstance().getAllConversations();
		Collections.shuffle(shuffledConversations);
		conversationIndex = 0;
	}
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) {
			for(User user : UserStore.getInstance().getBots()) {
				if(shuffledConversations.size() == 0 || Math.random() < CONVERSATION_CREATION_PROB)
					generateConversation(user);
				else {
					conversationIndex = (conversationIndex + 1) % shuffledConversations.size();
					Conversation conversation = shuffledConversations.get(conversationIndex);		//cycle through all conversations randomly
					generateMessage(user,conversation);
				}
			}
	}
	
	public void generateConversation(User user) {
		Conversation conversation =
				new Conversation(UUID.randomUUID(), user.getId(), user.getName() + "'s conversation", Instant.now());
		ConversationStore.getInstance().addConversation(conversation);
		//add new conversation to shuffledConversations and shuffle it in
		shuffledConversations.add(conversation);
		Collections.shuffle(shuffledConversations);
	}
	
	public void generateMessage(User user, Conversation conversation) {
		Message message = new Message(
				UUID.randomUUID(),
				conversation.getId(),
				user.getId(),
				"This message generated at: " + formatter.format(Instant.now()),
				Instant.now());
		MessageStore.getInstance().addMessage(message);
	}
	
}