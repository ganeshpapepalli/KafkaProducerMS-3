package com.poc.kafka.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import com.poc.kafka.model.User;

@Configuration
public class KafkaProducerConfig {
	
	@Value("${spring.kafka.producer.bootstrap-servers}")
	public String bootstrapServers;

	 @Bean
	  public ProducerFactory<String, String> stringProducerFactory() {
	    Map<String, Object> configProperties = new HashMap<>();
	    configProperties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
	    configProperties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
	    configProperties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
	    return new DefaultKafkaProducerFactory<>(configProperties);
	  }
	  
	  @Bean(name = "stringTemplate")
	  public KafkaTemplate<String, String> stringKafkaTemplate() {
	    return new KafkaTemplate<>(stringProducerFactory());
	  }
	  
	  @Bean
	  public ProducerFactory<String, User> userProducerFactory() {
	    Map<String, Object> configProperties = new HashMap<>();
	    configProperties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
	    configProperties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
	    configProperties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
	    
	    return new DefaultKafkaProducerFactory<>(configProperties);
	  }
	  
	  @Bean(name = "userTemplate")
	  public KafkaTemplate<String,User> userKafkaTemplate() {
	    return new KafkaTemplate<>(userProducerFactory());
	  }

}
