package fr.xcompta.core.xcontext.dao;

import java.time.LocalDate;
import java.util.List;

import fr.xcompta.core.date.exception.DateInvalideException;

public interface extractableParametrable<TParameter, TOutput> {
	List<TOutput> getAll(TParameter comptaOvjet)
			throws DateInvalideException, fr.xcompta.core.date.exception.DateInvalideException;
	List<TOutput> getSince(TParameter  objetCompta, LocalDate begin) throws DateInvalideException, fr.xcompta.core.date.exception.DateInvalideException;
	List<TOutput> getTo(TParameter objetCompta,LocalDate end) throws DateInvalideException, fr.xcompta.core.date.exception.DateInvalideException;
	List<TOutput> getBetween(TParameter objetCompta, LocalDate begin, LocalDate end) throws DateInvalideException, fr.xcompta.core.date.exception.DateInvalideException;
}
