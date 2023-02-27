package com.poc.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import com.poc.model.ConvertedUser;

@Configuration
@EnableKafka
public class KafkaProducerConfig {
	
	@Bean
	public ProducerFactory<String, String> stringProducerFactory() {
		Map<String, Object> configProperties = new HashMap<>();
		configProperties.put(org.apache.kafka.clients.producer.ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		configProperties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		configProperties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

		return new DefaultKafkaProducerFactory<>(configProperties);
	}

	@Bean
	public KafkaTemplate<String, String> stringKafkaTemplate() {
		return new KafkaTemplate<>(stringProducerFactory());
	}
	
	
	
	@Bean
	public ProducerFactory<String, ConvertedUser> userProducerFactory() {
		Map<String, Object> configProperties = new HashMap<>();
		configProperties.put(org.apache.kafka.clients.producer.ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		configProperties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		configProperties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

		return new DefaultKafkaProducerFactory<>(configProperties);
	}

	@Bean
	public KafkaTemplate<String, ConvertedUser> userKafkaTemplate() {
		return new KafkaTemplate<>(userProducerFactory());
	}

}
