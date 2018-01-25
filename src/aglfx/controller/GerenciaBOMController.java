/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aglfx.controller;

import aglfx.model.bean.Material;
import aglfx.model.bean.Produto;
import aglfx.model.dao.MaterialDAO;
import aglfx.model.dao.ProdutoDAO;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javax.swing.JOptionPane;

/**
 *
 * @author Usuário
 */
public class GerenciaBOMController implements Initializable {

    private final ObservableList listData = FXCollections.observableArrayList();
    private final ObservableList<Material> tableData = FXCollections.observableArrayList();

    private List<Produto> listProd;
    private List<Material> listMat;

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
    private TableColumn<Material, String> colCodigoMat;

    @FXML
    private TableColumn<Material, String> colNomeMat;

    @FXML
    private TableColumn<Material, String> colQuantidadeMat;

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
    private Label lblErroCodigoMat;

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
    private void btnAddMatMouseOnClickListener(MouseEvent event) {
        int index = lvProdutos.getSelectionModel().getSelectedIndex();

        if (index != -1) {
            String cod = txtCodMaterial.getText();
            String nome = txtNomeMat.getText();
            int quant = (Integer) spnQuantidadeMat.getValue();

            if (nome.trim().equals("")) {
                lblErroNomeMat.setVisible(true);
            }else if (cod.equals("")){
                lblErroCodigoMat.setVisible(true);
            }else if (quant == 0) {
                lblErroQuantidadeMat.setVisible(true);
            } else {
                Material m = new Material();

                m.setId_produto(listProd.get(index).getId());
                m.setCod(cod);
                m.setNome(nome);
                m.setQuant(quant);

                MaterialDAO dao = new MaterialDAO();
                dao.create(m);
                carregarListaMatCompleta(listProd.get(index).getId());
            }
        } else {
            JOptionPane.showMessageDialog(null, "Nenhum produto selecionado!");
        }
    }

    @FXML
    void btnEditarMatMouseOnClickListener(MouseEvent event) {
        int index = tableMat.getSelectionModel().getSelectedIndex();
        int idProd = lvProdutos.getSelectionModel().getSelectedIndex();

        if (index != -1) {
            Material m = new Material();
            MaterialDAO dao = new MaterialDAO();

            m.setId(listMat.get(index).getId());
            m.setNome(txtNomeMat.getText());
            m.setCod(txtCodMaterial.getText());
            m.setQuant((int) spnQuantidadeMat.getValue());

            dao.update(m);
            carregarListaMatCompleta(listProd.get(idProd).getId());
        } else {
            JOptionPane.showMessageDialog(null, "Nenhum Material selecionado!");
        }
    }

    @FXML
    void btnExcluirMatMouseOnClickListener(MouseEvent event) {
        int index = tableMat.getSelectionModel().getSelectedIndex();
        int idProd = lvProdutos.getSelectionModel().getSelectedIndex();

        if (index != -1) {
            Material m = new Material();
            MaterialDAO dao = new MaterialDAO();

            m.setId(listMat.get(index).getId());

            dao.delete(m);
            carregarListaMatCompleta(listProd.get(idProd).getId());
        } else {
            JOptionPane.showMessageDialog(null, "Nenhum produto selecionado!");
        }
    }

    @FXML
    private void lvProdOnMouseClicked(MouseEvent event) {
        int index = lvProdutos.getSelectionModel().getSelectedIndex();

        if (index != -1) {
            String p = lvProdutos.getSelectionModel().getSelectedItem();
            txtNomeProd.setText(p);
            carregarListaMatCompleta(listProd.get(index).getId());
        }
    }
    
    @FXML
    void tableMatOnMouseClicked(MouseEvent event) {
        int index = tableMat.getSelectionModel().getSelectedIndex();
        
        if(index != -1){
             String nome = listMat.get(index).getNome();
            String cod = listMat.get(index).getCod();
            int quant = listMat.get(index).getQuant();

            txtNomeMat.setText(nome);
            txtCodMaterial.setText(cod);
            spnQuantidadeMat.getValueFactory().setValue(quant);
        }
    }

