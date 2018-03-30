// Copyright 2017 Google Inc.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//    http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package codeu.model.store.basic;

import codeu.model.data.Conversation;
import codeu.model.data.Message;
import codeu.model.data.User;
import codeu.model.store.persistence.PersistentStorageAgent;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.Scanner;
import java.io.*;

/**
 * This class makes it easy to add dummy data to your chat app instance. To use fake data, set
 * USE_DEFAULT_DATA to true, then adjust the COUNT variables to generate the corresponding amount of
 * users, conversations, and messages. Note that the data must be consistent, i.e. if a Message has
 * an author, that author must be a member of the Users list.
 */
public class DefaultDataStore {

  /** Set this to true to use generated default data. */
  private boolean USE_DEFAULT_DATA = true;

  /**
   * Default user count. Only used if USE_DEFAULT_DATA is true. Make sure this is <= the number of
   * names in the getRandomUsernames() function.
   */
  private int DEFAULT_USER_COUNT = 20;

  /**
   * Default conversation count. Only used if USE_DEFAULT_DATA is true. Each conversation is
   * assigned a random user as its author.
   */
  private int DEFAULT_CONVERSATION_COUNT = 10;

  /**
   * Default message count. Only used if USE_DEFAULT_DATA is true. Each message is assigned a random
   * author and conversation.
   */
  private int DEFAULT_MESSAGE_COUNT = 100;

  private static DefaultDataStore instance = new DefaultDataStore();

  public static DefaultDataStore getInstance() {
    return instance;
  }

  private List<User> users;
  private List<Conversation> conversations;
  private List<Message> messages;

  /** This class is a singleton, so its constructor is private. Call getInstance() instead. */
  private DefaultDataStore() {
    users = new ArrayList<>();
    conversations = new ArrayList<>();
    messages = new ArrayList<>();

    if (USE_DEFAULT_DATA) {
      addRandomUsers();
      addRandomConversations();
      addRandomMessages();
    }
  }
  
  Map<String, User> userMap = new HashMap<>();
  Map<String, Conversation> conversationMap = new HashMap<>();
  Map<String, Message> messageMap = new HashMap<>();
  
  public User makeUser(String userName){ 
	User user = new User(UUID.randomUUID(), userName, "password", Instant.now());
	return user;
  }
  
  public void testData(){
	/*BufferReader takes in information from a given txt file. for now the txt file name is hardcoded
	will eventually make it so that user can select which file they want*/	
	BufferedReader br = new BufferedReader(new FileReader("Practice.txt"));
	Conversation conversation = null;
	
	while(br.readLine() != null){
		
		//line refers to what part of the script the scanner is on i.e "Romeo: i love juliet....... /return symbol/"
		String line = br.readLine();
		String userName = line.substring(0, line.indexOf(":")).trim();
		
		//creates user if userName isn't mapped to any object within userMap
		userMap.computeIfAbsent(userName, this::makeUser);
		
		if(conversationMap.isEmpty() && userName != null){
			String title = "Test Conversation";
			User conversationCreator = userMap.get(userName);
			conversation = new Conversation(UUID.randomUUID(), conversationCreator.getId(), title, Instant.now());
			conversationMap.put(userName, conversation);
		}
		
		if(userName != null){
			User author = userMap.get(userName);
			String content = line.substring(line.indexOf(":")+1).trim();
			Message message =
			  new Message(UUID.randomUUID(), conversation.getId(), author.getId(), content, Instant.now());
			messageMap.put(userName, message);
		}
	}
	// performs the write through function for each Map value V  
	userMap.forEach((k, v) -> PersistentStorageAgent.getInstance().writeThrough(v));
	conversationMap.forEach((k, v) -> PersistentStorageAgent.getInstance().writeThrough(v));
	messageMap.forEach((k, v) -> PersistentStorageAgent.getInstance().writeThrough(v));
  }
  
  public boolean isValid() {
    return true;
  }

