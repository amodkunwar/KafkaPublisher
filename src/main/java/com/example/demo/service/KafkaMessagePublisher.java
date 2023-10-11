package com.example.demo.service;

import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

@Service
public class KafkaMessagePublisher {

	@Autowired
	private KafkaTemplate<String, Object> kafkaTemplate;

	@Value("${kafka.topic}")
	private String topic;

	public void sendMessage(String message) {
		CompletableFuture<SendResult<String, Object>> future = kafkaTemplate.send(topic, message);

		future.whenComplete((result, ex) -> {

			if (ex == null) {
				System.out.println("Message " + message + " and offset is " + result.getRecordMetadata().offset());
			} else {
				System.out.println("Unable to send message to topic :" + ex.getLocalizedMessage());
			}

		});

	}

}
