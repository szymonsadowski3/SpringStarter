package pl.edu.agh.kis.poll.core;

import org.springframework.stereotype.Component;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@Component
public class Supervisor {
    private final Queue<DeferredJSON> responseBodyQueue = new ConcurrentLinkedQueue<>();

    public void processQueues() {
        for (DeferredJSON result : responseBodyQueue) {
            if(result.process()) {
                responseBodyQueue.remove(result);
            }
        }
    }

    public void add(DeferredJSON deferredJSON) {
        responseBodyQueue.add(deferredJSON);
    }
}
