import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Urkom
 */
public class SupermarketPriceLookupSystem extends Application {

    private Konektor konektor;

    private ListView<String> artikliListView;
    private ObservableList<String> artikli;

    @Override
    public void start(Stage primaryStage) throws ClassNotFoundException {
        konektor = new Konektor();

        artikli = FXCollections.observableArrayList();
        artikliListView = new ListView<>(artikli);
        Button ucitajButton = new Button("Učitaj artikle");
        Button dodajButton = new Button("Dodaj artikal");
        Button pretragaButton = new Button("Pretraga artikla");

        ucitajButton.setOnAction(event -> {
            try {
                ucitajArtikle();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(SupermarketPriceLookupSystem.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        dodajButton.setOnAction(event -> {
            prikaziDodajArtikalProzor();
        });
        pretragaButton.setOnAction(event -> {
            prikaziPretragaArtiklaProzor();
        });

        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(10));
        vbox.getChildren().addAll(artikliListView, ucitajButton, dodajButton, pretragaButton);

        Scene scene = new Scene(vbox, 300, 400);
        primaryStage.setTitle("Artikli GUI");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void prikaziDodajArtikalProzor() {
        Stage dodajArtikalStage = new Stage();
        dodajArtikalStage.setTitle("Dodaj artikal");

        TextField upcTextField = new TextField();
        TextField imeTextField = new TextField();
        TextField cenaTextField = new TextField();
        Button dodajButton = new Button("Dodaj");

        dodajButton.setOnAction(event -> {
            try {
                String upc = upcTextField.getText();
                String ime = imeTextField.getText();
                double cena = Double.parseDouble(cenaTextField.getText());
                Artikal artikal = new Artikal(upc, ime, cena);
                Konektor konektor = new Konektor();
                konektor.unesiArtikal(artikal);
                dodajArtikalStage.close();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(SupermarketPriceLookupSystem.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        VBox dodajArtikalVBox = new VBox(10);
        dodajArtikalVBox.setPadding(new Insets(10));
        dodajArtikalVBox.getChildren().addAll(
                new Label("UPC:"),
                upcTextField,
                new Label("Ime:"),
                imeTextField,
                new Label("Cena:"),
                cenaTextField,
                dodajButton);

        Scene dodajArtikalScene = new Scene(dodajArtikalVBox, 330, 230);
        dodajArtikalStage.setScene(dodajArtikalScene);
        dodajArtikalStage.show();
    }

    private void prikaziPretragaArtiklaProzor() {
        Stage pretragaArtiklaStage = new Stage();
        pretragaArtiklaStage.setTitle("Pretraga artikla");

        TextField kljucTextField = new TextField();
        Button pretraziButton = new Button("Pretraži");

        pretraziButton.setOnAction(event -> {
            try {
                String kljuc = kljucTextField.getText();
                Konektor konektor = new Konektor();
                Artikal artikal = konektor.pronadjiArtikal(kljuc);
                if (artikal != null) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Rezultat pretrage");
                    alert.setHeaderText(null);
                    alert.setContentText("UPC: " + artikal.getUpc()
                            + "\nIme: " + artikal.getIme()
                            + "\nCena: " + artikal.getCena());
                    alert.showAndWait();
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Rezultat pretrage");
                    alert.setHeaderText(null);
                    alert.setContentText("Artikal nije pronađen.");
                    alert.showAndWait();
                }
                pretragaArtiklaStage.close();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(SupermarketPriceLookupSystem.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        VBox pretragaArtiklaVBox = new VBox(10);
        pretragaArtiklaVBox.setPadding(new Insets(10));
        pretragaArtiklaVBox.getChildren().addAll(
                new Label("Unesite ključ artikla:"),
                kljucTextField,
                pretraziButton);

        Scene pretragaArtiklaScene = new Scene(pretragaArtiklaVBox, 330, 150);
        pretragaArtiklaStage.setScene(pretragaArtiklaScene);
        pretragaArtiklaStage.show();
    }

    private void ucitajArtikle() throws ClassNotFoundException {
        artikli.clear();

        Konektor konektor = new Konektor();
        List<Artikal> artikliList = konektor.citajArtikal();

        for (Artikal artikal : artikliList) {
            String info = "UPC: " + artikal.getUpc() + ", Ime: " + artikal.getIme() + ", Cena: " + artikal.getCena();
            artikli.add(info);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
