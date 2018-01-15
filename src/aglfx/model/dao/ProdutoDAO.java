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
import aglfx.model.bean.Produto;

/**
 *
 * @author Usuário
 */
public class ProdutoDAO {
    
    private static final String COL_ID = "id";
    private static final String COL_NOME = "nome";
    
    public void create(Produto p) {
        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = connection.prepareStatement("SELECT * FROM produtos WHERE nome LIKE ?");
            stmt.setString(1, ""+p.getNome()+"%");
            rs = stmt.executeQuery();
            
            if(!rs.first()){
                stmt = null;
                stmt = connection.prepareStatement("INSERT INTO produtos(nome) VALUES(?)");
                stmt.setString(1, p.getNome());
            
                stmt.executeUpdate();
            }else{
                JOptionPane.showMessageDialog(null, "O produto cadastrado já existe");
                return;
            }
            
            
            
            JOptionPane.showMessageDialog(null, "Salvo com sucesso!");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao salvar: "+ex);
        }finally{
            ConnectionFactory.closeConnection(connection, stmt);
        }
    }
    
    public List<Produto> read(){
        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        List<Produto> produtos = new ArrayList<>();
        
        try {
            stmt = connection.prepareStatement("SELECT * FROM produtos");                        
            rs = stmt.executeQuery();
            
            while(rs.next()){
                Produto p = new Produto();
                
                p.setId(rs.getInt(COL_ID));
                p.setNome(rs.getString(COL_NOME));
                
                produtos.add(p);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao carregar: "+ex);
        }finally{
            ConnectionFactory.closeConnection(connection, stmt, rs);
        }        
        return produtos;
    }
    
    public void update(Produto p) {
        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = connection.prepareStatement("UPDATE produtos SET nome = ? WHERE id = ?");            
            stmt.setString(1, p.getNome());
            stmt.setInt(2, p.getId());
            
            stmt.executeUpdate();
            
            JOptionPane.showMessageDialog(null, "Atualizado com sucesso!");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar: "+ex);
        }finally{
            ConnectionFactory.closeConnection(connection, stmt);
        }
    }
    
    public void delete(Produto p) {
        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = connection.prepareStatement("DELETE FROM produtos WHERE id = ?");   
            stmt.setInt(1, p.getId());
            
            stmt.executeUpdate();
            
            stmt = connection.prepareStatement("DELETE FROM materiais WHERE id_prod = ?");
            stmt.setInt(1, p.getId());
            
            stmt.executeUpdate();
                        
            JOptionPane.showMessageDialog(null, "Excluído com sucesso!");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao excluir: "+ex);
        }finally{
            ConnectionFactory.closeConnection(connection, stmt);
        }
    }
    
    public List<Produto> search(String descricao){
        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        List<Produto> produtos = new ArrayList<>();
        
        try {
            stmt = connection.prepareStatement("SELECT * FROM produtos WHERE nome LIKE ?");
            stmt.setString(1, ""+descricao+"%");
            rs = stmt.executeQuery();
            
            while(rs.next()){
                Produto p = new Produto();
                
                p.setId(rs.getInt(COL_ID));
                p.setNome(rs.getString(COL_NOME));
                
                produtos.add(p);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao carregar: "+ex);
        }finally{
            ConnectionFactory.closeConnection(connection, stmt, rs);
        }        
        return produtos;
    }
}
