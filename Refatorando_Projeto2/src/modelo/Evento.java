package modelo;

import java.util.ArrayList;

public class Evento {

    private int id;
    private String data;
    private String descricao;
    private int capacidade;
    private double preco;
    private ArrayList<Ingresso> ingressos;

    public Evento(int id, String data, String descricao, int capacidade, double preco) {
        this.id = id;
        this.data = data;
        this.descricao = descricao;
        this.capacidade = capacidade;
        this.preco = preco;
        this.ingressos = new ArrayList<>(); // Initialize the ArrayList
    }
    
    public void adicionar(Ingresso i) {
		ingressos.add(i);
	}
	
	public void remover(Ingresso i) {
		ingressos.remove(i);
	}

    public boolean lotado() {
        return ingressos.size() >= capacidade;
    }

    public int quantidadeIngressos() {
        return ingressos.size();
    }

 // Retorna o valor total arrecadado pelo evento
 	public double totalArrecadado() {
 		double total = 0.0;
		
		for (Ingresso i : ingressos) {
			total += i.calcularPreco(); // Calcula o valor do ingresso, já com o desconto, e o incrementa na variável total que será retornada
		}
		return total;
 	}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getCapacidade() {
        return capacidade;
    }

    public void setCapacidade(int capacidade) {
        this.capacidade = capacidade;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public ArrayList<Ingresso> getIngressos() {
        return ingressos;
    }

    public void setIngressos(ArrayList<Ingresso> ingressos) {
        this.ingressos = ingressos;
    }

    @Override
    public String toString() {
        // Implement the toString method to represent the object as a string
        return "Evento {id=" + id + ", data=" + data + ", descricao=" + descricao + ", capacidade=" + capacidade
                + ", preco=" + preco + "}";
    }
    
}
