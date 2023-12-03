package be.xl.eventsourcing.model;

public interface DomainEvent<AggregateRoot> extends Versioned {

   String getType();

   default boolean isType(String type) {
         return getType().equals(type);
   }
}
