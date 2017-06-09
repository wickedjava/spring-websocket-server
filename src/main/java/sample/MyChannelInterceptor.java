package sample;

import java.security.Principal;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptorAdapter;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.stereotype.Service;


@Configuration
@Service
public class MyChannelInterceptor extends ChannelInterceptorAdapter {

	  @Override
	  public Message<?> preSend(Message<?> message, MessageChannel channel) {
          StompHeaderAccessor accessor =
          MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

      if (StompCommand.CONNECT.equals(accessor.getCommand())) {
       //   Principal user = ... ; // access authentication header(s)
          accessor.setUser(new Principal() {
				
				@Override
				public String getName() {
					// TODO Auto-generated method stub
					return "tester";
				}
			});
      }

      return message;
        
	  }
	  
	  
	  @Override
		public Message<?> postReceive(Message<?> message, MessageChannel channel) {
		  StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
		    StompCommand stompCommand = accessor.getCommand();
		    String user = (String)message.getHeaders().get("simpUser");
		    System.out.println("postReceive StompCommand="+stompCommand.name());
		    System.out.println("postReceive Destination="+accessor.getDestination());
		    System.out.println("postReceive Principal="+accessor.getUser());
		    System.out.println("postReceive user="+user);
		    System.out.println("-------------------------------------------------------------------");
		    System.out.println("-------------------------------------------------------------------");
		    
			return message;
		}

		@Override
		public void afterReceiveCompletion(Message<?> message, MessageChannel channel, Exception ex) {
			  StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
			    StompCommand stompCommand = accessor.getCommand();
			    System.out.println("afterReceiveCompletion StompCommand="+stompCommand.name());
			    System.out.println("afterReceiveCompletion Destination="+accessor.getDestination());
			    System.out.println("afterReceiveCompletion Principal="+accessor.getUser());
			    System.out.println("-------------------------------------------------------------------");
			    System.out.println("-------------------------------------------------------------------");
		}
		
		
		@MessageExceptionHandler
		@SendToUser("/queue/errors")
		public String handleException(Throwable exception) {
			return exception.getMessage();
		}
	}