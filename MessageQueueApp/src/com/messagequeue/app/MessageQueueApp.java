package com.messagequeue.app;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class MessageQueueApp {
	public static void main(String[] args) {
        BlockingQueue<Message> queue = new LinkedBlockingQueue<>(10);

        MessageProduce producer = new MessageProduce(queue);
        MessageConsumer consumer = new MessageConsumer(queue);

        Thread producerThread = new Thread(producer);
        Thread consumerThread = new Thread(consumer);

        producerThread.start();
        consumerThread.start();

        try {
            producerThread.join();
            consumerThread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("Message processing completed.");
    }
}


