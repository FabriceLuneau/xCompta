package fr.xcompta.core.xcontext.dao;

import java.time.LocalDate;
import java.util.List;

public interface Extractable<T> {
	List<T> getAll();

	List<T> getSince(LocalDate begin);

	List<T> getTo(LocalDate end);

	List<T> getBetween(LocalDate begin, LocalDate end);
}