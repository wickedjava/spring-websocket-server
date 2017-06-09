package sample;

import java.security.Principal;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptorAdapter;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.messaging.Message;

/*
 * WebSocket configuration.
 *
 * @Author Jay Sridhar
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer
{
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config)
    {
        config.enableSimpleBroker("/topic","/queue");
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry)
    {
        registry.addEndpoint("/chat").setAllowedOrigins("*").withSockJS();
    }
    
    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
      registration.setInterceptors(new MyChannelInterceptor());
//    	 registration.setInterceptors(new ChannelInterceptorAdapter() {
//
//    	        @Override
//    	        public Message<?> preSend(Message<?> message, MessageChannel channel) {
//
//    	            StompHeaderAccessor accessor =
//    	                MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
//
//    	            if (StompCommand.CONNECT.equals(accessor.getCommand())) {
//    	             //   Principal user = ... ; // access authentication header(s)
//    	                accessor.setUser(new Principal() {
//							
//							@Override
//							public String getName() {
//								// TODO Auto-generated method stub
//								return "tester";
//							}
//						});
//    	            }
//
//    	            return message;
//    	        }
//    	    });
      
    }
    
}
