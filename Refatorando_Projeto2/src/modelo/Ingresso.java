package modelo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Ingresso {
	// Atributos
	private String codigo;
	private String telefone;
	
	private Evento evento; // Relacionamento 1:1 com a classe Evento
	private Participante participante; // Relacionamento 1:1 com a classe Participante
	
	// Construtor
	public Ingresso(String codigo, String telefone) {
		this.codigo = codigo;
		this.telefone = telefone;
	}

	// Getters e setters
	public String getCodigo() {
		return codigo;
	}

	public String getTelefone() {
		return telefone;
	}

	public Evento getEvento() {
		return evento;
	}

	public Participante getParticipante() {
		return participante;
	}

	public void setEvento(Evento evento) {
		this.evento = evento;
	}
	
	public void setParticipante(Participante participante) {
		this.participante = participante;
	}
	
	// Métodos
	public double calcularPreco() { // A ser testado
		return evento.getPreco() - (evento.getPreco() * participante.valorDesconto()); // Calcula o valor do desconto e depois retira do valor inteiro do ingresso
	}
	
	// Método para verificar se o último ingresso está ultrapassado
	 public boolean verificaIngressoUltrapassado() {
	        DateTimeFormatter f = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	        LocalDate dataEvento = LocalDate.parse(this.getEvento().getData(), f);
	        LocalDate hoje = LocalDate.now();

	        return dataEvento.isAfter(hoje); // Se a data do evento for maior do que a data de hoje, retorna true, provando que a data do evento não está ultrapassada
	    }

	@Override
	public String toString() {
        return "\nIngresso ===> Cod = " + codigo + " | Tel = " + telefone + " | Cod Evento = " + evento.getId() + " | CPF = " + participante.getCpf();
    }
}