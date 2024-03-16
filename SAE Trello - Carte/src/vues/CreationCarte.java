package vues;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import modeles.Carte;
import modeles.Tag;

import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class CreationCarte extends JFrame implements ActionListener {

    private JTextField nomTextField;
    private JTextField descriptionTextField;
    private JTextField dateDebutTextField;
    private JTextField dateFinTextField;
    private JButton creerButton;
    private Carte carte;
    private ArrayList<Carte> listeCarte = new ArrayList<Carte>();
    private JComboBox<String> couleurComboBox;
   
    String[] couleurs = {"Rouge", "Vert", "Bleu"};
    String[] codesCouleurs = {"#FF0000", "#00FF00", "#0000FF"};
    
    
    public CreationCarte(Carte carte) {
        this.carte = carte;

        // Configuration de la fenêtre
        setTitle("Création de carte");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLayout(new BorderLayout());
        JPanel panel = new JPanel(new GridLayout(5, 2, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        String[] couleurs = {"Rouge", "Vert", "Bleu"};
        couleurComboBox = new JComboBox<>(couleurs);
        
        
        
        // Création des composants
        JLabel nomLabel = new JLabel("Nom :");
        nomTextField = new JTextField(20);
        JLabel descriptionLabel = new JLabel("Description :");
        descriptionTextField = new JTextField(20);
        JLabel dateDebutLabel = new JLabel("Date de début :");
        dateDebutTextField = new JTextField(20);
        JLabel dateFinLabel = new JLabel("Date de fin :");
        dateFinTextField = new JTextField(20);
        creerButton = new JButton("Créer");
        creerButton.addActionListener(this);

        // Ajout des composants au panneau
        panel.add(nomLabel);
        panel.add(nomTextField);
        panel.add(descriptionLabel);
        panel.add(descriptionTextField);
        panel.add(dateDebutLabel);
        panel.add(dateDebutTextField);
        panel.add(dateFinLabel);
        panel.add(dateFinTextField);
        panel.add(new JLabel("Couleur :"));
        panel.add(couleurComboBox);
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(creerButton);

        add(panel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Charger les cartes précédentes
        String cheminAcces = "C:\\Users\\Enzo\\eclipse-workspace\\SAE Trello\\src\\ListeCarte.txt"; // Spécifier le chemin d'accès
        chargerCartes(cheminAcces);

        // Affichage des cartes précédentes
        afficherCartes();

        // Affichage de la fenêtre
        pack();
        setVisible(true);
    }

    private void clearFields() {
        nomTextField.setText("");
        descriptionTextField.setText("");
        dateDebutTextField.setText("");
        dateFinTextField.setText("");
    }

    private boolean isValidDate(String dateStr) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setLenient(false);

        try {
            Date date = dateFormat.parse(dateStr);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    private boolean isDateBeforeOrEqual(String date1Str, String date2Str) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setLenient(false);

        try {
            Date date1 = dateFormat.parse(date1Str);
            Date date2 = dateFormat.parse(date2Str);
            return date1.before(date2) || date1.equals(date2);
        } catch (ParseException e) {
            return false;
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == creerButton) {
            String nom = nomTextField.getText();
            String description = descriptionTextField.getText();
            String dateDebut = dateDebutTextField.getText();
            String dateFin = dateFinTextField.getText();

            // Vérifier si les champs sont vides
            if (nom.isEmpty() || description.isEmpty() || dateDebut.isEmpty() || dateFin.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs.", "Champs vides", JOptionPane.ERROR_MESSAGE);
                return; // Sortir de la méthode actionPerformed si un champ est vide
            }

            // Rechercher une carte existante avec le même nom
            for (Carte c : listeCarte) {
                if (c.getNom().equals(nom)) {
                    JOptionPane.showMessageDialog(this, "Une carte avec le même nom existe déjà.", "Erreur de nom", JOptionPane.ERROR_MESSAGE);
                    return; // Sortir de la méthode actionPerformed si une carte avec le même nom existe
                }
            }

            // Valider les dates
            if (!isValidDate(dateDebut) || !isValidDate(dateFin)) {
                JOptionPane.showMessageDialog(this, "Veuillez entrer des dates valides au format 'dd/MM/yyyy'.", "Erreur de date", JOptionPane.ERROR_MESSAGE);
                return; // Sortir de la méthode actionPerformed si les dates ne sont pas valides
            }

            // Vérifier si la date de début est antérieure à la date de fin
            if (!isDateBeforeOrEqual(dateDebut, dateFin)) {
                JOptionPane.showMessageDialog(this, "La date de début doit être antérieure à la date de fin.", "Erreur de date", JOptionPane.ERROR_MESSAGE);
                return; // Sortir de la méthode actionPerformed si la condition n'est pas respectée
            }

            
            String couleur = codesCouleurs[couleurComboBox.getSelectedIndex()];
            // Créer une nouvelle carte avec les données saisies
            Carte nouvelleCarte = new Carte(nom, description, dateDebut, dateFin);
            

            try {
                nouvelleCarte.setTag(new Tag(nom, couleur));  // Ajouter le tag à la carte
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erreur lors de la création du tag : " + ex.getMessage(), "Erreur de création du tag", JOptionPane.ERROR_MESSAGE);
                return; // Sortir de la méthode actionPerformed en cas d'erreur
            }

        

            // Ajouter la nouvelle carte à la liste des cartes
            listeCarte.add(nouvelleCarte);

            // Effacer les champs de saisie
            clearFields();

            // Sauvegarder les cartes dans un fichier
            String cheminAcces = "C:\\Users\\Enzo\\eclipse-workspace\\SAE Trello\\src\\ListeCarte.txt"; // Spécifier le chemin d'accès
            sauvegarderCartes(cheminAcces);

            // Afficher un message de succès
            JOptionPane.showMessageDialog(this, "La carte a été créée et sauvegardée avec succès.", "Création et sauvegarde réussies", JOptionPane.INFORMATION_MESSAGE);
        
        
        
        
        }
    }

    private void sauvegarderCartes(String cheminAcces) {
        try {
            FileWriter writer = new FileWriter(cheminAcces);

            for (Carte c : listeCarte) {
                writer.write(c.getId() + ";" + c.getNom() + ";" + c.getDescription() + ";" + c.getDateDebut() + ";" + c.getDateFin() + "\n");
            }

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void chargerCartes(String cheminAcces) {
        try {
            FileReader reader = new FileReader(cheminAcces);
            BufferedReader bufferedReader = new BufferedReader(reader);

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] elements = line.split(";");
                if (elements.length == 5) {
                    int id = Integer.parseInt(elements[0]);
                    String nom = elements[1];
                    String description = elements[2];
                    String dateDebut = elements[3];
                    String dateFin = elements[4];

                    Carte nouvelleCarte = new Carte(nom, description, dateDebut, dateFin);
                    nouvelleCarte.setId(id);

                    listeCarte.add(nouvelleCarte);
                }
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void afficherCartes() {
        for (Carte carte : listeCarte) {
            System.out.println(carte.toString());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Carte carte = new Carte();
            CreationCarte creationCarte = new CreationCarte(carte);
        });
    }
}
