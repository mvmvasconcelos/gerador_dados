/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package negocio;
import java.util.Comparator;
import java.sql.Timestamp;


/**
 *
 * @author marcus.vasconcelos
 */
public class Pessoa {
    
    private String nome;
    private String cidade;
    private Timestamp data;
    private int id;
    
    public Pessoa(int id, String nome, String cidade, Timestamp data){
        this.nome = nome;
        this.cidade = cidade;
        this.data = data;
        this.id = id;
    }
    
    public void setNome(String t){
        this.nome = t;
    }
    
    public String getNome(){
        return nome;
    }
    public void setCidade(String s){
        this.cidade = s;
    }
    
    public String getCidade(){
        return cidade;
    }
    
    public Timestamp getData(){
        return data;
    }
    
    
    /*Comparador para ordenar os termos em ordem alfabética*/
    public static Comparator<Pessoa> comparadorTermo = new Comparator<Pessoa>() {

	public int compare(Pessoa s1, Pessoa s2) {
	   String Pessoa1 = s1.getNome().toUpperCase();
	   String Pessoa2 = s2.getNome().toUpperCase();

	   //ordem crescente
	   return Pessoa1.compareTo(Pessoa2);

	   //ordem dcrescente
	   //return Pessoa2.compareTo(Pessoa1);
    }};
    
}
