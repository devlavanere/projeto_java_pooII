package regras_negocio;

import java.util.ArrayList;

import modelo.Evento;
import modelo.Participante;
import modelo.Ingresso;
import modelo.Convidado;
import repositorio.Repositorio;

public class Fachada {
	private static Repositorio repositorio = new Repositorio();
	
	// Construtor
		private Fachada() {}
	
//	Adiciona um evento no repositório	
	public static void criarEvento(String data, String descricao, int capacidade, double preco) throws Exception {
				
//		2-Um evento pode ter preço zero, mas nunca negativo 
		if (preco < 0)
			throw new Exception("Preco do evento não pode ser negativo:" + preco);
		
//		3-A data e a descrição do evento são obrigatórias
		if (data == null || data.isEmpty()) {
	        throw new Exception("A data do evento é obrigatória.");
	    }
				
		if (descricao == null || descricao.isEmpty()) {
		    throw new Exception("A descrição do evento é obrigatória.");
		}
			
//		4-A capacidade do evento deve ser de no mínimo 2 ingressos 
		if (capacidade < 2)
			throw new Exception("A capacidade do evento não pode ser menor que dois ingressos");
		
//		5-A quantidade de ingressos de um evento não pode ultrapassar a sua capacidade
		
//		6-Pode existir mais de um evento numa mesma data
		
		int idevento = repositorio.gerarId();
		Evento e = new Evento(idevento, data, descricao, capacidade, preco);
		repositorio.adicionar(e);
		repositorio.salvarObjetos();
	}

//  Adiciona um participante no repositório
	public static void criarParticipante(String cpf, String nascimento) throws Exception {
		
//		8-Um participante é identificado por um cpf 
		Participante p = repositorio.localizarParticipante(cpf) ;
		if(p != null)							//participante ja cadastrado no repositorio? //ok
			throw new Exception("criar participante " + cpf + " - participante já cadastrado: " +cpf);
		
//		teste2 - Não pode criar participante sem data
		if (nascimento == null || nascimento.isEmpty()) {
	        throw new Exception("A data é obrigatória.");
	    }
		
//		9-Um participante tem idade calculada pela diferença entre a sua data de nascimento e a data atual
		
		p = new Participante(cpf, nascimento);
		repositorio.adicionar(p);
		repositorio.salvarObjetos();
	}
	
	// Adicionar um convidado no repositório
	public static void criarConvidado(String cpf, String nascimento, String empresa) throws Exception {

	    Participante pc = repositorio.localizarParticipante(cpf);
	    if (pc != null)
	        throw new Exception("Participante já cadastrado - CPF nº: " + cpf);

	    // teste 3 -> Não pode criar convidado sem empresa
	    if (empresa == null || empresa.isEmpty())
	        throw new Exception("Não é possível criar convidado sem informar a empresa.");

	    pc = new Convidado(cpf, nascimento, empresa);
	    repositorio.adicionar(pc);
	    repositorio.salvarObjetos();
	}
	
	// Adicionar um ingresso no repositório e criar os relacionamentos com o evento e o participante
	public static void criarIngresso(int id, String cpf, String telefone) throws Exception {
		Evento e = repositorio.localizarEvento(id);
		Participante p = repositorio.localizarParticipante(cpf);
		
		// Lança exceção, caso o número do telefone for nulo
		if (telefone == null || telefone.isEmpty()) {
			throw new Exception("O número do telefone é obrigatório"); 
		}
		
		// Lança exceção caso o evento não exista
		if (e == null) {
			throw new Exception("Evento não encontrado"); 
		}
		
		// Lança exceção caso o participante não exista
		if (p == null) {
			throw new Exception("Participante não encontrado"); 
		}
		
		// Lança exceção caso o evento esteja lotado
		if(e.lotado()) {
			throw new Exception("Evento lotado"); 
		}
		
		// 11-Um ingresso é identificado por um código gerado pelo sistema no formato id + “-“ + cpf
		String codigo = id + "-" + cpf;
		Ingresso ingressoExistente = repositorio.localizarIngresso(codigo);
		
		if(ingressoExistente != null) {
			throw new Exception("Ingresso já cadastrado");
		}
		
		// Criando o ingresso propriamente dito
		Ingresso ingresso = new Ingresso(codigo, telefone);

			        
		// Configurar relacionamento bidirecional
		ingresso.setEvento(e);
		ingresso.setParticipante(p);
			        
		// Adicionar o ingresso ao evento e ao participante
		e.adicionar(ingresso);
		p.adicionar(ingresso);
			        
		// Adicionar o ingresso ao repositório
		repositorio.adicionar(ingresso);
	        
		// Salvar objetos no repositório
		repositorio.salvarObjetos();
		
	    // 11-Um ingresso é identificado por um código gerado pelo sistema no formato id + “-“ + cpf
//	    String codigoIngresso = id + "-" + cpf;
//	    Ingresso ingresso = repositorio.localizarIngresso(codigoIngresso);
//	    if (ingresso != null)
//	        throw new Exception("Ingresso já existe");
//
//	    // 12-Um ingresso possui um telefone de contato obrigatório, que pode ser de qualquer pessoa
//	    if (telefone == null)
//	        throw new Exception("O número do telefone é obrigatório para gerar o ingresso.");
//
//	    // 14-O preço do ingresso é o preço do evento menos o desconto que é calculado de acordo com a idade do participante
//	    Evento evento = repositorio.localizarEvento(id);
//	    Participante participante = repositorio.localizarParticipante(cpf);
//	    if (evento == null || participante == null)
//	        throw new Exception("Evento ou participante não encontrados.");
//
//	    double descontoIdade = (participante.calcularIdade());
//	    double descontoEmpresa = (participante instanceof Convidado) ? 0.5 : 0.0;
//	    double precoIngresso = evento.getPreco() * (1 - descontoIdade - descontoEmpresa);
//
//	    // Create the ingresso
//	    Ingresso novoIngresso = new Ingresso(codigoIngresso, telefone, evento, participante);
//	    repositorio.adicionar(novoIngresso);
	}
	
