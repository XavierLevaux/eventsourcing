package be.xl.eventsourcing.eventstore.candle;


import java.util.UUID;
import be.xl.eventsourcing.model.AggregateIdentifier;

public record CandleId(UUID id) implements AggregateIdentifier<UUID> {

   public static final String AGGREGATE_ROOT_NAME = "eventsourcing.Candle";

   @Override
   public String getAggregateName() {
      return AGGREGATE_ROOT_NAME;
   }
}
