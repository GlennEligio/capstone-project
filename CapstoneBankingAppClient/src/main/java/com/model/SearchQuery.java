package com.model;

import java.util.Objects;

public class SearchQuery {
	private String query;
	private String type;

	public SearchQuery(String query, String type) {
		super();
		this.query = query;
		this.type = type;
	}

	public SearchQuery() {
		super();
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "SearchQuery [query=" + query + ", type=" + type + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(query, type);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SearchQuery other = (SearchQuery) obj;
		return Objects.equals(query, other.query) && Objects.equals(type, other.type);
	}

}
