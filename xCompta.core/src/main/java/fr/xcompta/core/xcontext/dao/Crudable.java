package fr.xcompta.core.xcontext.dao;

import java.util.List;
import java.util.Optional;

import fr.xcompta.core.xcontext.dao.exception.XComptaMiseAJourException;
import fr.xcompta.core.xcontext.dao.exception.XComptaObjetExisteDejaException;
import fr.xcompta.core.xcontext.dao.exception.XComptaObjetIntrouvableException;
import fr.xcompta.core.xcontext.dao.exception.XComptaObjetVerouilleException;
import fr.xcompta.core.xcontext.dao.exception.XComptaSauvegardeException;

public interface Crudable<T, I> {
	public Optional<T> findById(I id) throws XComptaObjetIntrouvableException;

	public List<T> findAll();

	public I save(T object) throws XComptaObjetExisteDejaException, XComptaSauvegardeException;

//	public void saveAlll(List<T listOfObjects) throws XCompta
	public void update(T object)
			throws XComptaObjetVerouilleException, XComptaObjetIntrouvableException, XComptaMiseAJourException;

//	public void updateAll(List<T> listOfObjecss);
	public void delete(T object) throws XComptaObjetVerouilleException, XComptaObjetIntrouvableException;

//	public void deleteAll(List<T> listOfObjects);
	public boolean existsById(I id);
}