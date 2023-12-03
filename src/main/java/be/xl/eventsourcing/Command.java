package be.xl.eventsourcing;

import org.jmolecules.ddd.types.Identifier;

@Port
public interface Command {
    Identifier aggregateId();
}
