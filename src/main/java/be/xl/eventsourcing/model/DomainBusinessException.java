package be.xl.eventsourcing.model;

public class DomainBusinessException extends RuntimeException {
   @SuppressWarnings("rawtypes")
   public final AggregateIdentifier aggregateId;
   public final String message;

   public <T> DomainBusinessException(AggregateIdentifier<T> aggregateId, String message) {
      super(message);
      this.aggregateId = aggregateId;
      this.message = message;
   }
}
