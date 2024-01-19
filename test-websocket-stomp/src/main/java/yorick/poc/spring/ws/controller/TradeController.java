package yorick.poc.spring.ws.controller;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

import lombok.extern.slf4j.Slf4j;
import yorick.poc.spring.ws.handler.msg.TradeRequest;
import yorick.poc.spring.ws.handler.msg.TradeResponse;


@Controller
@Slf4j
public class TradeController {

	@MessageMapping("/trade")
	@SendToUser("/queue/reply") 
	public CompletableFuture<TradeResponse> accept(Message<TradeRequest> message) throws IOException {
		log.info("TradeController.accept() called");
		TradeRequest request = message.getPayload();
		CompletableFuture<TradeResponse> future = CompletableFuture.supplyAsync(() -> {
            TradeResponse response = new TradeResponse(true, request);
            return response;
        });
		return future;
	}
}