  public List<User> getAllUsers() {
    return users;
  }

  public List<Conversation> getAllConversations() {
    return conversations;
  }

  public List<Message> getAllMessages() {
    return messages;
  }

  private void addRandomUsers() {

    List<String> randomUsernames = getRandomUsernames();
    Collections.shuffle(randomUsernames);

    for (int i = 0; i < DEFAULT_USER_COUNT; i++) {
      User user = new User(UUID.randomUUID(), randomUsernames.get(i), "password", Instant.now());
      // for testing so password is hard-coded
      PersistentStorageAgent.getInstance().writeThrough(user);
      users.add(user);
    }
  }

  private void addRandomConversations() {
    for (int i = 1; i <= DEFAULT_CONVERSATION_COUNT; i++) {
      User user = getRandomElement(users);
      String title = "Conversation_" + i;
      Conversation conversation =
          new Conversation(UUID.randomUUID(), user.getId(), title, Instant.now());
      PersistentStorageAgent.getInstance().writeThrough(conversation);
      conversations.add(conversation);
    }
  }

  private void addRandomMessages() {
    for (int i = 0; i < DEFAULT_MESSAGE_COUNT; i++) {
      Conversation conversation = getRandomElement(conversations);
      User author = getRandomElement(users);
      String content = getRandomMessageContent();

      Message message =
          new Message(
              UUID.randomUUID(), conversation.getId(), author.getId(), content, Instant.now());
      PersistentStorageAgent.getInstance().writeThrough(message);
      messages.add(message);
    }
  }

  private <E> E getRandomElement(List<E> list) {
    return list.get((int) (Math.random() * list.size()));
  }

  private List<String> getRandomUsernames() {
    List<String> randomUsernames = new ArrayList<>();
    randomUsernames.add("Grace");
    randomUsernames.add("Ada");
    randomUsernames.add("Stanley");
    randomUsernames.add("Howard");
    randomUsernames.add("Frances");
    randomUsernames.add("John");
    randomUsernames.add("Henrietta");
    randomUsernames.add("Gertrude");
    randomUsernames.add("Charles");
    randomUsernames.add("Jean");
    randomUsernames.add("Kathleen");
    randomUsernames.add("Marlyn");
    randomUsernames.add("Ruth");
    randomUsernames.add("Irma");
    randomUsernames.add("Evelyn");
    randomUsernames.add("Margaret");
    randomUsernames.add("Ida");
    randomUsernames.add("Mary");
    randomUsernames.add("Dana");
    randomUsernames.add("Tim");
    randomUsernames.add("Corrado");
    randomUsernames.add("George");
    randomUsernames.add("Kathleen");
    randomUsernames.add("Fred");
    randomUsernames.add("Nikolay");
    randomUsernames.add("Vannevar");
    randomUsernames.add("David");
    randomUsernames.add("Vint");
    randomUsernames.add("Mary");
    randomUsernames.add("Karen");
    return randomUsernames;
  }

  private String getRandomMessageContent() {
    String loremIpsum =
        "dolorem ipsum, quia dolor sit amet consectetur adipiscing velit, "
            + "sed quia non numquam do eius modi tempora incididunt, ut labore et dolore magnam "
            + "aliquam quaerat voluptatem. Ut enim ad minima veniam, quis nostrum exercitationem ullam "
            + "corporis suscipit laboriosam, nisi ut aliquid ex ea commodi consequatur? Quis autem vel eum "
            + "iure reprehenderit, qui in ea voluptate velit esse, quam nihil molestiae consequatur, vel illum, "
            + "qui dolorem eum fugiat, quo voluptas nulla pariatur";

    int startIndex = (int) (Math.random() * (loremIpsum.length() - 100));
    int endIndex = (int) (startIndex + 10 + Math.random() * 90);
    String messageContent = loremIpsum.substring(startIndex, endIndex).trim();

    return messageContent;
  }
}
