package fr.eni.eniEncheres.dal.dao;

import fr.eni.eniEncheres.bo.Retrait;
import fr.eni.eniEncheres.dal.DalException;

import java.sql.SQLException;
import java.util.List;

public interface RetraitDao {

    Retrait selectRetraitById(int retraitId)throws SQLException, DalException;

    Retrait insertRetrait(Retrait retrait) throws SQLException, DalException;

    Retrait updateRetrait(Retrait modifRetrait) throws  DalException;


}
