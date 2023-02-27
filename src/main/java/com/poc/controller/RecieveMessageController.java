package com.poc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.poc.model.ConvertedUser;
import com.poc.service.SendMessageToKafka;

@RestController
public class RecieveMessageController {

	@Autowired
	SendMessageToKafka<String> sendStringMessageToKafka;

	@Autowired
	SendMessageToKafka<ConvertedUser> sendUserMessageToKafka;

	@PostMapping(path = "/recieve_user_message")
	public ResponseEntity<?> recieveUserMessage(@RequestBody ConvertedUser userMessage) {

		String response = sendUserMessageToKafka.sendMessageToKafka(userMessage);
		return new ResponseEntity<String>(response, HttpStatusCode.valueOf(201));
	}

	@GetMapping("/recieve_string_message/{message}")
	public ResponseEntity<?> recieveStringMessage(@PathVariable String message) {

		String response = sendStringMessageToKafka.sendMessageToKafka(message);
		return new ResponseEntity<String>(response, HttpStatusCode.valueOf(201));
	}

}
