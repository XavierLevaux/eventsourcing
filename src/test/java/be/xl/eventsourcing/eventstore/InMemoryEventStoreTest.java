package be.xl.eventsourcing.eventstore;

import be.xl.eventsourcing.eventstore.candle.Candle;
import be.xl.eventsourcing.eventstore.candle.CandleExtinguished;
import be.xl.eventsourcing.eventstore.candle.CandleId;
import be.xl.eventsourcing.eventstore.candle.CandleLit;
import be.xl.eventsourcing.model.DomainEvents;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static be.xl.eventsourcing.model.Version.initialVersion;
import static be.xl.eventsourcing.model.Version.version;
import static org.assertj.core.api.Assertions.assertThat;

class InMemoryEventStoreTest {

    private static final UUID CANDLE_ID_VALUE = UUID.randomUUID();
    protected final CandleId CANDLE_ID = new CandleId(CANDLE_ID_VALUE);

    @Nested
    class Given_I_have_domain_events {
        protected final InMemoryEventStore<Candle, CandleId> eventStore = new InMemoryEventStore<>();

        protected DomainEvents<Candle, CandleId> events;

        public Given_I_have_domain_events() {
            events = new DomainEvents<>(CANDLE_ID, initialVersion(), List.of(
                    new CandleLit(CANDLE_ID_VALUE, version(1)
                    )));
        }

        @Nested
        class When_I_save_a_new_aggregate {

            public When_I_save_a_new_aggregate() {
                eventStore.saveNewAggregate(CANDLE_ID, events);
            }

            @Test
            void then_I_can_load_those_events_from_the_store() {
                java.util.Optional<DomainEvents<Candle, CandleId>> domainEvents = eventStore
                        .loadEvents(CANDLE_ID);

                assertThat(domainEvents).isNotEmpty();
                assertThat(domainEvents.get()).containsExactly(
                        new CandleLit(CANDLE_ID_VALUE, version(1))
                );
            }
        }
    }

    @Nested
    class Given_I_have_domain_events_saved {

        protected final InMemoryEventStore<Candle, CandleId> eventStore = new InMemoryEventStore<>();

        public Given_I_have_domain_events_saved() {
            DomainEvents<Candle, CandleId> events = new DomainEvents<>(
                    CANDLE_ID,
                    initialVersion(),
                    List.of(
                            new CandleLit(CANDLE_ID_VALUE, version(1))
                    )
            );
            eventStore.saveNewAggregate(CANDLE_ID, events);
        }

        @Nested
        class When_I_update {

            public When_I_update() {
                DomainEvents<Candle, CandleId> events = new DomainEvents<>(
                        CANDLE_ID,
                        version(1),
                        List.of(
                                new CandleExtinguished(CANDLE_ID_VALUE, version(2))
                        ));
                eventStore.updateExistingAggregate(CANDLE_ID, events);
            }

            @Test
            void then_I_can_load_all_events() {
                Optional<DomainEvents<Candle, CandleId>> domainEvents = eventStore
                        .loadEvents(CANDLE_ID);

                assertThat(domainEvents).isNotEmpty();

                DomainEvents<Candle, CandleId> expectedDomainEvents = new DomainEvents<>(
                        CANDLE_ID,
                        version(2),
                        List.of(
                                new CandleLit(CANDLE_ID_VALUE, version(1)),
                                new CandleExtinguished(CANDLE_ID_VALUE, version(2))
                        ));

                assertThat(domainEvents.get()).isEqualTo(
                        expectedDomainEvents
                );
            }
        }

        @Nested
        class When_I_update_with_stale_version {

            private final DomainEvents<Candle, CandleId> events;

            public When_I_update_with_stale_version() {
                events = new DomainEvents<>(
                        CANDLE_ID,
                        version(0),
                        List.of(
                                new CandleExtinguished(CANDLE_ID_VALUE, version(1)))
                );
            }

            @Test
            void then_I_should_receive_an_error_saying_persisted_version_is_more_recent() {
                StaleAggregateVersionException exception = Assertions.catchThrowableOfType(
                        () -> eventStore.updateExistingAggregate(
                                CANDLE_ID,
                                events
                        ),
                        StaleAggregateVersionException.class
                );

                assertThat(exception.getAggregateVersion()).isEqualTo(1);
                assertThat(exception.getPresentedVersion()).isEqualTo(0);
                assertThat(exception.getAggregateName()).isEqualTo(CANDLE_ID.getAggregateName());
            }

        }
    }
}

