import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Urkom
 */
public class Konektor {

    private Connection connection;

    public Konektor() throws ClassNotFoundException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/supermarket", "root", "");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void unesiArtikal(Artikal artikal) {
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO artikli (vrednost, ime, cena) VALUES (?, ?, ?)");
            statement.setString(1, calculateHash(artikal.getUpc()));
            statement.setString(2, artikal.getIme());
            statement.setDouble(3, artikal.getCena());
            statement.executeUpdate();
            statement.close();

            PreparedStatement hashStatement = connection.prepareStatement("INSERT INTO hashtabela (vrednost, upc) VALUES (?, ?)");
            hashStatement.setString(1, calculateHash(artikal.getUpc()));
            hashStatement.setString(2, artikal.getUpc());
            hashStatement.executeUpdate();
            hashStatement.close();

            System.out.println("Artikal uspešno unet u bazu podataka.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Artikal pronadjiArtikal(String s) {
        Artikal artikal = null;

        try {
            PreparedStatement statement = connection.prepareStatement("SELECT vrednost, ime, cena FROM Kes WHERE vrednost = ?");
            statement.setString(1, calculateHash(s));
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String vrednost = resultSet.getString("vrednost");
                String ime = resultSet.getString("ime");
                double cena = resultSet.getDouble("cena");
                artikal = new Artikal(vrednost, ime, cena);
                System.out.println("procitano iz kesa");
            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (artikal == null) {
            try {
                PreparedStatement statement = connection.prepareStatement("SELECT vrednost, ime, cena FROM artikli WHERE vrednost = ?");
                statement.setString(1, calculateHash(s));
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    String vrednost = resultSet.getString("vrednost");
                    String ime = resultSet.getString("ime");
                    double cena = resultSet.getDouble("cena");
                    artikal = new Artikal(vrednost, ime, cena);
                    System.out.println("nije procitano iz kesa");
                    try {
                        PreparedStatement insertStatement = connection.prepareStatement("INSERT INTO Kes (upc, vrednost, ime, cena) VALUES (?, ?, ?, ?)");
                        insertStatement.setString(1, calculateHash(s));
                        insertStatement.setString(2, vrednost);
                        insertStatement.setString(3, ime);
                        insertStatement.setDouble(4, cena);
                        insertStatement.executeUpdate();
                        insertStatement.close();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (artikal == null) {
            System.out.println("Artikal nije pronađen.");
        }

        return artikal;
    }

    private String calculateHash(String upc) {
        try {
            String input = upc;
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hashBytes = md.digest(input.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Artikal> citajArtikal() {
        List<Artikal> artikli = new ArrayList<>();

        try {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT h.upc, a.ime, a.cena\n"
                    + "FROM hashtabela h\n"
                    + "JOIN Artikli a ON h.vrednost = a.vrednost");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String vrednost = resultSet.getString("upc");
                String ime = resultSet.getString("ime");
                double cena = resultSet.getDouble("cena");
                Artikal artikal = new Artikal(vrednost, ime, cena);
                artikli.add(artikal);
            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return artikli;
    }
}
