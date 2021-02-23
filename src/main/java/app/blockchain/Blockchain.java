package app.blockchain;


import app.entities.Block;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.LinkedList;

/**
 * Blockchain
 */
@Slf4j
public class Blockchain {
    public static final int INCREASE_TIME = 5;
    public static final int DECREASE_TIME = 10;
    private static Blockchain blockchain;
    private final int SIZE;
    private volatile LinkedList<Block> blocks;
    private volatile int numberOfZerosInHash = 0;
    private volatile LinkedList<String> messagePool;

    private Blockchain(int size) {
        blocks = new LinkedList<>();
        SIZE = size;
        messagePool = new LinkedList<>();
    }

    /**
     * Method returns instance of blockchain
     * @param size - size of blockchain
     * @return instance of blockchain
     */
    public static Blockchain getInstance(int size) {
        if (blockchain == null) {
            blockchain = new Blockchain(size);
        }
        return blockchain;
    }


    /**
     * Method adds block in blockchain
     *
     * @param block - block
     */
    public synchronized void addBlock(Block block) {
        if (block.getHashOfThePreviousBlock().equals(getHashOfTheLastBlock()) && block.getHashOfTheCurrentBlock().startsWith(blockchain.getPrefix())) {
            blocks.add(block);
            if(blocks.size() > 1) {
                block.setMessages(messagePool);
                log.info(String.valueOf(block));
                messagePool.clear();
            }
            long timeOfCreation = block.getTimeOfCreation();
            if(timeOfCreation < INCREASE_TIME) {
                numberOfZerosInHash++;
                log.info("Number of zeros in hash was increased to {}\n", numberOfZerosInHash);
            }
            else if(timeOfCreation > DECREASE_TIME && numberOfZerosInHash > 0) {
                numberOfZerosInHash--;
                log.info("Number of zeros in hash was decreased to {}\n", numberOfZerosInHash);
            } else {
                log.info("Number of zeros in hash stays the same\n");
            }
        }
    }

    /**
     * Method adds message in message pool
     *
     * @param message - message from miner
     */
    public synchronized void addMessage(String message) {
        messagePool.add(message);
    }

    /**
     * Method validates blockchain blocks
     *
     * @return blockchain correctness status
     */
    public boolean isValid() {
        if (blocks.size() == 0 || blocks.size() == 1)
            return true;
        for (int i = 1; i < blocks.size(); i++) {
            String curBlockHash = blocks.get(i).getHashOfThePreviousBlock();
            String prevBlockHash = blocks.get(i - 1).getHashOfTheCurrentBlock();
            if (!curBlockHash.equals(prevBlockHash)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Serialize blockchain
     */
    public void serialize() {
        try {
            FileOutputStream fos = new FileOutputStream("blockchain");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(blocks);
            oos.close();
            fos.close();
        } catch (IOException ioe) {
            log.warn("Failed to serialize blocks from file");
        }
    }

    /**
     * Deserialize blockchain
     */
    public boolean deserialize() {
        try {
            FileInputStream fis = new FileInputStream("blockchain");
            ObjectInputStream ois = new ObjectInputStream(fis);
            blocks = (LinkedList<Block>) ois.readObject();
            ois.close();
            fis.close();
            return true;
        } catch (IOException | ClassNotFoundException exception) {
            log.warn("Failed to deserialize blocks from file");
            return false;
        }
    }

    /**
     * Method returns current size of blockchain
     *
     * @return current size of blockchain
     */
    public int getCurrentSize() {
        return blocks.size();
    }

    /**
     * Method returns hash of the last element of blockchain
     *
     * @return hash of the last element of blockchain
     */
    public String getHashOfTheLastBlock() {
        return blocks.isEmpty() ? "0" : blocks.getLast().getHashOfTheCurrentBlock();
    }

    /**
     * Method returns the required prefix in the hash of the new block
     *
     * @return required prefix in the hash of the new block
     */
    public String getPrefix() {
        return blocks.isEmpty() ? "" : "0".repeat(numberOfZerosInHash);
    }

    /**
     * Method returns fullness of blockchain
     *
     * @return fullness of blockchain
     */
    public boolean isFull() {
        return blocks.size() >= SIZE;
    }
}
