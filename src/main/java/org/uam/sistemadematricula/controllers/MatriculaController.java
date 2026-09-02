package org.uam.sistemadematricula.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import org.uam.sistemadematricula.models.Estudiante;
import org.uam.sistemadematricula.utils.CRUD;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MatriculaController implements CRUD<Estudiante> {

    @FXML
    private TextField txtNombres;

    @FXML
    private TextField txtApellidos;

    @FXML
    private TextField txtUsuario;

    @FXML
    private PasswordField txtContrasenia;

    @FXML
    private DatePicker dpFechaNacimiento;

    @FXML
    private ComboBox<String> cbDepartamento;

    @FXML
    private ListView<String> lvCursos;

    @FXML
    private RadioButton rbPresencial;

    @FXML
    private RadioButton rbVirtual;

    @FXML
    private ToggleGroup tgModalidad;

    @FXML
    private CheckBox chkMatutino;

    @FXML
    private CheckBox chkVespertino;

    @FXML
    private CheckBox chkNocturno;

    @FXML
    private CheckBox chkSabatino;

    @FXML
    private CheckBox chkAceptoNormas;

    @FXML
    private ImageView ivLogo;

    @FXML
    private TableView<Estudiante> tvEstudiantes;

    @FXML
    private TableColumn<Estudiante, String> colNombreCompleto;

    @FXML
    private TableColumn<Estudiante, String> colDepartamento;

    @FXML
    private TableColumn<Estudiante, String> colCurso;

    @FXML
    private TableColumn<Estudiante, String> colModalidad;

    @FXML
    private TableColumn<Estudiante, String> colHorario;

    @FXML
    private TableColumn<Estudiante, LocalDate> colFechaNacimiento;

    private final ObservableList<Estudiante> listaEstudiantes = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        if (colNombreCompleto != null) {
            colNombreCompleto.setCellValueFactory(new PropertyValueFactory<>("nombreCompleto"));
        }
        if (colDepartamento != null) {
            colDepartamento.setCellValueFactory(new PropertyValueFactory<>("departamento"));
        }
        if (colCurso != null) {
            colCurso.setCellValueFactory(new PropertyValueFactory<>("curso"));
        }
        if (colModalidad != null) {
            colModalidad.setCellValueFactory(new PropertyValueFactory<>("modalidad"));
        }
        if (colHorario != null) {
            colHorario.setCellValueFactory(new PropertyValueFactory<>("horario"));
        }
        if (colFechaNacimiento != null) {
            colFechaNacimiento.setCellValueFactory(new PropertyValueFactory<>("fechaNacimiento"));
        }

        if (tvEstudiantes != null) {
            tvEstudiantes.setItems(listaEstudiantes);
            tvEstudiantes.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                if (newSelection != null) {
                    seleccionarEstudiante(newSelection);
                }
            });
        }

        if (cbDepartamento != null) {
            cbDepartamento.setItems(FXCollections.observableArrayList(
                    "Boaco", "Carazo", "Chinandega", "Chontales",
                    "Costa Caribe Norte", "Costa Caribe Sur", "Estelí",
                    "Granada", "Jinotega", "León", "Madriz", "Managua",
                    "Masaya", "Matagalpa", "Nueva Segovia", "Río San Juan", "Rivas"
            ));
        }

        if (lvCursos != null) {
            lvCursos.setItems(FXCollections.observableArrayList(
                    "Programación", "Excel", "Redes", "Diseño Gráfico"
            ));
        }

        if (tgModalidad == null) {
            tgModalidad = new ToggleGroup();
        }
        if (rbPresencial != null) {
            rbPresencial.setToggleGroup(tgModalidad);
        }
        if (rbVirtual != null) {
            rbVirtual.setToggleGroup(tgModalidad);
        }
    }

    @Override
    public void registrar(Estudiante objeto) {
        listaEstudiantes.add(objeto);
    }

    @Override
    public List<Estudiante> mostrar() {
        return listaEstudiantes;
    }

    @Override
    public void actualizar(int index, Estudiante objeto) {
        if (index >= 0 && index < listaEstudiantes.size()) {
            listaEstudiantes.set(index, objeto);
        }
    }

    @Override
    public void eliminar(int index) {
        if (index >= 0 && index < listaEstudiantes.size()) {
            listaEstudiantes.remove(index);
        }
    }

    @FXML
    private void registrarOnClick() {
        if (validarFormulario()) {
            Estudiante estudiante = crearEstudianteDesdeFormulario();
            registrar(estudiante);
            limpiarCampos();
            mostrarAlerta(Alert.AlertType.INFORMATION, "Registro exitoso", "El estudiante ha sido registrado exitosamente.");
        }
    }

    @FXML
    private void actualizarOnClick() {
        int index = tvEstudiantes.getSelectionModel().getSelectedIndex();
        if (index < 0) {
            mostrarAlerta(Alert.AlertType.WARNING, "Selección requerida", "Debe seleccionar un estudiante de la tabla para actualizarlo.");
            return;
        }

        if (validarFormulario()) {
            Estudiante estudianteActualizado = crearEstudianteDesdeFormulario();
            actualizar(index, estudianteActualizado);
            tvEstudiantes.refresh();
            limpiarCampos();
            mostrarAlerta(Alert.AlertType.INFORMATION, "Actualización exitosa", "El estudiante ha sido actualizado exitosamente.");
        }
    }

    @FXML
    private void eliminarOnClick() {
        int index = tvEstudiantes.getSelectionModel().getSelectedIndex();
        if (index < 0) {
            mostrarAlerta(Alert.AlertType.WARNING, "Selección requerida", "Debe seleccionar un estudiante de la tabla para eliminarlo.");
            return;
        }

        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmar eliminación");
        confirmacion.setHeaderText(null);
        confirmacion.setContentText("¿Está seguro de que desea eliminar el registro de este estudiante?");

        Optional<ButtonType> resultado = confirmacion.showAndWait();
        if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
            eliminar(index);
            limpiarCampos();
            mostrarAlerta(Alert.AlertType.INFORMATION, "Eliminación exitosa", "El estudiante ha sido eliminado del sistema.");
        }
    }

    @FXML
    private void limpiarOnClick() {
        limpiarCampos();
    }

    @FXML
    private void onTablaMouseClicked(MouseEvent event) {
        Estudiante seleccionado = tvEstudiantes.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            seleccionarEstudiante(seleccionado);
        }
    }

    private void seleccionarEstudiante(Estudiante estudiante) {
        if (estudiante == null) {
            return;
        }

        txtNombres.setText(estudiante.getNombres());
        txtApellidos.setText(estudiante.getApellidos());
        txtUsuario.setText(estudiante.getUsuario());
        txtContrasenia.setText(estudiante.getContrasenia());
        dpFechaNacimiento.setValue(estudiante.getFechaNacimiento());
        cbDepartamento.setValue(estudiante.getDepartamento());
        lvCursos.getSelectionModel().select(estudiante.getCurso());

        if ("Presencial".equalsIgnoreCase(estudiante.getModalidad())) {
            rbPresencial.setSelected(true);
        } else if ("Virtual".equalsIgnoreCase(estudiante.getModalidad())) {
            rbVirtual.setSelected(true);
        }

        asignarHorarios(estudiante.getHorario());
        chkAceptoNormas.setSelected(estudiante.isAceptoNormas());
    }

    private boolean validarFormulario() {
        if (txtNombres == null || txtNombres.getText() == null || txtNombres.getText().trim().isEmpty()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Campo vacío", "El campo de nombres no puede quedar vacío.");
            if (txtNombres != null) txtNombres.requestFocus();
            return false;
        }

        if (txtApellidos == null || txtApellidos.getText() == null || txtApellidos.getText().trim().isEmpty()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Campo vacío", "El campo de apellidos no puede quedar vacío.");
            if (txtApellidos != null) txtApellidos.requestFocus();
            return false;
        }

        String usuario = txtUsuario != null && txtUsuario.getText() != null ? txtUsuario.getText().trim() : "";
        if (usuario.isEmpty()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Campo vacío", "El campo de usuario no puede quedar vacío.");
            if (txtUsuario != null) txtUsuario.requestFocus();
            return false;
        }

        if (usuario.length() < 5) {
            mostrarAlerta(Alert.AlertType.WARNING, "Validación de usuario", "El nombre de usuario debe tener al menos 5 caracteres.");
            if (txtUsuario != null) txtUsuario.requestFocus();
            return false;
        }

        String contrasenia = txtContrasenia != null && txtContrasenia.getText() != null ? txtContrasenia.getText() : "";
        if (contrasenia.isEmpty()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Campo vacío", "El campo de contraseña no puede quedar vacío.");
            if (txtContrasenia != null) txtContrasenia.requestFocus();
            return false;
        }

        if (contrasenia.length() < 8) {
            mostrarAlerta(Alert.AlertType.WARNING, "Validación de contraseña", "La contraseña debe tener al menos 8 caracteres.");
            if (txtContrasenia != null) txtContrasenia.requestFocus();
            return false;
        }

        if (dpFechaNacimiento == null || dpFechaNacimiento.getValue() == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Campo requerido", "Debe seleccionar la fecha de nacimiento.");
            if (dpFechaNacimiento != null) dpFechaNacimiento.requestFocus();
            return false;
        }

        if (cbDepartamento == null || cbDepartamento.getValue() == null || cbDepartamento.getValue().trim().isEmpty()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Campo requerido", "Debe seleccionar un departamento.");
            if (cbDepartamento != null) cbDepartamento.requestFocus();
            return false;
        }

        if (lvCursos == null || lvCursos.getSelectionModel().getSelectedItem() == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Campo requerido", "Debe elegir un curso de la lista.");
            if (lvCursos != null) lvCursos.requestFocus();
            return false;
        }

        if (tgModalidad == null || tgModalidad.getSelectedToggle() == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Campo requerido", "Debe seleccionar la modalidad (Presencial o Virtual).");
            return false;
        }

        String horarios = obtenerHorariosSeleccionados();
        if (horarios.isEmpty()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Campo requerido", "Debe seleccionar al menos un horario.");
            return false;
        }

        if (chkAceptoNormas == null || !chkAceptoNormas.isSelected()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Normas requeridas", "Debe aceptar las normas para poder matricularse.");
            if (chkAceptoNormas != null) chkAceptoNormas.requestFocus();
            return false;
        }

        return true;
    }

    private Estudiante crearEstudianteDesdeFormulario() {
        String nombres = txtNombres.getText().trim();
        String apellidos = txtApellidos.getText().trim();
        String usuario = txtUsuario.getText().trim();
        String contrasenia = txtContrasenia.getText();
        LocalDate fechaNacimiento = dpFechaNacimiento.getValue();
        String departamento = cbDepartamento.getValue();
        String curso = lvCursos.getSelectionModel().getSelectedItem();
        String modalidad = rbPresencial.isSelected() ? "Presencial" : "Virtual";
        String horario = obtenerHorariosSeleccionados();
        boolean aceptoNormas = chkAceptoNormas.isSelected();

        return new Estudiante(
                nombres,
                apellidos,
                usuario,
                contrasenia,
                fechaNacimiento,
                departamento,
                curso,
                modalidad,
                horario,
                aceptoNormas
        );
    }

    private String obtenerHorariosSeleccionados() {
        List<String> lista = new ArrayList<>();
        if (chkMatutino != null && chkMatutino.isSelected()) {
            lista.add("Matutino");
        }
        if (chkVespertino != null && chkVespertino.isSelected()) {
            lista.add("Vespertino");
        }
        if (chkNocturno != null && chkNocturno.isSelected()) {
            lista.add("Nocturno");
        }
        if (chkSabatino != null && chkSabatino.isSelected()) {
            lista.add("Sabatino");
        }
        return String.join(", ", lista);
    }

    private void asignarHorarios(String horario) {
        deseleccionarHorarios();
        if (horario == null) {
            return;
        }
        if (chkMatutino != null && horario.contains("Matutino")) {
            chkMatutino.setSelected(true);
        }
        if (chkVespertino != null && horario.contains("Vespertino")) {
            chkVespertino.setSelected(true);
        }
        if (chkNocturno != null && horario.contains("Nocturno")) {
            chkNocturno.setSelected(true);
        }
        if (chkSabatino != null && horario.contains("Sabatino")) {
            chkSabatino.setSelected(true);
        }
    }

    private void deseleccionarHorarios() {
        if (chkMatutino != null) chkMatutino.setSelected(false);
        if (chkVespertino != null) chkVespertino.setSelected(false);
        if (chkNocturno != null) chkNocturno.setSelected(false);
        if (chkSabatino != null) chkSabatino.setSelected(false);
    }

    private void limpiarCampos() {
        if (txtNombres != null) txtNombres.clear();
        if (txtApellidos != null) txtApellidos.clear();
        if (txtUsuario != null) txtUsuario.clear();
        if (txtContrasenia != null) txtContrasenia.clear();
        if (dpFechaNacimiento != null) dpFechaNacimiento.setValue(null);
        if (cbDepartamento != null) cbDepartamento.getSelectionModel().clearSelection();
        if (lvCursos != null) lvCursos.getSelectionModel().clearSelection();
        if (tgModalidad != null && tgModalidad.getSelectedToggle() != null) {
            tgModalidad.getSelectedToggle().setSelected(false);
        }
        deseleccionarHorarios();
        if (chkAceptoNormas != null) chkAceptoNormas.setSelected(false);
        if (tvEstudiantes != null) tvEstudiantes.getSelectionModel().clearSelection();
        if (txtNombres != null) txtNombres.requestFocus();
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
