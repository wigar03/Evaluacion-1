package org.uam.sistemadematricula.models;

import java.time.LocalDate;

/**
 * Modelo que representa a un Estudiante en el Sistema de Matrícula.
 * Basado en los requerimientos del Caso 1: Centro Nicaragüense de Formación Tecnológica.
 */
public class Estudiante {

    private String nombres;
    private String apellidos;
    private String usuario;
    private String contrasenia;
    private LocalDate fechaNacimiento;
    private String departamento;
    private String curso;
    private String modalidad;
    private String horario;
    private boolean aceptoNormas;

    /**
     * Constructor por defecto
     */
    public Estudiante() {
    }

    /**
     * Constructor completo con todos los atributos requeridos
     *
     * @param nombres          Nombres del estudiante
     * @param apellidos        Apellidos del estudiante
     * @param usuario          Nombre de usuario (mínimo 5 caracteres)
     * @param contrasenia      Contraseña del estudiante (mínimo 8 caracteres)
     * @param fechaNacimiento  Fecha de nacimiento seleccionada en DatePicker
     * @param departamento     Departamento de procedencia
     * @param curso            Curso seleccionado (Programación, Excel, Redes, Diseño Gráfico)
     * @param modalidad        Modalidad seleccionada (Presencial o Virtual)
     * @param horario          Horario de clases asignado o seleccionado
     * @param aceptoNormas     Indica si aceptó las normas institucionales
     */
    public Estudiante(String nombres, String apellidos, String usuario, String contrasenia,
                      LocalDate fechaNacimiento, String departamento, String curso,
                      String modalidad, String horario, boolean aceptoNormas) {
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.usuario = usuario;
        this.contrasenia = contrasenia;
        this.fechaNacimiento = fechaNacimiento;
        this.departamento = departamento;
        this.curso = curso;
        this.modalidad = modalidad;
        this.horario = horario;
        this.aceptoNormas = aceptoNormas;
    }

    // ==========================================
    // Métodos de acceso (Getters y Setters)
    // ==========================================

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    /**
     * Método de conveniencia para la columna "Nombre Completo" en el TableView
     *
     * @return Nombres y apellidos concatenados
     */
    public String getNombreCompleto() {
        String n = nombres != null ? nombres.trim() : "";
        String a = apellidos != null ? apellidos.trim() : "";
        return (n + " " + a).trim();
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public String getModalidad() {
        return modalidad;
    }

    public void setModalidad(String modalidad) {
        this.modalidad = modalidad;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public boolean isAceptoNormas() {
        return aceptoNormas;
    }

    public void setAceptoNormas(boolean aceptoNormas) {
        this.aceptoNormas = aceptoNormas;
    }

    @Override
    public String toString() {
        return "Estudiante{" +
                "nombreCompleto='" + getNombreCompleto() + '\'' +
                ", usuario='" + usuario + '\'' +
                ", departamento='" + departamento + '\'' +
                ", curso='" + curso + '\'' +
                ", modalidad='" + modalidad + '\'' +
                ", horario='" + horario + '\'' +
                ", fechaNacimiento=" + fechaNacimiento +
                ", aceptoNormas=" + aceptoNormas +
                '}';
    }
}
