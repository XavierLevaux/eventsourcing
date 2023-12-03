package be.xl.eventsourcing.eventstore.candle;

import be.xl.eventsourcing.model.DomainEventVersionGenerator;
import be.xl.eventsourcing.model.DomainEvents;
import be.xl.eventsourcing.model.EventSourcedAggregateRoot;
import be.xl.eventsourcing.model.Version;
import org.jmolecules.ddd.types.AggregateRoot;

import java.util.List;

import static be.xl.eventsourcing.model.DomainEventVersionGenerator.domainEventVersionGenerator;

public class Candle implements AggregateRoot<Candle, CandleId>, EventSourcedAggregateRoot<Candle, CandleId> {
    private final CandleId candleId;
    public final Version version;
    public final boolean lit;

    public Candle(CandleId candleId, Version version, boolean lit) {
        this.candleId = candleId;
        this.version = version;
        this.lit = lit;
    }

    @Override
    public Version getVersion() {
        return version;
    }

    @Override
    public CandleId getId() {
        return candleId;
    }

    public DomainEvents<Candle, CandleId> lit() {
        DomainEventVersionGenerator versionGenerator = domainEventVersionGenerator(version);
        return new DomainEvents<>(candleId, versionGenerator.getAggregateVersion(),
                List.of(
                        new CandleLit(candleId.id(), versionGenerator.nextVersion())
                ));
    }
}
