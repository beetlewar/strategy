package ru.beetlewar.strategy.contract;

public class MatchId {
    public final long value;

    public MatchId(long value){
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
}
