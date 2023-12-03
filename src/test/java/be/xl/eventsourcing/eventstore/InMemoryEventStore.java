package be.xl.eventsourcing.eventstore;

import be.xl.eventsourcing.model.AggregateIdentifier;
import be.xl.eventsourcing.model.DomainEvents;
import be.xl.eventsourcing.model.EventSourcedAggregateRoot;
import be.xl.eventsourcing.model.Version;
import io.vavr.Tuple2;
import org.jmolecules.ddd.types.Identifier;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InMemoryEventStore<T extends EventSourcedAggregateRoot<T, ID>, ID extends Identifier & AggregateIdentifier<?>>
        implements EventStore<T, ID> {
    private final Map<ID, Tuple2<Version, DomainEvents<T, ID>>> aggregateEvents = new HashMap<>();

    @Override
    public Optional<DomainEvents<T, ID>> loadEvents(ID aggregateId) {
        return aggregateEvents.containsKey(aggregateId) ? Optional
                .of(aggregateEvents.get(aggregateId)._2) : Optional.empty();
    }

    @Override
    public void saveNewAggregate(ID aggregateId, DomainEvents<T, ID> events) {
        if (aggregateEvents.containsKey(aggregateId)) {
            throw new IllegalStateException("Aggregate already exists ");
        }
        aggregateEvents.put(aggregateId, new Tuple2<>(events.getToAggregateVersion(), events));
    }

    @Override
    public void updateExistingAggregate(ID aggregateId, DomainEvents<T, ID> events) throws StaleAggregateVersionException {
        Tuple2<Version, DomainEvents<T, ID>> storedDomainEvents = Optional.of(aggregateEvents.get(events.aggregateId()))
                .orElseThrow(() -> new IllegalStateException("No event stored for aggregate with ID %s".formatted(aggregateId)));

        if (!storedDomainEvents._1.equals(events.fromAggregateVersion())) {
            throw new StaleAggregateVersionException(aggregateId.getAggregateName(), storedDomainEvents._1, events.fromAggregateVersion());
        }

        aggregateEvents.put(events.aggregateId(), new Tuple2<>(events.getToAggregateVersion(), storedDomainEvents._2.withEvents(events)));
    }
}
