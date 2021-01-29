package app.entities;

import app.blockchain.Miner;
import app.security.HashUtil;
import lombok.Getter;

import java.io.Serializable;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Random;

/**
 * Block entity
 */
public class Block implements Serializable {
    int id;
    long timestamp;
    int magicNumber;
    int minerId;
    @Getter
    String hashOfThePreviousBlock;
    @Getter
    String hashOfTheCurrentBlock;
    long timeOfCreation;

    public Block(int id, String hashOfThePreviousBlock, String prefix, Miner miner) {
        LocalTime creationStartTime = LocalTime.now();
        int magic = new Random().nextInt();
        String hash = HashUtil.applySha256(id + hashOfThePreviousBlock + magic);
        while (!hash.startsWith(prefix)) {
            magic = new Random().nextInt();
            hash = HashUtil.applySha256(id + hashOfThePreviousBlock + magic);
        }

        this.id = id;
        this.magicNumber = magic;
        this.timeOfCreation = ChronoUnit.SECONDS.between(creationStartTime, LocalTime.now());
        this.timestamp = new Date().getTime();
        this.hashOfThePreviousBlock = hashOfThePreviousBlock;
        this.hashOfTheCurrentBlock = hash;
        this.minerId = miner.getId();
    }

    @Override
    public String toString() {
        return "Block:" +
                "\nCreated by miner: " + minerId +
                "\nId: " + id +
                "\nTimestamp: " + timestamp +
                "\nHash of the previous block:\n" + hashOfThePreviousBlock +
                "\nHash of the block:\n" + hashOfTheCurrentBlock +
                "\nBlock was generating for " + timeOfCreation + " seconds" + "\n";
    }
}
