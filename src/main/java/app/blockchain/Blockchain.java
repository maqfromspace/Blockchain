package app.blockchain;


import app.entities.Block;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

/**
 * Blockchain
 */

public class Blockchain {
    LinkedList<Block> blocks;

    public Blockchain() {
        this.blocks = new LinkedList<>();
    }


    /**
     * Method adds block in blockchain
     * @param block - block
     */
    public void addBlock(Block block) {
        blocks.add(block);
    }

    /**
     * Method prints blocks
     */
    public void printBlockchain() {
        blocks.forEach(System.out::println);
    }

    public boolean isValid() {
        if(blocks.size() == 0 || blocks.size() == 1)
            return true;
        for(int i = 1; i < blocks.size(); i++) {
            String curBlockHash = blocks.get(i).getHashOfThePreviousBlock();
            String prevBlockHash = blocks.get(i - 1).getHashOfTheCurrentBlock();
            if(!curBlockHash.equals(prevBlockHash)) {
                System.out.println(i);
                System.out.println(curBlockHash);
                System.out.println(prevBlockHash);
                return false;
            }
        }
        return true;
    }

    public List<Block> getBlocks() {
        return blocks;
    }

    public void serialize() {
        try
        {
            FileOutputStream fos = new FileOutputStream("blockchain");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(blocks);
            oos.close();
            fos.close();
        }
        catch (IOException ioe)
        {
            System.out.println("Failed to serialize blocks from file");

        }
    }

    public boolean deserialize() {
        try
        {
            FileInputStream fis = new FileInputStream("blockchain");
            ObjectInputStream ois = new ObjectInputStream(fis);
            blocks = (LinkedList<Block>) ois.readObject();
            ois.close();
            fis.close();
            return true;
        }
        catch (IOException | ClassNotFoundException exception)
        {
            System.out.println("Failed to deserialize blocks from file");
            return false;
        }
    }
}
