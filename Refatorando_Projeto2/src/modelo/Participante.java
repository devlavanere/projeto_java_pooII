package modelo;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Participante {
    private String cpf;
    private String nascimento;
    private ArrayList<Ingresso> ingresso = new ArrayList<>();

    public Participante(String cpf, String nascimento) {
        this.cpf = cpf;
        this.nascimento = nascimento;
    }
    
 // Métodos
 	public void adicionar(Ingresso i) {
 		ingresso.add(i);
 	}
 	
 	public void remover(Ingresso i) {
 		ingresso.remove(i);
 	}
 	
 	public Ingresso localizar(String codigo) { // Verificar se este método é realmente necessário, visto que apenas segui o exemplo da classe Prateleteira
 		for(Ingresso i : ingresso) {
 			if(i.getCodigo().equals(codigo)) {
 				return i;
 			}
 		}
 		return null;
 	}
    
	public int calcularIdade() {
    	DateTimeFormatter f;
		f = DateTimeFormatter.ofPattern("dd/MM/yyyy"); // Setando o tipo de formatação da data
		
		LocalDate dataNasc = LocalDate.parse(this.nascimento, f); // Aplicando a formatação à idade do participante
        LocalDate hoje = LocalDate.now(); // Obtendo a data atual
        Period periodo = Period.between(dataNasc, hoje); // Calculando a diferença entre as datas
        
        return periodo.getYears(); // Retornando a idade (em anos)
	}
	
	//Método para retornar o valor do desconto do participante
			public double valorDesconto() {
				if(calcularIdade() < 18) {
					return 0.1; // Se for menor de idade, 10% de desconto
				}
				else {
					if(calcularIdade() < 60) {
						return 0; // Se for maior de idade e não for idoso, o preço permanece normal
					}
					else {
						return 0.2; // Se for idoso, 20% de desconto
					}
				}
			}

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNascimento() {
        return nascimento;
    }

    public void setNascimento(String nascimento) {
        this.nascimento = nascimento;
    }

    public ArrayList<Ingresso> getIngresso() {
        return ingresso;
    }

    public void setIngresso(ArrayList<Ingresso> ingresso) {
        this.ingresso = ingresso;
    }

    @Override
    public String toString() {
        return "Participante{" +
                "cpf='" + cpf + '\'' +
                ", nascimento='" + nascimento + '\'' +
                '}';
    }

}