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

import codeu.model.data.Message;
import codeu.model.data.User;
import codeu.model.store.persistence.PersistentStorageAgent;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.LinkedHashMap;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.function.Function;
/**
 * Store class that uses in-memory data structures to hold values and automatically loads from and
 * saves to PersistentStorageAgent. It's a singleton so all servlet classes can access the same
 * instance.
 */
public class MessageStore {

  /** Singleton instance of MessageStore. */
  private static MessageStore instance;
  
  /**
   * Returns the singleton instance of MessageStore that should be shared between all servlet
   * classes. Do not call this function from a test; use getTestInstance() instead.
   */
  public static MessageStore getInstance() {
    if (instance == null) {
      instance = new MessageStore(PersistentStorageAgent.getInstance());
      
    }
    return instance;
  }

  /**
   * Instance getter function used for testing. Supply a mock for PersistentStorageAgent.
   *
   * @param persistentStorageAgent a mock used for testing
   */
  public static MessageStore getTestInstance(PersistentStorageAgent persistentStorageAgent) {
    return new MessageStore(persistentStorageAgent);
  }

  /**
   * The PersistentStorageAgent responsible for loading Messages from and saving Messages to
   * Datastore.
   */
  private PersistentStorageAgent persistentStorageAgent;

  /** The in-memory list of Messages. */
  private HashMap<String, Message> messageMap;

  /** This class is a singleton, so its constructor is private. Call getInstance() instead. */
  private MessageStore(PersistentStorageAgent persistentStorageAgent) {
    this.persistentStorageAgent = persistentStorageAgent;
	messageMap = new LinkedHashMap<>();
  }

  /**
   * Load a set of randomly-generated Message objects.
   *
   * @return false if an error occurs.
   */
  public boolean loadTestData() {
    boolean loaded = false;
    try {
	  DefaultDataStore.getInstance().getAllMessages().forEach(messageMap::putIfAbsent);
      loaded = true;
    } catch (Exception e) {
      loaded = false;
      System.out.println("ERROR: Unable to establish initial store (messages).");
    }
    return loaded;
  }

  /** Add a new message to the current set of messages known to the application. */
  public void addMessage(Message message) {
	messageMap.put(message.getId().toString(), message);
    persistentStorageAgent.writeThrough(message);
    User user = UserStore.getInstance().getUser(message.getAuthorId());
    if(user != null) {			//do this check so test passes (change MessageStoreTest later)
    	user.addMessage(message);
    }
  }

  /** Access the current set of Messages within the given Conversation. */
  public List<Message> getMessagesInConversation(UUID conversationId) {

    List<Message> messagesInConversation = new ArrayList<>();
	
	messageMap.forEach((key, value)->{
		if(value.getConversationId().equals(conversationId)){
			messagesInConversation.add(value);
		}
	});

    return messagesInConversation;
  }

  /** Sets the List of Messages stored by this MessageStore. */
  public void setMessages(List<Message> messages) {
	messages.forEach(e->messageMap.put(e.getId().toString(),e));
	//I cannot seem to get this stream to work with a linkedMap
	//Map<String, Message> messageMap1 = messages.stream().collect(Collectors.toMap(m -> m.getId().toString(), m -> m));
  }
  
  public int numMessages() {
	 return messageMap.size();
  }
  
  public Message getMessage(UUID id) {
	if(messageMap.get(id.toString()) != null){
		return messageMap.get(id.toString());
	}else{
	    return null;
	}
  }
}






