package Meths;
import javax.swing.table.AbstractTableModel;
import java.util.List;
import Meths.BaseDeDatos;

public class PuntajeTableModel extends AbstractTableModel {
    private final List<BaseDeDatos.Puntaje> puntajes;
    private final String[] columnNames = {"Nombre", "Score"};

    public PuntajeTableModel(List<BaseDeDatos.Puntaje> puntajes) {
        this.puntajes = puntajes;
    }

    @Override
    public int getRowCount() {
        return puntajes.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        BaseDeDatos.Puntaje puntaje = puntajes.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return puntaje.getNombre();
            case 1:
                return puntaje.getScore();
            default:
                return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }
}
