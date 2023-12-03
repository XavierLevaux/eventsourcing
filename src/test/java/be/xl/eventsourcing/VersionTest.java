package be.xl.eventsourcing;

import be.xl.eventsourcing.model.Version;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

class VersionTest {

   @Test
   void initialVersion() {
      assertThat(Version.initialVersion().version()).isEqualTo(0L);
   }

   @Test
   void version() {
      assertThat(Version.version(3L).version()).isEqualTo(3L);
   }

   @Test
   void nextVersion() {
      assertThat(Version.version(3L).nextVersion().version()).isEqualTo(4L);
   }
}