/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aglfx.model.dao;

import connection.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import aglfx.model.bean.Material;

/**
 *
 * @author Usuário
 */
public class MaterialDAO {
    private static final String COL_ID = "id";
    private static final String COL_ID_PROD = "id_prod";
    private static final String COL_COD = "cod";
    private static final String COL_NOME = "nome";
    private static final String COL_QUANT = "quant";
    
    public void create(Material m) {
        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = connection.prepareStatement("SELECT * FROM materiais WHERE nome LIKE ? AND id_prod LIKE ?");
            stmt.setString(1, m.getNome());
            stmt.setInt(2, m.getId_produto());
            rs = stmt.executeQuery();
            
            if(!rs.first()){
                stmt = null;
                stmt = connection.prepareStatement("INSERT INTO materiais(id_prod, cod, nome, quant) VALUES(?, ?, ?, ?)");
                stmt.setInt(1, m.getId_produto());
                stmt.setString(2, m.getCod());
                stmt.setString(3, m.getNome());
                stmt.setInt(4, m.getQuant());
            
                stmt.executeUpdate();
            }else{
//                JOptionPane.showMessageDialog(null, "O Material cadastrado já existe para esse produto");
                return;
            }
            
            //JOptionPane.showMessageDialog(null, "Salvo com sucesso!");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao salvar: "+ex);
        }finally{
            ConnectionFactory.closeConnection(connection, stmt);
        }
    }
    
    public List<Material> read(int index){
        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        List<Material> materiais = new ArrayList<>();
        
        try {
            stmt = connection.prepareStatement("SELECT * FROM materiais WHERE id_prod = ?");
            stmt.setInt(1, index);
            rs = stmt.executeQuery();
            
            while(rs.next()){
                Material m = new Material();
                
                m.setId(rs.getInt(COL_ID));
                m.setId_produto(rs.getInt(COL_ID_PROD));
                m.setCod(rs.getString(COL_COD));
                m.setNome(rs.getString(COL_NOME));
                m.setQuant(rs.getInt(COL_QUANT));
                                
                materiais.add(m);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao carregar: "+ex);
        }finally{
            ConnectionFactory.closeConnection(connection, stmt, rs);
        }        
        return materiais;
    }
    
    public void update(Material m) {
        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = connection.prepareStatement("UPDATE materiais SET cod = ?, nome = ?, quant = ? WHERE id = ?");            
            stmt.setString(1, m.getCod());
            stmt.setString(2, m.getNome());
            stmt.setInt(3, m.getQuant());
            stmt.setInt(4, m.getId());
            
            stmt.executeUpdate();
            
            JOptionPane.showMessageDialog(null, "Atualizado com sucesso!");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar: "+ex);
        }finally{
            ConnectionFactory.closeConnection(connection, stmt);
        }
    }
    
    public void delete(Material m) {
        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = connection.prepareStatement("DELETE FROM materiais WHERE id = ?");   
            stmt.setInt(1, m.getId());
            
            stmt.executeUpdate();
            
            JOptionPane.showMessageDialog(null, "Excluído com sucesso!");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao excluir: "+ex);
        }finally{
            ConnectionFactory.closeConnection(connection, stmt);
        }
    }
    
    public List<Material> search(String descricao, int index){
        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        List<Material> materiais = new ArrayList<>();
        
        try {
            stmt = connection.prepareStatement("SELECT * FROM materiais WHERE id_prod = ? and (cod LIKE ? OR nome LIKE ?)");
            stmt.setInt(1, index);
            stmt.setString(2, descricao+"%");
            stmt.setString(3, descricao+"%");
            rs = stmt.executeQuery();
            
            while(rs.next()){
                Material m = new Material();
                
                m.setId(rs.getInt(COL_ID));
                m.setId_produto(rs.getInt(COL_ID_PROD));
                m.setCod(rs.getString(COL_COD));
                m.setNome(rs.getString(COL_NOME));
                m.setQuant(rs.getInt(COL_QUANT));
                
                materiais.add(m);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao carregar: "+ex);
        }finally{
            ConnectionFactory.closeConnection(connection, stmt, rs);
        }        
        return materiais;
    }
}
