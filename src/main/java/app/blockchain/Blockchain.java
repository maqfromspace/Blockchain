package app.blockchain;


import app.entities.Block;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.LinkedList;

/**
 * Blockchain
 */
@Slf4j
public class Blockchain {
    @Getter
    LinkedList<Block> blocks;
    private static Blockchain blockchain;

    private Blockchain() {
        blocks = new LinkedList<>();
    }

    public static Blockchain getInstance() {
        if (blockchain == null) {
            blockchain = new Blockchain();
        }
        return blockchain;
    }


    /**
     * Method adds block in blockchain
     *
     * @param block - block
     */
    public void addBlock(Block block) {
        blocks.add(block);
    }

    /**
     * Method prints blocks
     */
    public void printBlockchain() {
        for (Block block : blocks) {
            log.info(String.valueOf(block));
        }
    }

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
}
