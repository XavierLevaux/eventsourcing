package be.xl.eventsourcing.model;

import org.jmolecules.ddd.types.AggregateRoot;

public interface EventSourcedAggregateRoot<T extends org.jmolecules.ddd.types.AggregateRoot<T,ID>, ID extends org.jmolecules.ddd.types.Identifier>
        extends Versioned, AggregateRoot<T, ID> {
}
