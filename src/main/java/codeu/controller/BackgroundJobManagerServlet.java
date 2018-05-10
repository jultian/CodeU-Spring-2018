package codeu.controller;

import codeu.model.data.Conversation;
import codeu.model.data.Message;
import codeu.model.data.User;
import codeu.model.store.basic.ConversationStore;
import codeu.model.store.basic.MessageStore;
import codeu.model.store.basic.UserStore;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
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
import java.util.HashMap;
import java.io.*;



public class BackgroundJobManagerServlet extends HttpServlet{
	
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT).withZone(ZoneId.systemDefault());
	final double CONVERSATION_CREATION_PROB = 0.05;
    List<Conversation> shuffledConversations;
//    HashMap<File,Integer> conversationFiles;			//key: File, val: number of lines in File
    ArrayList<File> conversationFiles;				//Parallel arrays. Arrays used for random memory access function (for getting random file)
    ArrayList<Integer> fileLengths;
    int conversationIndex;
	
	@Override
	public void init() throws ServletException {
		super.init();
		shuffledConversations = ConversationStore.getInstance().getAllConversations();
		Collections.shuffle(shuffledConversations);
		conversationFiles = new ArrayList<>();
		fileLengths = new ArrayList<>();
		File folder = new File("chatterbot-conversations");
		conversationIndex = 0;
		
		try {
			for(File fileEntry : folder.listFiles()) {
				conversationFiles.add(fileEntry);
				fileLengths.add(countLines(fileEntry));
//				conversationFiles.put(fileEntry,countLines(fileEntry));
//				System.out.println(fileEntry.getName() + " has " + conversationFiles.get(fileEntry) + " lines!");
			}
		} catch(IOException e){
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws FileNotFoundException {
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
	
	//generates a new conversation given a User bot.
	public void generateConversation(User user) {
		Conversation conversation =
				new Conversation(UUID.randomUUID(), user.getId(), user.getName() + "'s conversation", Instant.now());		//potentially find diff way to name conversations?
		ConversationStore.getInstance().addConversation(conversation);
		//add new conversation to shuffledConversations and re-shuffle
		shuffledConversations.add(conversation);
		Collections.shuffle(shuffledConversations);
	}
	
	//generate a Message for a given User bot, to a given conversation
	public void generateMessage(User user, Conversation conversation) throws FileNotFoundException{
		int randomFileIndex = (int)(Math.random() * conversationFiles.size());
		File selectedFile = conversationFiles.get(randomFileIndex);
		int randomLineIndex = (int)(Math.random() * fileLengths.get(randomFileIndex));
//		String content = "This message generated at: " + formatter.format(Instant.now());
		String content = getCommentAtLine(selectedFile, randomLineIndex);
		Message message = new Message(
				UUID.randomUUID(),
				conversation.getId(),
				user.getId(),
				content,
				Instant.now());
		MessageStore.getInstance().addMessage(message);
	}
	
	//counts number of lines in a given file (uses bytes for efficiency
	public int countLines(File file) throws IOException {
	    InputStream is = new BufferedInputStream(new FileInputStream(file));
	    try {
	        byte[] c = new byte[1024];
	        int count = 0;
	        int readChars = 0;
	        boolean empty = true;
	        while ((readChars = is.read(c)) != -1) {
	            empty = false;
	            for (int i = 0; i < readChars; ++i) {
	                if (c[i] == '\n') {
	                    ++count;
	                }
	            }
	        }
	        return (count == 0 && !empty) ? 1 : count;
	    } finally {
	        is.close();
	    }
	}
	
	//returns the comment at given line index for given file
	public String getCommentAtLine(File file, int lineIndex) throws FileNotFoundException {
		BufferedReader br = new BufferedReader(new FileReader(file));
		int line = 0;
		String lastLine = null;
		try {
			while((lastLine = br.readLine()) != null && line++ < lineIndex);
		} catch(IOException e){
			e.printStackTrace();
		}
		return lastLine.substring(lastLine.indexOf(":")+1).trim();		
	}
	
}