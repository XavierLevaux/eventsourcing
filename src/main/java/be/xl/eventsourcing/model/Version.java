package be.xl.eventsourcing.model;

public record Version(long version) {
   public static Version initialVersion() {
      return new Version(0);
   }

   public static Version version(long version) {
      return new Version(version);
   }

   public Version nextVersion() {
      return new Version(version + 1);
   }

   @Override
   public String toString() {
      return Long.toString(version);
   }
}
