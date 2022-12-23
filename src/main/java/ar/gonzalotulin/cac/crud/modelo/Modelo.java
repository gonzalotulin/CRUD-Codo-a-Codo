/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ar.gonzalotulin.cac.crud.modelo;
import java.util.List;

/**
 *
 * @author Gonzalo
 */
public interface Modelo {
 /**
     * Devuelve una lista de jugadores existentes
     * @return 
     */
    public List<Jugador> getJugadores();
    
    /**
     * Retorna un jugador por ID
     * @param id el id del jugador a retornar
     * @return El jugador encontrado por ID o null si no existe
     */
    public Jugador getJugador(int id);
    
    /**
     * Agrega un jugador al modelo
     * @param jugador El jugador a agregar
     * @return La cantidad de registros modificados
     */
    public int addJugador(Jugador jugador);
    
    /**
     * Modifica un jugador del modelo
     * @param jugador El jugador que contiene los datos para modificar
     * @return La cantidad de registros modificados
     */
    public int updateJugador(Jugador jugador);
    
    /**
     * Borra un jugador por ID
     * @param id el id del jugador a borrar
     * @return La cantidad de registros modificados
     */
    public int removeJugador(int id);    
}
