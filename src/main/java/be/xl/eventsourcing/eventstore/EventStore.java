package be.xl.eventsourcing.eventstore;

import be.xl.eventsourcing.Port;
import be.xl.eventsourcing.model.AggregateIdentifier;
import be.xl.eventsourcing.model.DomainEvents;
import be.xl.eventsourcing.model.EventSourcedAggregateRoot;

import java.util.Optional;

@Port
public interface EventStore<T extends EventSourcedAggregateRoot<T, ID>, ID extends AggregateIdentifier<?>> {


   Optional<DomainEvents<T, ID>> loadEvents(ID aggregateId);

   void saveNewAggregate(ID aggregateId, DomainEvents<T, ID> events);

   void updateExistingAggregate(ID aggregateId, DomainEvents<T, ID> events)
       throws StaleAggregateVersionException;
}
