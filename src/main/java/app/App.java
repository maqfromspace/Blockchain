package app;

import app.blockchain.Blockchain;
import app.blockchain.Miner;
import app.entities.Block;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.Scanner;

@Slf4j
public class App {
    public static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        Blockchain blockchain = Blockchain.getInstance();
        Miner miner = new Miner(1);
        File file = new File("blockchain");
        if (file.exists()) {
            log.info("Blockchain file found");
            if (blockchain.deserialize() && blockchain.isValid()) {
                log.info("Blockchain in file is valid");
                log.info("How many blocks do you want to add?");
                int countOfBlocks = scanner.nextInt();
                String prefix = createPrefix();
                int sizeOfBlockchain = blockchain.getBlocks().size();
                String hashOfPreviousBlock = blockchain.getBlocks()
                        .get(sizeOfBlockchain - 1)
                        .getHashOfTheCurrentBlock();
                Block block = miner.miningBlock(sizeOfBlockchain + 1, hashOfPreviousBlock, prefix);
                for (int i = sizeOfBlockchain + 1; i <= sizeOfBlockchain + countOfBlocks; i++) {
                    blockchain.addBlock(block);
                    block = miner.miningBlock(i + 1, block.getHashOfTheCurrentBlock(), prefix);
                }
                blockchain.serialize();
                blockchain.printBlockchain();
            } else {
                log.info("Blockchain in file is not valid");
            }
        } else {
            log.info("Blockchain file not found");
            String prefix = createPrefix();
            log.info("How many blocks do you want to add?");
            int countOfBlocks = scanner.nextInt();
            Block block = miner.miningBlock(1, "0", prefix);
            for (int i = 1; i <= countOfBlocks; i++) {
                blockchain.addBlock(block);
                block = miner.miningBlock(i + 1, block.getHashOfTheCurrentBlock(), prefix);
            }
            blockchain.serialize();
            blockchain.printBlockchain();
        }
    }

    private static String createPrefix() {
        log.info("Enter how many zeros the hash must start with: ");
        int numberOfZeros = scanner.nextInt();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < numberOfZeros; i++) {
            sb.append('0');
        }
        return sb.toString();
    }
}
