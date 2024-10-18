package com.messagequeue.app;

import java.util.concurrent.BlockingQueue;

public class MessageConsumer implements Runnable{
	
	    private final BlockingQueue<Message> queue;
	    private int successCount = 0;
	    protected int errorCount = 0;

	    public MessageConsumer(BlockingQueue<Message> queue) {
	        this.queue = queue;
	    }

	    @Override
	    public void run() {
	        try {
	            while (true) {
	                Message message = queue.take();
	                if ("END".equals(message.getContent())) {
	                    break;
	                }
	                processMessage(message);
	            }
	        } catch (InterruptedException e) {
	            Thread.currentThread().interrupt();
	        }finally {
	            System.out.println("Messages Processed Successfully: " + successCount);
	            System.out.println("Messages Failed: " + errorCount);
	        }
	    }

	    protected void processMessage(Message message) {
	        if (Math.random() < 0.8) {
	            System.out.println("Consumed: " + message.getContent());
	            successCount++;
	        } else {
	            System.err.println("Error processing: " + message.getContent());
	            errorCount++;
	        }
	    }

	    public int getSuccessCount() {
	        return successCount;
	    }

	    public int getErrorCount() {
	        return errorCount;
	    }
	}