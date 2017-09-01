package ru.beetlewar.strategy.contract;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MatchId {
    public final long value;

    @JsonCreator
    public MatchId(@JsonProperty("value") long value){
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MatchId matchId = (MatchId) o;

        return value == matchId.value;
    }

    @Override
    public int hashCode() {
        return (int) (value ^ (value >>> 32));
    }

    @Override
    public String toString() {
        return Long.toString(value);
    }
}
