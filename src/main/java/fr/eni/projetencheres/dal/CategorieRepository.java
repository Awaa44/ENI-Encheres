package fr.eni.projetencheres.dal;

import fr.eni.projetencheres.bo.Categorie;

import java.util.ArrayList;
import java.util.List;

public interface CategorieRepository
{
    ArrayList<Categorie> selectAllCategories();
}
