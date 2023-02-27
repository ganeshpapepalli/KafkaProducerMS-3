package com.poc.kafka.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.poc.kafka.exceptions.CustomException;
import com.poc.kafka.model.User;
import com.poc.kafka.service.SendMessageService;
import com.poc.kafka.validation.UserValidation;

import lombok.extern.slf4j.Slf4j;

@Service(value = "user_message_service")
@Slf4j
public class SendUserMessageToKafkaService implements SendMessageService<User> {

	@Autowired
	public KafkaTemplate<String, User> userKafkaTemplate;

	@Autowired
	public UserValidation userValidation;
	@Value("${spring.kafka.usertopicName}")
	public String userTopic;

	@Value("${spring.kafka.errortopicName}")
	public String errorTopic;

	public SendUserMessageToKafkaService(KafkaTemplate<String, User> userKafkaTemplate) {
		super();
		this.userKafkaTemplate = userKafkaTemplate;
	}

	@Override
	public String sendMessageToKafka(User messagedata) {
		User data=messagedata;
		Message<User> message = null;
		String response = null;
		if (!ObjectUtils.isEmpty(messagedata)) {

			if (userValidation.validateVowelUserName(data.getName())) {

				message = MessageBuilder.withPayload(data).setHeader(KafkaHeaders.TOPIC, userTopic).build();
				response = "User Message sent to Kafka Topic with name:" + userTopic;
			} else {
				message = MessageBuilder.withPayload(data).setHeader(KafkaHeaders.TOPIC, errorTopic).build();
				response = "User Message sent to Kafka Topic with name : " + errorTopic;
			}

		} else {
			log.error("User Deatils are Null Please provide valid Details");
			throw new CustomException("User details must not be null");
		}

		userKafkaTemplate.send(message);
		return response;
	}

}
