package com.messagequeue.app;

import java.util.concurrent.BlockingQueue;

public class MessageProduce implements Runnable {
    private final BlockingQueue<Message> queue;

    // Constructor to initialize queue
    public MessageProduce(BlockingQueue<Message> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < 10; i++) {
                String messageContent = "Message " + i;
                System.out.println("Produced: " + messageContent);

                // Put the message into the queue
                queue.put(new Message(messageContent));

                // Simulate a delay in message production
                Thread.sleep(500);
            }

            // Indicate end of message production
            queue.put(new Message("END"));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();  // Handle thread interruption
        }
    }
}
