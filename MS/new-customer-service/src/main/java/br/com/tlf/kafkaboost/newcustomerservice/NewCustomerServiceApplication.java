package br.com.tlf.kafkaboost.newcustomerservice;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class NewCustomerServiceApplication {

	public static void main(String[] args) throws ExecutionException, InterruptedException {
		var producer = new KafkaProducer<String, String>(properties());
		var value = "Fulano da Silva,SilvaFulano@email.com,(31)7777-9999, Rua Jose Silva, PA";
		var record = new ProducerRecord<>("NEW_CUSTOMER", value, value);
		producer.send(record, (data, ex) -> {
			if(ex != null) {
				ex.printStackTrace();
				return;
			}
			System.out.println("Sucesso ao enviar " + data.topic() + ":::partition " + data.partition() + "/ offset "  + data.offset() + "/ timestamp " + data.timestamp());
		}).get();
	}

	private static Properties properties() {
		var properties = new Properties();
		properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		return properties;
	}
}