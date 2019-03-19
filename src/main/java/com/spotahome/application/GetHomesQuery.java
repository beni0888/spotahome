package com.spotahome.application;

import java.util.Objects;

public final class GetHomesQuery {

    public final String sortedBy;

    public GetHomesQuery(String sortedBy) {
        this.sortedBy = sortedBy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GetHomesQuery query = (GetHomesQuery) o;
        return Objects.equals(sortedBy, query.sortedBy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sortedBy);
    }
}
