package app.factories;

import app.blockchain.Blockchain;
import app.blockchain.ChatClient;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
public class ChatClientFactory {

    private final Blockchain blockchain;

    public List<ChatClient> createChatClients() {
        ChatClient chatClient1 = new ChatClient("Maksim Leonov", blockchain);
        ChatClient chatClient2 = new ChatClient("Vasya Pupkin", blockchain);
        return new ArrayList<>(Arrays.asList(chatClient1, chatClient2));
    }
}
