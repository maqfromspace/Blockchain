package app.blockchain;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalTime;

/**
 * Chat client
 */
@AllArgsConstructor
@Slf4j
public class ChatClient implements Runnable {
    String name;
    Blockchain blockchain;

    @Override
    public void run() {
        String message = "Message from " + name + " (" + LocalTime.now() + ")";
        log.info(name + " create message!");
        blockchain.addMessage(message);
    }
}
