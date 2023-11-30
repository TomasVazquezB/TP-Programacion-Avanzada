package Logica;

import javax.swing.*;
import java.util.List;

public class PersonajeComboBoxModel extends DefaultComboBoxModel<Object> {

    public PersonajeComboBoxModel(List<Personaje> personajes) {
        super(personajes.toArray());
    }

    @Override
    public Object getElementAt(int index) {
        Personaje personaje = (Personaje) super.getElementAt(index);
        return personaje.getNombre() + " - " + personaje.getTipo();
    }
}