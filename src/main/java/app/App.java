package app;

import app.blockchain.Blockchain;
import app.entities.Block;

import java.io.File;
import java.util.Scanner;

public class App {
    public static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        Blockchain blockchain = new Blockchain();
        File file = new File("blockchain");
        if (file.exists()) {
            System.out.println("Blockchain file found");
            if (blockchain.deserialize() && blockchain.isValid()) {
                System.out.println("Blockchain in file is valid");
                System.out.println("How many blocks do you want to add?");
                int countOfBlocks = scanner.nextInt();
                String prefix = createPrefix();
                int sizeOfBlockchain = blockchain.getBlocks().size();
                String hashOfPreviousBlock = blockchain.getBlocks()
                        .get(sizeOfBlockchain - 1)
                        .getHashOfTheCurrentBlock();
                Block block = new Block(sizeOfBlockchain + 1, hashOfPreviousBlock, prefix);
                for (int i = sizeOfBlockchain + 1; i <= sizeOfBlockchain + countOfBlocks; i++) {
                    blockchain.addBlock(block);
                    block = new Block(i + 1, block.getHashOfTheCurrentBlock(), prefix);
                }
                blockchain.serialize();
                blockchain.printBlockchain();
            } else {
                System.out.println("Blockchain in file is not valid");
            }
        } else {
            System.out.println("Blockchain file not found");
            String prefix = createPrefix();
            System.out.println("How many blocks do you want to add?");
            int countOfBlocks = scanner.nextInt();
            Block block = new Block(1, "0", prefix);
            for (int i = 1; i <= countOfBlocks; i++) {
                blockchain.addBlock(block);
                block = new Block(i + 1, block.getHashOfTheCurrentBlock(), prefix);
            }
            blockchain.serialize();
            blockchain.printBlockchain();
        }
    }

    private static String createPrefix() {
        System.out.println("Enter how many zeros the hash must start with: ");
        int numberOfZeros = scanner.nextInt();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < numberOfZeros; i++) {
            sb.append('0');
        }
        return sb.toString();
    }
}
