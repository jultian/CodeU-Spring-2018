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

import codeu.model.data.User;
import codeu.model.data.Message;
import codeu.model.data.Conversation;
import codeu.model.store.basic.MessageStore;
import codeu.model.store.basic.ConversationStore;
import codeu.model.store.persistence.PersistentStorageAgent;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.time.Instant;
import java.time.temporal.ChronoUnit;


/**
 * Store class that uses in-memory data structures to hold values and automatically loads from and
 * saves to PersistentStorageAgent. It's a singleton so all servlet classes can access the same
 * instance.
 */
public class UserStore {

  /** Singleton instance of UserStore. */
  private static UserStore instance;

  /**
   * Returns the singleton instance of UserStore that should be shared between all servlet classes.
   * Do not call this function from a test; use getTestInstance() instead.
   */
  public static UserStore getInstance() {
    if (instance == null) {
      instance = new UserStore(PersistentStorageAgent.getInstance());
    }
    return instance;
  }

  /**
   * Instance getter function used for testing. Supply a mock for PersistentStorageAgent.
   *
   * @param persistentStorageAgent a mock used for testing
   */
  public static UserStore getTestInstance(PersistentStorageAgent persistentStorageAgent) {
    return new UserStore(persistentStorageAgent);
  }

  /**
   * The PersistentStorageAgent responsible for loading Users from and saving Users to Datastore.
   */
  private PersistentStorageAgent persistentStorageAgent;

  /** The in-memory list of Users. */
  private List<User> users;

  /** This class is a singleton, so its constructor is private. Call getInstance() instead. */
  private UserStore(PersistentStorageAgent persistentStorageAgent) {
    this.persistentStorageAgent = persistentStorageAgent;
    users = new ArrayList<User>();
  }

  /** Load a set of randomly-generated Message objects. */
  public void loadTestData() {
    users.addAll(DefaultDataStore.getInstance().getAllUsers());
  }

  /**
   * Access the User object with the given name.
   *
   * @return null if username does not match any existing User.
   */
  public User getUser(String username) {
    // This approach will be pretty slow if we have many users CONSIDER CHANGING TO HASHTABLE
    for (User user : users) {
      if (user.getName().equals(username)) {
        return user;
      }
    }
    return null;
  }

  /**
   * Access the User object with the given UUID.
   *
   * @return null if the UUID does not match any existing User.
   */
  public User getUser(UUID id) {
    for (User user : users) {
      if (user.getId().equals(id)) {
        return user;
      }
    }
    return null;
  }

  /** Add a new user to the current set of users known to the application. */
  public void addUser(User user) {
    users.add(user);
    persistentStorageAgent.writeThrough(user);
  }

  /** Return true if the given username is known to the application. */
  public boolean isUserRegistered(String username) {
    for (User user : users) {
      if (user.getName().equals(username)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Sets the List of Users stored by this UserStore. This should only be called once, when the data
   * is loaded from Datastore.
   */
  public void setUsers(List<User> users) {
    this.users = users;
  }
  
  public int numUsers() {
	  return users.size();
  }
  
  //return user with most messages
  public User wordiestUser() {
	  User wordiest = null;		//will return a null User if no existing Users, Conversations, or Messages
	  int mostMessages = 0;		//keeps track of most messages sent by a single User
	  int numMessages;			//temp var for every user
	  for(User user : users) {
		  numMessages = 0;
		  for(Conversation convo : ConversationStore.getInstance().getAllConversations()) {
			  for(Message msg : MessageStore.getInstance().getMessagesInConversation(convo.getId())) {
				  if(msg.getAuthorId().equals(user.getId())) {
					  numMessages++;		//increment if a message is found that belongs to the user being visited in loop
				  }
			  }
		  }
		  if(numMessages > mostMessages) {		//check and update vars if this user has most messages so far
			  wordiest = user;
			  mostMessages = numMessages;
		  }
	  }		  
	  return wordiest;
  }
  
  //returns User most recently registered
  public User newestUser() {
	  if(users.size() == 0) return null;
	  Instant latestCreation = users.get(0).getCreationTime();		//begin with first user in list as newest user
	  User newestUser = users.get(0);
	  for(User user : users) {
		  if(user.getCreationTime().compareTo(latestCreation) > 0) {		//check and update vars if newer user is found by comparing creationTimes
			  latestCreation = user.getCreationTime();
			  newestUser = user;
		  }
	  }
	  return newestUser;
  }
  
  //returns User with most messages sent in the last 24 hours
  //similar implementation to wordiestUser
  public User mostActiveUser() {
	  User mostActive = null;
	  Instant now = Instant.now();		//create Instant representing current time
	  int numMessages;					
	  int mostRecentMessages = 0;		
	  long hourDiff;
	  for(User user : users) {
		  numMessages = 0;
		  for(Conversation convo : ConversationStore.getInstance().getAllConversations()) {
			  for(Message msg : MessageStore.getInstance().getMessagesInConversation(convo.getId())) {
				  if(msg.getAuthorId().equals(user.getId())) {
					  hourDiff = ChronoUnit.HOURS.between(msg.getCreationTime(),now);		//get number of hours ago the message was sent
					  if(hourDiff < 24)			//if message was sent in last 24 hours, increment
						  numMessages++;
				  }
			  }
		  }
		  if(numMessages > mostRecentMessages) {			//check for new most active user
			  mostActive = user;
			  mostRecentMessages = numMessages;
		  }
	  }
	  return mostActive;
	  
  }
}
