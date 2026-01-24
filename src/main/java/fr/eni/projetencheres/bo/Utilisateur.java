package fr.eni.projetencheres.bo;

import java.util.ArrayList;
import java.util.List;

public class Utilisateur {
    private int noUtilisateur;
    private int noUtilisateurGagnant;
    private int idRetrait;
    private String pseudo;
    private String pseudoGagnant;
    private String nom;
    private String prenom;
    private String email;
    private String telephone;
    private String rue;
    private String codePostal;
    private String ville;
    private String motDePasse;
    private Integer credit;
    private boolean administrateur;
    private Retrait retrait;

    private List<ArticleVendu> ventes;
    private List<Enchere> encheres;
    private List<Retrait> retraits;

    public Utilisateur(){

        this.ventes=new ArrayList<>();
        this.encheres=new ArrayList<>();
        this.retraits=new ArrayList<>();
    }

    public Utilisateur(int noUtilisateur, String pseudo, String nom, String prenom, String email, String telephone,
                       String rue, String codePostal, String ville, String motDePasse, Integer credit, boolean administrateur, int idRetrait) {

        this.noUtilisateur = noUtilisateur;
        this.pseudo = pseudo;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.telephone = telephone;
        this.rue = rue;
        this.codePostal = codePostal;
        this.ville = ville;
        this.motDePasse = motDePasse;
        this.credit = credit;
        this.administrateur = administrateur;
        this.idRetrait = idRetrait;
    }

    public int getNoUtilisateur() {
        return noUtilisateur;
    }

    public void setNoUtilisateur(int noUtilisateur) {
        this.noUtilisateur = noUtilisateur;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getRue() {
        return rue;
    }

    public void setRue(String rue) {
        this.rue = rue;
    }

    public String getCodePostal() {
        return codePostal;
    }

    public void setCodePostal(String codePostal) {
        this.codePostal = codePostal;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public Integer getCredit() {
        return credit;
    }


    public void setCredit(Integer credit) {
        this.credit = credit;
    }

    public boolean isAdministrateur() {
        return administrateur;
    }

    public void setAdministrateur(boolean administrateur) {
        this.administrateur = administrateur;
    }

    public List<ArticleVendu> getVente() {
        return ventes;
    }

    public void setVente(List<ArticleVendu> vente) {
        this.ventes = vente;
    }

    public List<Enchere> getEncheres() {
        return encheres;
    }

    public void setEncheres(List<Enchere> encheres) {
        this.encheres = encheres;
    }

    public List<ArticleVendu> getVentes() {
        return ventes;
    }

    public void setVentes(List<ArticleVendu> ventes) {
        this.ventes = ventes;
    }

    public List<Retrait> getRetraits() {
        return retraits;
    }

    public void setRetraits(List<Retrait> retraits) {
        this.retraits = retraits;
    }

    public int getIdRetrait() {
        return idRetrait;
    }

    public void setIdRetrait(int idRetrait) {
        this.idRetrait = idRetrait;
    }

    public Retrait getRetrait() {
        return retrait;
    }

    public void setRetrait(Retrait retrait) {
        this.retrait = retrait;
    }

    public String getPseudoGagnant() {
        return pseudoGagnant;
    }

    public void setPseudoGagnant(String pseudoGagnant) {
        this.pseudoGagnant = pseudoGagnant;
    }

    public int getNoUtilisateurGagnant() {
        return noUtilisateurGagnant;
    }

    public void setNoUtilisateurGagnant(int noUtilisateurGagnant) {
        this.noUtilisateurGagnant = noUtilisateurGagnant;
    }
}