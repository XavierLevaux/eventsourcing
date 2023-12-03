package be.xl.eventsourcing.eventstore;

import be.xl.eventsourcing.model.DomainEvent;
import org.jmolecules.ddd.types.Identifier;

public interface EventStream<T> extends Iterable<DomainEvent<T>> {

   Identifier getAggregateId();

   String getAggregateName();
}
