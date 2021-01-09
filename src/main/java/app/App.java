package app;

import app.blockchain.Blockchain;
import app.entities.Block;

public class App {
    public static void main(String[] args) {
        Blockchain blockchain = new Blockchain(10);
        Block block = new Block(1, "0");
        for(int i = 1; i <= 10; i++) {
            blockchain.addBlock(block);
            block = new Block(i + 1, block.getHashOfTheCurrentBlock());
        }
        blockchain.printBlockchain();
    }
}
