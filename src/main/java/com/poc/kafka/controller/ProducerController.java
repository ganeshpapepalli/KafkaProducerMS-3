package com.poc.kafka.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.poc.kafka.exceptions.CustomException;
import com.poc.kafka.model.ResponseModel;
import com.poc.kafka.model.User;
import com.poc.kafka.service.SendMessageService;

@RestController
public class ProducerController {

	private static Logger LOGGER = LoggerFactory.getLogger(ProducerController.class);

	@Autowired
	public SendMessageService<User> sendMessageServiceForUser;
	
	@Autowired
	public SendMessageService<String> sendMessageServiceForString;
	
	

	@GetMapping("/sendMessage/{message}")
	public String sendMessage(@PathVariable String message) {
		LOGGER.info("Send meessage controller started with message %s", message);
		sendMessageServiceForString.sendMessageToKafka(message);

		return "Message Sent to Kafka!";
	}

	@PostMapping("/sendMessage")
	public ResponseEntity<ResponseModel> sendUserMessage(@RequestBody User user) throws CustomException {
		ResponseModel response = new ResponseModel();
		try {

			response.setResponse(sendMessageServiceForUser.sendMessageToKafka(user));
			response.setStatusCode(HttpStatus.OK.value());
			response.setResponseStatus("Success");
		} catch (CustomException exception) {

			response.setStatusCode(HttpStatus.BAD_REQUEST.value());
			response.setResponseStatus("Failure");
		}

		return new ResponseEntity<ResponseModel>(response, HttpStatus.ACCEPTED);
	}

}
