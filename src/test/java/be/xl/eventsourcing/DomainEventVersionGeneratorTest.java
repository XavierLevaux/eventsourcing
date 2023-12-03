package be.xl.eventsourcing;

import be.xl.eventsourcing.model.DomainEventVersionGenerator;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import static be.xl.eventsourcing.model.Version.version;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class DomainEventVersionGeneratorTest {

   @Test
   void domain_event_version_generator_gives_back_current_aggregate_version() {
      DomainEventVersionGenerator versionGenerator = DomainEventVersionGenerator
          .domainEventVersionGenerator(version(1));

      assertThat(versionGenerator.getAggregateVersion()).isEqualTo(version(1));
   }

   @Test
   void domain_event_version_generator_gives_a_next_version_and_the_current_aggregate_version() {
      DomainEventVersionGenerator versionGenerator = DomainEventVersionGenerator
          .domainEventVersionGenerator(version(1));

      assertThat(versionGenerator.nextVersion()).isEqualTo(version(2));
      assertThat(versionGenerator.getAggregateVersion()).isEqualTo(version(1));
   }

}