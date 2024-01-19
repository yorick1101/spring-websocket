package yorick.poc.spring.ws.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class JobDispatcherService {
	
	@Autowired
	private SimpMessagingTemplate template;
	
	private int counter = 0;
	
	@Scheduled(fixedRate = 5000, initialDelay=2000)
	public void schedule() {
		//log.info("Sending job");
		template.convertAndSend("/queue/job", "Job " + counter++);
	}
	

}
