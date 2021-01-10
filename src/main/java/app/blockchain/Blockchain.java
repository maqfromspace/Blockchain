package app.blockchain;


import app.entities.Block;

import java.util.Arrays;

/**
 * Blockchain
 */
public class Blockchain {
    Block[] blocks;
    int size;

    public Blockchain(int size) {
        this.size = size;
        this.blocks = new Block[size];
    }

    /**
     * Method finds first empty cell for block
     * @return empty cell index or -1 if array is full
     */
    private int findEmptyCellIndex() {
        for (int i = 0; i <= blocks.length; i++) {
            if (blocks[i] == null)
                return i;
        }
        return -1;
    }

    /**
     * Method adds block in blockchain
     * @param block - block
     */
    public void addBlock(Block block) {
        int idx = findEmptyCellIndex();

        if (idx != -1)
            blocks[idx] = block;
    }

    /**
     * Method prints blocks
     */
    public void printBlockchain() {
        Arrays.stream(blocks).forEach(System.out::println);
    }

    public boolean validate() {
        for(int i = 0; i < blocks.length - 1; i++) {
            if(!blocks[i].getHashOfTheCurrentBlock().equals(blocks[i+1].getHashOfThePreviousBlock()))
                return false;
        }
        return true;
    }
}
