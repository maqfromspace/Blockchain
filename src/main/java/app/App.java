package app;

import app.blockchain.Blockchain;
import app.blockchain.Miner;
import app.factories.MinerFactory;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Slf4j
public class App {
    public static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws InterruptedException {
        log.info("Enter size of blockchain");
        int size = scanner.nextInt();

        log.info("Enter number of miners");
        int numberOfMiners = scanner.nextInt();

        Blockchain blockchain = Blockchain.getInstance(size);
        MinerFactory minerFactory = new MinerFactory(blockchain);
        List<Miner> miners = minerFactory.createMiners(numberOfMiners);
        ExecutorService executor = Executors.newFixedThreadPool(numberOfMiners);
        miners.forEach(executor::submit);
        executor.shutdown();
        if(executor.awaitTermination(30, TimeUnit.MINUTES)) {
            log.info("Mining completed!");
        } else {
            log.info("Mining was not completed correctly");
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
}