    /**
     * @return Listener para mudança de índice selecionado na ListView.
     */
    @FXML
    private ChangeListener<String> txtBuscaProdOnTextChanged() {
        return (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            String busca = txtBuscarProd.getText();
            ProdutoDAO dao = new ProdutoDAO();

            listProd.clear();
            carregarListaProdBusca(busca);
        };
    }

    @FXML
    private ChangeListener<String> txtBuscaMatOnTextChanged() {

        return (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            String busca = txtBuscarMat.getText();
            MaterialDAO dao = new MaterialDAO();

            listMat.clear();
            int index = lvProdutos.getSelectionModel().getSelectedIndex();
            
            if(index != -1){
                carregarListaMatBusca(busca, index);
            }            
        };

    }

    @FXML
    private void txtBuscarProdOnActionListener(ActionEvent event) {
        System.out.println("Texto" + txtBuscarProd.getText());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        listProd = new ArrayList<>();
        listMat = new ArrayList<>();

        lvProdutos.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        carregarListaProdCompleta();

        txtBuscarProd.textProperty().addListener(txtBuscaProdOnTextChanged());
        txtBuscarMat.textProperty().addListener(txtBuscaMatOnTextChanged());

        colCodigoMat.setCellValueFactory(new PropertyValueFactory<>("cod"));
        colNomeMat.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colQuantidadeMat.setCellValueFactory(new PropertyValueFactory<>("quant"));

        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE, 1);
        spnQuantidadeMat.setValueFactory(valueFactory);
        
        
        
//        try{
//            InputStream is = new FileInputStream("bom.txt");
//            InputStreamReader isr = new InputStreamReader(is);
//            BufferedReader br = new BufferedReader(isr);
//            
//            Produto p = new Produto();
//            Material m = new Material();
//            ProdutoDAO pd = new ProdutoDAO();
//            MaterialDAO md = new MaterialDAO();
//            
//            int index = -1;
//            String text = br.readLine();
//            
//            while(text!= null){
//                if(text.startsWith("---")){
//                    index++;
//                    p.setNome(text.substring(3));
//                    pd.create(p);
//                }else{
//                    String s[] = text.split("#");
//                    m.setCod(s[0]);
//                    m.setNome(s[1]);
//                    m.setCod(s[2]);
//                    m.setId_produto(index);
//                    md.create(m);
//                }
//                text = br.readLine();
//            }
//        }catch(FileNotFoundException e){
//            
//        } catch (IOException ex) {
//            Logger.getLogger(GerenciaBOMController.class.getName()).log(Level.SEVERE, null, ex);
//        }
        
    }

    private void carregarListaProdCompleta() {
        ProdutoDAO dao = new ProdutoDAO();
        List<Produto> prod = dao.read();

        listProd.clear();
        listProd = prod;
        atualizarProdutos(prod);
    }

    /**
     * @param nome Nome do produto que deseja buscar na ListView.
     */
    private void carregarListaProdBusca(String nome) {
        ProdutoDAO dao = new ProdutoDAO();
        List<Produto> prod = dao.search(nome);

        listProd.clear();
        listProd = prod;
        atualizarProdutos(prod);
    }

    /**
     * @param prod Lista de produtos carregados.
     */
    private void atualizarProdutos(List<Produto> prod) {
        listData.clear();

        for (int i = 0; i < prod.size(); i++) {
            listData.add(prod.get(i).getNome());
        }

        lvProdutos.setItems(listData);
    }

    private void carregarListaMatBusca(String nome, int index) {
        MaterialDAO dao = new MaterialDAO();
        List<Material> mat = dao.search(nome, listProd.get(index).getId());

        listMat.clear();
        listMat = mat;
        atualizarMateriais(mat);
    }

    /**
     *
     * @param index Índice selecionado na ListView.
     */
    private void carregarListaMatCompleta(int index) {
        MaterialDAO dao = new MaterialDAO();
        List<Material> mat = dao.read(index);
        
        listMat.clear();
        listMat = mat;
        
        atualizarMateriais(mat);
    }

    /**
     * @param mat Lista de materiais referentes ao produto selecionado. *
     */
    private void atualizarMateriais(List<Material> mat) {
        tableData.clear();

        for (int i = 0; i < mat.size(); i++) {
            tableData.add(mat.get(i));
        }

        tableMat.setItems(tableData);
    }
}
