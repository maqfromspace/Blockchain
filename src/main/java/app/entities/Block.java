package app.entities;

import app.security.HashUtil;

import java.util.Date;

/**
 * Block entity
 */
public class Block {
    int id;
    long timestamp;
    String hashOfThePreviousBlock;
    String hashOfTheCurrentBlock;

    public Block(int id, String hashOfThePreviousBlock) {
        this.id = id;
        this.timestamp = new Date().getTime();
        this.hashOfThePreviousBlock = hashOfThePreviousBlock;
        this.hashOfTheCurrentBlock = HashUtil.applySha256(id + hashOfThePreviousBlock);
    }


    public String getHashOfTheCurrentBlock() {
        return hashOfTheCurrentBlock;
    }

    public String getHashOfThePreviousBlock() {
        return hashOfThePreviousBlock;
    }

    @Override
    public String toString() {
        return "Block:" +
                "\nId: " + id +
                "\nTimestamp: " + timestamp +
                "\nHash of the previous block:\n" + hashOfThePreviousBlock +
                "\nHash of the block:\n" + hashOfTheCurrentBlock + "\n";
    }
}
