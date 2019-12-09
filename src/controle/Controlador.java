/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controle;

import java.sql.*;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import negocio.Pessoa;


/**
 *
 * @author vinicius
 */
public class Controlador {
    
    Connection conecta = null;
    PreparedStatement pst = null;
    ResultSet resultado = null;
    
    public Controlador() throws ClassNotFoundException{
        conecta = ConexaoBD.ConectaBancoDados();
    }
    
    /* Listas temporárias para armazenar os objetos criados    
       posteriormente serão substituídas pelo banco de dados */    
    public static ArrayList<Pessoa> listaDePessoas = new ArrayList<>();
    
    /**
     * Faz select na tabela Pessoas e armazena na lista
     */
    public void selectListaPessoas(){
        //Limpa o arraylist
        listaDePessoas.clear();
        String sql = "select * from pessoas";
        try{
            //Prepara a query
            pst = conecta.prepareStatement(sql);
            //executa a query
            resultado = pst.executeQuery();
            //pega os metadados do resultado
            ResultSetMetaData rsmd = resultado.getMetaData();
            //armazena número de colunas da tabela
            int nrDeColunas = rsmd.getColumnCount();
            //variáveis que serão gravadas com os dados
            int id = 0; String nome = null; String cidade = null; Timestamp data = null;
            //Percorre resultado enquanto houver registros
            while (resultado.next()) {
                //Para cada coluna no registro, salva o dado correspondente de acordo com a coluna
                for (int i = 1; i <= nrDeColunas; i++) {
                    switch(i){
                        case 1:
                            id = Integer.parseInt(resultado.getString(i));
                            break;
                        case 2:
                            nome = resultado.getString(i);
                            break;
                        case 3:
                            cidade = resultado.getString(i);
                            break;
                        case 4:
                            data = Timestamp.valueOf(resultado.getString(i));
                            break;
                    }
                }
                //Armazena o registro na listaDePessoas
                adicionaPessoaNaLista(id, nome, cidade, data);
            }
            pst.close();
        }
        catch(SQLException error){
            JOptionPane.showMessageDialog(null, "ERRO SQL: " + error);
        }
    }
    
    /**
     * Insere o novo Pessoa no banco de dados
     */
    public void cadastraPessoa(String nome, String cidade, Timestamp data){
        
        String sql = "insert into pessoas (nome, cidade, data)"
                        + "values (?, ?, ?)";
        try{
            

             //Prepara a query
            pst = conecta.prepareStatement(sql);
            
            pst.setString(1, nome);
            pst.setString(2, cidade);
            pst.setTimestamp(3, data);
            //executa a query
            //resultado = pst.executeQuery();
            
            pst.execute();
            pst.close();
            //adicionaPessoaNaLista(id, codigo, tipo, descricao, situacao);
            
        }
        catch(SQLException error){
            
            JOptionPane.showMessageDialog(null, "ERRO SQL: " + error);

        }
    }
    
    public static ArrayList<Pessoa> getListaDePessoas() {
        return listaDePessoas;
    }
    
    /**
     * Cria um array com todos as pessoas que contenham a cidade
     * relacionada
     * @param cidade cidade da Pessoa
     * @return lista de pessoas
     */
    public ArrayList<Pessoa> getPessoasPelCidade(String cidade){
        
        ArrayList<Pessoa> pessoasPelaCidade = new ArrayList<>();
        for (int i = 0; i < listaDePessoas.size(); i++) {            
            if (listaDePessoas.get(i).getCidade() == cidade) {
                pessoasPelaCidade.add(listaDePessoas.get(i));
            }
        }
        return pessoasPelaCidade;
    }    
    public void adicionaPessoaNaLista(int id, String nome, String cidade, Timestamp data){
        Pessoa novo = new Pessoa(id, nome, cidade, data);
        listaDePessoas.add(novo);
    }
}
