/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aglfx.model.dao;

import aglfx.model.bean.Material;
import aglfx.model.bean.Pallet;
import connection.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Point2D;
import javax.swing.JOptionPane;

/**
 *
 * @author Usuário
 */
public class PalletDAO {
    private static final String COL_ID = "id";
    private static final String COL_BLOCO = "bloco";
    private static final String COL_X = "x";
    private static final String COL_Y = "y";
    private static final String COL_COD = "cod";
    
        public void create(Pallet p) {
        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = connection.prepareStatement("SELECT * FROM pallets WHERE bloco LIKE ? AND cod LIKE ?");
            stmt.setString(1, p.getBloco());
            stmt.setString(2, p.getCod());
            rs = stmt.executeQuery();
            
            if(!rs.first()){
                
                double x = p.getCoordenada().getX();
                double y = p.getCoordenada().getY();
                
                stmt = null;
                stmt = connection.prepareStatement("INSERT INTO pallets(bloco, x, y, cod) VALUES(?, ?, ?, ?)");
                stmt.setString(1, p.getBloco());
                stmt.setDouble(2, x);
                stmt.setDouble(3, y);
                stmt.setString(4, p.getCod());
            
                stmt.executeUpdate();
            }else{
                JOptionPane.showMessageDialog(null, "O Pallet cadastrado já existe!");
                return;
            }
            
            JOptionPane.showMessageDialog(null, "Salvo com sucesso!");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao salvar: "+ex);
        }finally{
            ConnectionFactory.closeConnection(connection, stmt);
        }
    }
    
    public List<Pallet> read(String bloco){
        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        List<Pallet> pallets = new ArrayList<>();
        
        try {
            stmt = connection.prepareStatement("SELECT * FROM pallets WHERE bloco = ?");
            stmt.setString(1, bloco);
            rs = stmt.executeQuery();
            
            while(rs.next()){
                Pallet p = new Pallet();
                
                p.setBloco(rs.getString(COL_BLOCO));
                p.setCoordenada(new Point2D(rs.getDouble(COL_X), rs.getDouble(COL_Y)));
                p.setId(rs.getInt(COL_ID));
                p.setCod(rs.getString(COL_COD));
                                
                pallets.add(p);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao carregar: "+ex);
        }finally{
            ConnectionFactory.closeConnection(connection, stmt, rs);
        }        
        return pallets;
    }
    
    public void update(Pallet p) {
        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = connection.prepareStatement("UPDATE pallets SET cod = ?, bloco = ?, x = ?, y = ? WHERE id = ?");            
            stmt.setString(1, p.getCod());
            stmt.setString(2, p.getBloco());
            stmt.setDouble(3, p.getCoordenada().getX());
            stmt.setDouble(4, p.getCoordenada().getY());
            stmt.setInt(5, p.getId());
            
            stmt.executeUpdate();
            
            JOptionPane.showMessageDialog(null, "Atualizado com sucesso!");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar: "+ex);
        }finally{
            ConnectionFactory.closeConnection(connection, stmt);
        }
    }
    
    public void delete(Pallet p) {
        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = connection.prepareStatement("DELETE FROM pallets WHERE id = ?");   
            stmt.setInt(1, p.getId());
            
            stmt.executeUpdate();
            
            JOptionPane.showMessageDialog(null, "Excluído com sucesso!");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao excluir: "+ex);
        }finally{
            ConnectionFactory.closeConnection(connection, stmt);
        }
    }
    
    public List<Pallet> search(String descricao, String bloco){
        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        List<Pallet> pallets = new ArrayList<>();
        
        try {
            stmt = connection.prepareStatement("SELECT * FROM pallets WHERE bloco = ? and (cod LIKE ?)");
            stmt.setString(1, bloco);
            stmt.setString(2, descricao+"%");
            rs = stmt.executeQuery();
            
            while(rs.next()){
                Pallet p = new Pallet();
                
                p.setId(rs.getInt(COL_ID));
                p.setBloco(rs.getString(COL_BLOCO));
                p.setCod(rs.getString(COL_COD));
                p.setCoordenada(new Point2D(rs.getDouble(COL_X), rs.getDouble(COL_Y)));
                
                pallets.add(p);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao carregar: "+ex);
        }finally{
            ConnectionFactory.closeConnection(connection, stmt, rs);
        }        
        return pallets;
    }
}
