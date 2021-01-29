package app.blockchain;

import app.entities.Block;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class Miner {
    @Getter
    int id;

    public Block miningBlock(Integer sizeOfBlockchain, String hashOfPreviousBlock, String prefix) {
        return new Block(sizeOfBlockchain, hashOfPreviousBlock, prefix, this);
    }
}
