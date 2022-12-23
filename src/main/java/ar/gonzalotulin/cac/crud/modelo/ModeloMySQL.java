/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ar.gonzalotulin.cac.crud.modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Gonzalo
 */
public class ModeloMySQL implements Modelo {
        
    private static final String GET_ALL_QUERY = "SELECT * FROM jugadores";
    private static final String GET_BY_ID_QUERY = "SELECT * FROM jugadores WHERE id = ?"; //Le pongo un simbolo de interrogacion porque el id que puede tomar es variable
    private static final String ADD_QUERY = "INSERT INTO jugadores VALUES(null, ?, ?, ?, ?, ?)";
    private static final String UPDATE_QUERY = "UPDATE jugadores SET nombre=?, apellido=?, mail=?, fechaNac=?, fotobase64=? WHERE id=?";
    private static final String DELETE_QUERY = "DELETE FROM jugadores WHERE id = ?";
    
    
    @Override
    public List<Jugador> getJugadores() {
        List<Jugador> lista = new ArrayList<>();
        try(Connection con = Conexion.getConnection();
            PreparedStatement ps = con.prepareStatement(GET_ALL_QUERY);
            ResultSet rs = ps.executeQuery();){
            while (rs.next()){
                lista.add(rsToJugador (rs));
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Error de SQL", ex);
        }catch (Exception ex) {
            throw new RuntimeException("Error al obtener lista de jugadores", ex);
        }
        
        return lista;
    }

    @Override
    public Jugador getJugador(int id) {
        Jugador jugador = null;
        try(Connection con = Conexion.getConnection();
            PreparedStatement ps = con.prepareStatement(GET_BY_ID_QUERY);
            ){
            ps.setInt(1, id); //El primer argumento es la posicion (comienza a contar desde 1), si tengo mas de un simbolo de interrogacion
            try(ResultSet rs = ps.executeQuery()){
                rs.next();
                jugador = rsToJugador(rs);
            }
            /*while (rs.next()){
                lista.add(rsToJugador (rs));
            }*/
        }catch (SQLException ex) {
            throw new RuntimeException("Error de SQL", ex);
        }catch (Exception ex) {
            throw new RuntimeException("Error al obtener lista de jugadores", ex);
        }
        
        return jugador;
    }

    @Override
    public int addJugador(Jugador jugador) {
        int retorno;
        try(Connection con = Conexion.getConnection();
            PreparedStatement ps = con.prepareStatement(ADD_QUERY);){
            fillPreparedStatement(ps, jugador);
            retorno = ps.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException("Error de SQL", ex);
        } catch (Exception ex) {
                throw new RuntimeException("Error al agregar jugador", ex);
            }
        return retorno;
    }

    @Override
    public int updateJugador(Jugador jugador) {
        int retorno;
        try(Connection con = Conexion.getConnection();
            PreparedStatement ps = con.prepareStatement(UPDATE_QUERY);){
            fillPreparedStatement(ps, jugador);
            ps.setInt(6, jugador.getId());
            retorno = ps.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException("Error de SQL", ex);
        } catch (Exception ex) {
                throw new RuntimeException("Error al actualizar jugador", ex);
            }
        return retorno;
    }

    @Override
    public int removeJugador(int id){
        int retorno;
        try(Connection con = Conexion.getConnection();
            PreparedStatement ps = con.prepareStatement(DELETE_QUERY);){
            ps.setInt(1, id);
            retorno = ps.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException("Error de SQL", ex);
        } catch (Exception ex) {
                throw new RuntimeException("Error al borrar jugador", ex);
            }
        return retorno;
        }
    
    private void fillPreparedStatement(PreparedStatement ps, Jugador jug) throws SQLException{
        ps.setString(1, jug.getNombre());
        ps.setString(2, jug.getApellido()); 
        ps.setString(3, jug.getMail());
        ps.setString(4, jug.getFechaNacimiento());
        ps.setString(5, jug.getFoto()); 
    }
    private Jugador rsToJugador(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String nombre = rs.getString("nombre");
        String apellido = rs.getString("apellido");
        String mail = rs.getString("mail");
        String fechaNac = rs.getString("fechaNac");
        String fotoBase64 = rs.getString("fotoBase64");
        return new Jugador(id, nombre, apellido, mail, fechaNac, fotoBase64);
    }
}
