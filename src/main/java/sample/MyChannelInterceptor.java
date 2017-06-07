package sample;

import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptorAdapter;
import org.springframework.messaging.Message;

public class MyChannelInterceptor extends ChannelInterceptorAdapter {

	  @Override
	  public Message<?> preSend(Message<?> message, MessageChannel channel) {
	    StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
	    StompCommand stompCommand = accessor.getCommand();
	    System.out.println("StompCommand="+stompCommand.name());
	    System.out.println("Destination="+accessor.getDestination());
	    System.out.println("Principal="+accessor.getUser());
	    
	  //  StompCommand command = accessor.getStompCommand();
	    
	    return message;
	  }
	}