package sample;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.user.SimpUser;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;;

@RestController
public class GreetingController {
	 @Autowired
    private SimpMessagingTemplate template;
    
    
    @Autowired
    private SimpUserRegistry userRegistry;
    
    @RequestMapping(path="/greetings", method=POST)
    public void greet(@RequestParam String greets) {
        String text = "[" +System.currentTimeMillis() + "]:" + greets;
        System.out.println("greet message="+text);
//        this.template.setUserDestinationPrefix("/topic");
//        this.template.convertAndSend("/messages", text);
//        this.template.setDefaultDestination("/messages");
//        this.template.convertAndSend(text);
      //  this.template.send("/topic/messages", null);
        this.template.convertAndSend("/topic/messages", "{\"haha\":\"text\"}");
        this.template.convertAndSendToUser("tester", "/queue/position-updates", "{\"queue\":\"text\"}");
        
    }

    
    @RequestMapping(path="/users", method=POST)
    public void users() {

    	Set<SimpUser> simpUsers = userRegistry.getUsers();
    	simpUsers.forEach(simpUser -> System.out.println("simUser="+simpUser.getName()));
    	
    }
}
