/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aglfx.controller;

import aglfx.model.bean.Material;
import aglfx.model.bean.MaterialTabela;
import aglfx.model.bean.Produto;
import aglfx.model.dao.MaterialDAO;
import aglfx.model.dao.ProdutoDAO;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javax.swing.JOptionPane;

/**
 *
 * @author Usuário
 */
public class GerenciaBOMController implements Initializable {
    
    private ObservableList data = FXCollections.observableArrayList();
    private List<Produto> listProd;
    
    @FXML
    private ListView<String> lvProdutos;

    @FXML
    private TextField txtNomeProd;

    @FXML
    private Button btnAddProd;

    @FXML
    private Button btnEditarProd;

    @FXML
    private Button btnExcluirProd;

    @FXML
    private TextField txtBuscarProd;
    
    @FXML
    private Label lblErroNomeProd;

    @FXML
    private TableView<Material> tableMat;

    @FXML
    private TextField txtBuscarMat;

    @FXML
    private TextField txtNomeMat;

    @FXML
    private Button btnAddMat;

    @FXML
    private Button btnEditarMat;

    @FXML
    private Button btnExcluirMat;

    @FXML
    private TextField txtCodMaterial;

    @FXML
    private Spinner<Integer> spnQuantidadeMat;
    
    @FXML
    private Label lblErroNomeMat;

    @FXML
    private Label lblErroNomeCodigoMat;

    @FXML
    private Label lblErroQuantidadeMat;
    
    @FXML
    private void btnAddProdMouseOnClickListener(MouseEvent event) {
        String nome = txtNomeProd.getText();
        if (nome.trim().equals("")) {
            lblErroNomeProd.setVisible(true);
        } else {
            Produto p = new Produto();
            ProdutoDAO dao = new ProdutoDAO();

            p.setNome(nome);
            dao.create(p);
            carregarListaProdCompleta();
        }
    }
    
    @FXML
    private void btnEditarProdMouseOnClickListener(MouseEvent event) {
        int index = lvProdutos.getSelectionModel().getSelectedIndex();

        if (index != -1) {
            Produto p = new Produto();
            ProdutoDAO dao = new ProdutoDAO();

            p.setId(listProd.get(index).getId());
            p.setNome(txtNomeProd.getText());

            dao.update(p);
            carregarListaProdCompleta();
        } else {
            JOptionPane.showMessageDialog(null, "Nenhum produto selecionado!");
        }
    }
    
    @FXML
    private void btnExcluirProdMouseOnClickListener(MouseEvent event) {
        int index = lvProdutos.getSelectionModel().getSelectedIndex();

        if (index != -1) {
            Produto p = new Produto();
            ProdutoDAO dao = new ProdutoDAO();

            p.setId(listProd.get(index).getId());

            dao.delete(p);
            carregarListaProdCompleta();
            if (listProd.size() > 0) {
                lvProdutos.getSelectionModel().select(0);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Nenhum produto selecionado!");
        }
    }
    
    @FXML
    private void lvProdOnMouseClicked(MouseEvent event){
        int index = lvProdutos.getSelectionModel().getSelectedIndex();
        
        if(index != -1){
            String p = lvProdutos.getSelectionModel().getSelectedItem();
            txtNomeProd.setText(p);
        }
    }
    
    @FXML
    private ChangeListener<String> txtBuscaProdOnTextChanged(){
        return (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            String busca = txtBuscarProd.getText();
            ProdutoDAO dao = new ProdutoDAO();

            listProd.clear();
            carregarListaProdBusca(busca);
        };
    }
    
    @FXML
    private void txtBuscarProdOnActionListener(ActionEvent event){
        System.out.println("Texto" + txtBuscarProd.getText());
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        lvProdutos.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        carregarListaProdCompleta();
        
        txtBuscarProd.textProperty().addListener(txtBuscaProdOnTextChanged());
    }

    private void carregarListaProdCompleta() {
        ProdutoDAO dao = new ProdutoDAO();
        List<Produto> prod = dao.read();
        listProd = prod;
        atualizarProdutos(prod);
    }

    private void carregarListaProdBusca(String nome) {
        ProdutoDAO dao = new ProdutoDAO();
        List<Produto> prod = dao.search(nome);
        listProd = prod;
        atualizarProdutos(prod);
    }

    private void atualizarProdutos(List<Produto> prod) {
        data.clear();
        
        for (int i = 0; i < prod.size(); i++) {
            data.add(prod.get(i).getNome());
        }
        
        lvProdutos.setItems(data);
    }

    private void carregarListaMatBusca(String nome, int index) {
        MaterialDAO dao = new MaterialDAO();
        List<Material> mat = dao.search(nome, index);
        atualizarMateriais(mat);
    }

    private void carregarListaMatCompleta(int index) {
        MaterialDAO dao = new MaterialDAO();
        List<Material> mat = dao.read(index);
        atualizarMateriais(mat);
    }

    private void atualizarMateriais(List<Material> mat) {
        MaterialDAO dao = new MaterialDAO();
        List<Material> listMat = mat;
        MaterialTabela model = new MaterialTabela(listMat);

        //tableMateriais.setModel(model);
    }
      
}
