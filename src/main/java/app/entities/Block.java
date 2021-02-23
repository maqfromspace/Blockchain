package app.entities;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

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
    @Getter
    long timeOfCreation;
    @Setter
    List<String> messages;

    public Block(int id, int magicNumber, String hashOfTheCurrentBlock, String hashOfThePreviousBlock, LocalTime creationStartTime, int minerId) {
        this.id = id;
        this.magicNumber = magicNumber;
        this.timeOfCreation = ChronoUnit.SECONDS.between(creationStartTime, LocalTime.now());
        this.timestamp = new Date().getTime();
        this.hashOfTheCurrentBlock = hashOfTheCurrentBlock;
        this.hashOfThePreviousBlock = hashOfThePreviousBlock;
        this.minerId = minerId;
    }

    @Override
    public String toString() {
        return "Block:" +
                "\nCreated by miner: " + minerId +
                "\nId: " + id +
                "\nTimestamp: " + timestamp +
                "\nHash of the previous block:\n" + hashOfThePreviousBlock +
                "\nHash of the block:\n" + hashOfTheCurrentBlock +
                "\nBlock was generating for " + timeOfCreation + " seconds" +
                "\nBlock data: " + messages + "\n";
    }
}
