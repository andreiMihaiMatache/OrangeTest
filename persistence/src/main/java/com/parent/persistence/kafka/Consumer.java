package com.parent.persistence.kafka;

import com.parent.persistence.model.Transaction;
import com.parent.persistence.services.TransactionServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class Consumer {
    private static final Logger LOG = LoggerFactory.getLogger(Consumer.class);

//    @Autowired
    TransactionServices transactionServices;

    public Consumer(TransactionServices transactionServices) {
        this.transactionServices = transactionServices;
    }

    /**
     * Consumator KAFKA. Citeste mesajele din coada si le trimite spre salvare in DB
     * @param transaction
     * @param headers
     */
    @KafkaListener(topics = "${kafka.topic}")
    public void receive(@Payload Transaction transaction, @Headers MessageHeaders headers) {

        try {
            transactionServices.saveTransaction(transaction);
        }
        catch (Exception e){
            LOG.error("Eroare consumator KAFKA: ",e);
        }


    }

}
