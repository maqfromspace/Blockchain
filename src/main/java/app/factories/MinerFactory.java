package app.factories;

import app.blockchain.Blockchain;
import app.blockchain.Miner;
import lombok.AllArgsConstructor;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@AllArgsConstructor
public class MinerFactory {

    private final Blockchain blockchain;

    public List<Miner> createMiners(int numberOfMiners) {
        return IntStream.rangeClosed(1, numberOfMiners).mapToObj(i -> new Miner(i, blockchain)).collect(Collectors.toCollection(LinkedList::new));
    }
}
