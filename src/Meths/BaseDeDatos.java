/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Meths;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BaseDeDatos {

    private String url = "jdbc:mysql://localhost:3306/CubixJump";
    private String usuario = "root";
    private String contraseña = "root";

    public List<Puntaje> obtenerPuntajes() {
        List<Puntaje> puntajes = new ArrayList<>();

        try {
            Connection con = DriverManager.getConnection(url, usuario, contraseña);
            String query = "SELECT Nombre, Score FROM Puntaje";
            PreparedStatement stmt = con.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String nombre = rs.getString("Nombre");
                int score = rs.getInt("Score");
                puntajes.add(new Puntaje(nombre, score));
            }

            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return puntajes;
    }

    public static class Puntaje {
        private String nombre;
        private int score;

        public Puntaje(String nombre, int score) {
            this.nombre = nombre;
            this.score = score;
        }

        public String getNombre() {
            return nombre;
        }

        public int getScore() {
            return score;
        }
    }
}
