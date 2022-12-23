/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ar.gonzalotulin.cac.crud.modelo;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
/**
 *
 * @author Gonzalo
 */
public class Jugador {
    private int id;
    private String nombre;
    private String apellido;
    private LocalDate fechaNacimiento;
    private String mail;
    private String foto;

    public Jugador() {
    }

    public Jugador(int id) {
        setId(id);
    }

    public Jugador(String nombre, String apellido, String mail, String fechaNacimiento) {
        this(0, nombre, apellido, mail, fechaNacimiento, "");
    }

    public Jugador(int id, String nombre, String apellido, String mail, String fechaNacimiento) {
        this(id, nombre, apellido, mail, fechaNacimiento, "");
    }

    public Jugador(int id, String nombre, String apellido, String mail, String fechaNacimiento, String foto) {
        setId(id);
        setApellido(apellido);
        setNombre(nombre);
        setMail(mail);
        setFechaNacimiento(fechaNacimiento);
        setFoto(foto);
    }

    public void setId(int id) {
        if (id < 0) {
            throw new RuntimeException("Valor para ID inconsistente");
        }
        this.id = id;
    }

    public void setApellido(String apellido) {
        if (apellido == null || apellido.trim().isEmpty()) {
            throw new RuntimeException("No se ha provisto un apellido");
        }
        this.apellido = apellido.trim();
    }

    public void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new RuntimeException("No se ha provisto un nombre");
        }
        this.nombre = nombre.trim();
    }

    public void setMail(String mail) {
        if (mail == null || mail.trim().isEmpty()) {
            throw new RuntimeException("No se ha provisto un mail");
        }
        this.mail = mail.trim();
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        if (fechaNacimiento == null || fechaNacimiento.trim().isEmpty()) {
            throw new RuntimeException("No se ha provisto una fecha de nacimiento");
        }
        try {
            LocalDate posibleFecha = LocalDate.parse(fechaNacimiento.trim());
            if (posibleFecha.isAfter(LocalDate.now())) {
                throw new RuntimeException("La fecha de nacimiento proveída es posterior al día de hoy");
            }
            this.fechaNacimiento = posibleFecha;
        } catch (DateTimeParseException ex) {
            throw new RuntimeException("La fecha de nacimiento proveída no es válida", ex);
        }
    }

    public void setFoto(String foto) {
        if (foto == null || foto.trim().isEmpty()) {
            foto = "assets/no-face.jpg";
        }
        if (!foto.contains("no-face") || this.foto == null || this.foto.contains("no-face")) {
            this.foto = foto.trim();
        }
    }

    public int getId() {
        return id;
    }

    public String getApellido() {
        return apellido;
    }

    public String getNombre() {
        return nombre;
    }

    public String getMail() {
        return mail;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    public String getNombreCompleto() {
        return nombre + " " + apellido;
    }

    public int getEdad() {
        return Period.between(fechaNacimiento, LocalDate.now()).getYears();
    }

    public String getFoto() {
        return foto;
    }

    @Override
    public String toString() {
        return "Jugador{" + "id=" + id + ", apellido=" + apellido + ", nombre=" + nombre + ", fechaNacimiento=" + fechaNacimiento + ", foto=" + foto + '}';
    }
}
