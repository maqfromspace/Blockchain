package app;

import app.blockchain.Blockchain;
import app.blockchain.ChatClient;
import app.blockchain.Miner;
import app.factories.ChatClientFactory;
import app.factories.MinerFactory;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
public class App {
    public void start() throws InterruptedException {
        Scanner scanner = new Scanner(System.in);
        log.info("Enter size of blockchain");
        int size = scanner.nextInt();

        log.info("Enter number of miners");
        int numberOfMiners = scanner.nextInt();

        Blockchain blockchain = Blockchain.getInstance(size);

        MinerFactory minerFactory = new MinerFactory(blockchain);
        ChatClientFactory chatClientFactory = new ChatClientFactory(blockchain);

        List<Miner> miners = minerFactory.createMiners(numberOfMiners);
        List<ChatClient> chatClients = chatClientFactory.createChatClients();

        ExecutorService executor = Executors.newFixedThreadPool(numberOfMiners);
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(chatClients.size());

        chatClients.forEach(chatClient -> scheduler.scheduleAtFixedRate(chatClient, 0, 5, TimeUnit.SECONDS));
        miners.forEach(executor::submit);

        executor.shutdown();

        if (executor.awaitTermination(30, TimeUnit.MINUTES)) {
            log.info("Mining completed!");
        } else {
            log.info("Mining was not completed correctly");
        }

        scheduler.shutdown();

        if (scheduler.awaitTermination(30, TimeUnit.MINUTES)) {
            log.info("Chat closed!");
        } else {
            log.info("Chat was closed incorrectly");
        }

        blockchain.serialize();
        File file = new File("blockchain");
        if (file.exists()) {
            if (blockchain.deserialize() && blockchain.isValid()) {
                log.info("Blockchain serialized successfully");
            } else {
                log.info("Blockchain in file is not valid");
            }
        } else {
            log.info("Blockchain file not found");
        }
    }

    public static void main(String[] args) {
        try {
            new App().start();
        } catch (InterruptedException e) {
            log.error("We are so sorry for that =(");
            e.printStackTrace();
        }
    }
}
