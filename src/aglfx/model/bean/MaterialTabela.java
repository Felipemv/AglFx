/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aglfx.model.bean;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Usuário
 */
public class MaterialTabela extends AbstractTableModel{
    
    private final int COL_COD = 0;
    private final int COL_NOME = 1;
    private final int COL_QUANT = 2;
    
    private List<Material> materiais;

    public MaterialTabela() {
        materiais = new ArrayList();
    }

    public MaterialTabela(List lista) {
        this();
        materiais.addAll(lista);
    }
    
    @Override
    public int getRowCount() {
        return materiais.size();
    }

    @Override
    public int getColumnCount() {
        return 3;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Material m = materiais.get(rowIndex);
        
        switch(columnIndex){
            case COL_COD:
                return m.getCod();
            case COL_NOME:
                return m.getNome();
            case COL_QUANT:
                return m.getQuant();           
        }
        return "";
    }
    
    @Override
    public String getColumnName(int column){
        switch(column){
            case COL_COD:
                return "Código";
            case COL_NOME:
                return "Nome";
            case COL_QUANT:
                return "Quantidade";           
        }
        return "";
    }
}
