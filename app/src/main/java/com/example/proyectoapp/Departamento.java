package com.example.proyectoapp;

public class Departamento {

    private String inombre;
    private String apellido;
    private String email;
    private int id;

    public String getInombre() {
        return inombre;
    }

    public void setInombre(String inombre) {
        this.inombre = inombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Departamento{" +
                "inombre=" + inombre +
                ", apellido=" + apellido +
                ", email=" + email +
                ", id=" + id +
                '}';
    }
}
