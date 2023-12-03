package be.xl.eventsourcing.eventstore.candle;


import be.xl.eventsourcing.model.DomainEvent;
import be.xl.eventsourcing.model.Version;

import java.util.UUID;

public record CandleExtinguished(UUID candleId, Version version) implements DomainEvent<Candle> {
    @Override
    public String getType() {
        return "Candle-Extinguished";
    }

    @Override
    public Version getVersion() {
        return version;
    }
}
