package test;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.junit.Before;
import org.junit.Test;

import com.messagequeue.app.Message;
import com.messagequeue.app.MessageConsumer;

public class MessageQueueAppTest {
	   private BlockingQueue<Message> queue;
	    private MessageConsumer consumer;

	    @Before
	    public void setup() {
	        queue = new LinkedBlockingQueue<>(10);
	        consumer = new MessageConsumer(queue);
	    }

	    @Test
	    public void testSuccessfulProcessing() throws InterruptedException {
	        queue.put(new Message("Message 1"));
	        queue.put(new Message("Message 2"));
	        queue.put(new Message("END"));

	        Thread consumerThread = new Thread(consumer);
	        consumerThread.start();
	        consumerThread.join();

	        assertEquals(2, consumer.getSuccessCount());
	        assertEquals(0, consumer.getErrorCount());
	    }

	    @Test
	    public void testProcessingWithError() throws InterruptedException {
	        queue.put(new Message("Message 1"));
	        queue.put(new Message("Message 2"));
	        queue.put(new Message("Message 3"));
	        queue.put(new Message("END"));

	        consumer = new MessageConsumer(queue) {
	            @Override
	            protected void processMessage(Message message) {
	                if ("Message 2".equals(message.getContent())) {
	                    System.err.println("Error processing: " + message.getContent());
	                    errorCount++;
	                } else {
	                    super.processMessage(message);
	                }
	            }
	        };

	        Thread consumerThread = new Thread(consumer);
	        consumerThread.start();
	        consumerThread.join();

	        assertEquals(2, consumer.getSuccessCount());
	        assertEquals(1, consumer.getErrorCount());
	    }
	}
