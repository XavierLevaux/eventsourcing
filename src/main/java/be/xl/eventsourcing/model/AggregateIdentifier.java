package be.xl.eventsourcing.model;

import org.jmolecules.ddd.types.Identifier;

public interface AggregateIdentifier<T> extends Identifier {
   String getAggregateName();
   T id();
}
