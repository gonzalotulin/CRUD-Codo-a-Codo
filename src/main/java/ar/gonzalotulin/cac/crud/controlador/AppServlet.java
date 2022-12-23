/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ar.gonzalotulin.cac.crud.controlador;

import ar.gonzalotulin.cac.crud.modelo.Jugador;
import ar.gonzalotulin.cac.crud.modelo.Modelo;
import ar.gonzalotulin.cac.crud.modelo.ModeloMySQL;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 * @author Gonzalo
 */
@WebServlet(name = "AppServlet", urlPatterns = {"/app"})

public class AppServlet extends HttpServlet {
    private static final String URI_LIST = "WEB-INF/pages/jugadores/listadoJugadores.jsp";
    private static final String URI_REMOVE = "WEB-INF/pages/jugadores/borrarJugador.jsp";
    private static final String URI_EDIT = "WEB-INF/pages/jugadores/editarJugador.jsp";
    private Modelo model;

    @Override
    public void init() throws ServletException {
        this.model = new ModeloMySQL();
    }
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       String laAccion = req.getParameter("accion"); /*Para editar o modificar jugador, creo la request que hace el cliente en el jsp cardsJugadores, y le pongo el nombre
                                    que quiero (en este caso accion, que es el nombre que luego llega por request y tengo que levantar desde el servidor)*/
       String idStr = req.getParameter("id"); /*Levanto el id. El getParameter viene siempre como String*/
       
       int id = (idStr == null ? -1 : Integer.parseInt(idStr)); /*Hago validaciones, si el idStr es null, guardo -1 (para que no se rompa), sino, lo parseo a int*/
       laAccion = (laAccion == null? "" : laAccion);
       
       switch(laAccion){
           case "remove": /*los nombres de las acciones las invento yo en cardsJugadores*/
               req.setAttribute("jugadorABorrar", model.getJugador(id));
               req.getRequestDispatcher(URI_REMOVE).forward(req, resp);
               //model.removeJugador(id);
           case "edit":
               Jugador jug = model.getJugador(id);
               req.setAttribute("jugadorAEditar", jug);
               req.setAttribute("yaTieneFoto", !jug.getFoto().contains("no-face")); //No-face es un jpg que esta en la carpeta assets y es la foto que se agrega por defecto
               req.getRequestDispatcher(URI_EDIT).forward(req, resp);
           default: /*En caso que el parametro de accion no sea ninguno de los esperados, muestro la lista de jugadores*/
               req.setAttribute("listaJugadores", model.getJugadores());/*La lista de jugadores la tengo que llamar igual que en el jsp listado de jugadores, que es a donde va a ir y
                                                   es el nombre que yo le puse en ese jsp*/
               req.getRequestDispatcher(URI_LIST).forward(req, resp); /*Se parte siempre desde la carpeta web pages para direcciones */
       }
         
    }
    /*Para confirmar los cambios (por ejemplo de borrar), tengo que hacerlo mediante un Post, porque tengo que validar un formulario con metodoPost. Ver en borrarjugador.jsp
    que si yo toco "No. mejor no", vuelve a la lista de jugadores sin hacer cambios, y si pongo "Si,borrar" se activa un submit en el formulario que tiene que llevar a un
    doPost que borre efectivamente el jugador (model.removeJugador(id))*/
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String laAccion = req.getParameter("accion"); 
       String idStr = req.getParameter("id");
       
       int id = (idStr == null ? -1 : Integer.parseInt(idStr));
       laAccion = (laAccion == null? "" : laAccion);
       
       switch(laAccion){
           case "delete": //Este nombre es el establecido en el post del formulario 
               model.removeJugador(id);
               break; 
           case "update":
               Jugador jugEditar = model.getJugador(id);
               cargarJugadorSegunParams(jugEditar,req); //En este request estan todos los parametros (nombre, apellido, mail, fechaNac y foto)
               model.updateJugador(jugEditar);
               break;
               
           case "add":
               Jugador jugAgregar = new Jugador();
               cargarJugadorSegunParams(jugAgregar,req);
               model.addJugador(jugAgregar);
               break;
               
       }
       resp.sendRedirect(getServletContext().getContextPath() + "/app");/*Esto redirecciona a /app, para volver a mostrar la lista jugadores luego de hacer cualquier cambio*/
                                                                       /*Como es comun para 'delete', 'update' y 'add', se pone fuera del switch*/
    }
  
   private void cargarJugadorSegunParams(Jugador a, HttpServletRequest request){ //Creo este metodo para remplazar los atributos del Jugador segun los parametros que me llegan por
                                                                               //formulario desde el cliente
       a.setNombre(request.getParameter("nombre"));
       a.setApellido(request.getParameter("apellido"));
       a.setMail(request.getParameter("mail"));
       a.setFechaNacimiento(request.getParameter("fechaNac"));
       a.setFoto(request.getParameter("fotoBase64"));
   }
}
