package app.blockchain;

import app.entities.Block;
import app.security.HashUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalTime;
import java.util.Random;

/**
 * Miner
 */
@Slf4j
@AllArgsConstructor
public class Miner implements Runnable {
    int id;
    Blockchain blockchain;

    @Override
    public void run() {
            log.info("Miner #{} started mining", id);
            while (!blockchain.isFull()) {
                LocalTime creationStartTime = LocalTime.now();
                int magic = new Random().nextInt();
                String hash = HashUtil.applySha256(id + blockchain.getHashOfTheLastBlock() + magic);
                while (!hash.startsWith(blockchain.getPrefix())) {
                    magic = new Random().nextInt();
                    hash = HashUtil.applySha256(id + blockchain.getHashOfTheLastBlock() + magic);
                    if(blockchain.isFull())
                        return;
                }
                Block block = new Block(blockchain.getCurrentSize() + 1, magic, hash, blockchain.getHashOfTheLastBlock(), creationStartTime, id);
                blockchain.addBlock(block);
            }
    }
}