	// Remover o evento do repositório
	public static void apagarEvento(int id) throws Exception {
		Evento e = repositorio.localizarEvento(id);
		
		// Lança exceção caso o evento não exista
		if (e == null) {
			throw new Exception("Evento não encontrado"); 
		}
		
		// Caso o evento possua ingressos, não será possível apagá-lo e será lançada uma exceção
		if(e.quantidadeIngressos() > 0) {
			throw new Exception("O evento possui ingressos"); 
		}
	    	
		repositorio.remover(e);
		repositorio.salvarObjetos();
		
//	    // 7-Um evento só pode ser apagado caso não tenha ingresso
//	    Evento evento = repositorio.localizarEvento(id);
//	    if (evento == null)
//	        throw new Exception("Evento não encontrado.");
//
//	    // Check if there are any associated tickets (ingressos)
////	    ArrayList<Ingresso> ingressosDoEvento = repositorio.localizarIngresso();
////	    if (!ingressosDoEvento.isEmpty())
////	        throw new Exception("Não é possível apagar o evento, pois existem ingressos associados a ele.");
//
//	    // Remove the event from the repository
	}

	// Remover o participante do repositório e remover todos os seus ingressos e respectivos relacionamentos
	public static void apagarParticipante(String cpf) throws Exception {
		Participante p = repositorio.localizarParticipante(cpf);

		if (p == null) {
			throw new Exception("Participante não encontrado");
		}

		// Verifica se o último ingresso do participante está ultrapassado
		Ingresso ultimoIngresso;

		if (p.getIngresso().isEmpty()) {
			ultimoIngresso = null;
		}
		else {
			ultimoIngresso = p.getIngresso().get(p.getIngresso().size() - 1);
		}

		if (ultimoIngresso.verificaIngressoUltrapassado()) {
			throw new Exception("O último ingresso não está ultrapassado");
		}

		// Remove todos os ingressos associados ao participante
		for (Ingresso ingresso : new ArrayList<>(p.getIngresso())) {
			apagarIngresso(ingresso.getCodigo());
		}

		// Remove o participante do repositório
		repositorio.remover(p);
		repositorio.salvarObjetos();
		
//	    // 10-Um participante só pode ser apagado caso o seu último ingresso esteja ultrapassado e, neste caso,
//	    // todos os seus ingressos devem ser apagados.
//
//	    Participante participante = repositorio.localizarParticipante(cpf);
//	    if (participante == null)
//	        throw new Exception("Participante não encontrado.");
//
//	    // Find the participant's last ticket
////	    Ingresso ultimoIngresso = repositorio.localizarUltimoIngressoDoParticipante(participante);
//
////	    if (ultimoIngresso != null && !ultimoIngresso.estaUltrapassado()) {
//	        throw new Exception("Não é possível apagar o participante. Seu último ingresso não está ultrapassado.");
////	    }
//
//	    // Remove all tickets associated with the participant
////	    ArrayList<Ingresso> ingressosDoParticipante = repositorio.localizarIngressosPorParticipante(participante);
////	    for (Ingresso ingresso : ingressosDoParticipante) {
////	        repositorio.removerIngresso(ingresso);
////    }
//
//	    // Remove the participant from the repository
////	    repositorio.removerParticipante(participante);
	}

	// remover o ingresso do repositório e remover os relacionamentos com evento e participante
	public static void apagarIngresso(String codigo) throws Exception {
		Ingresso i = repositorio.localizarIngresso(codigo);
        
		if(i == null) {
			throw new Exception("Ingresso não encontrado");
		}

		i.getEvento().remover(i); // Remove o ingresso do evento
		i.getParticipante().remover(i); // Remove o ingresso do participante
	        
		repositorio.remover(i); // Remove o ingresso do repositório
		repositorio.salvarObjetos(); // Salva objetos no repositório
		
//	    // 11-Um ingresso é identificado por um código gerado pelo sistema no formato id + “-“ + cpf
//	    
//	    Ingresso ingresso = repositorio.localizarIngresso(codigo);
//	    
//	    // 13-Um ingresso pode ser apagado
//	    if (ingresso == null) {
//	        throw new Exception("Ingresso não encontrado");
//	    }
//	    
//	    // Remover o ingresso do participante
//	    Participante participante = ingresso.getParticipante();
//	    if (participante != null) {
//	        participante.getIngresso();
//	    }
//	    
//	    // Remover o ingresso do evento
//	    Evento evento = ingresso.getEvento();
//	    if (evento != null) {
//	        evento.getIngressos();
//	    }
//	    
//	    // Remover o ingresso do repositório
//	    repositorio.remover(ingresso);
	}

//	retornar todos os eventos do repositório
	public static ArrayList<Evento> listarEventos() {
		return repositorio.getEventos();
		
	}
	
//	retorna todos os participantes e convidados do repositório
	public static ArrayList<Participante> listarParticipantes() {
		return repositorio.getParticipantes();
		
	}
	
//	retorna todos os ingressos do repositório
	public static ArrayList<Ingresso> listarIngressos() {
		return repositorio.getIngressos();
		
	}
	
}

