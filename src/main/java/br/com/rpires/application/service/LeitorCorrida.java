/**
 * 
 */
package br.com.rpires.application.service;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.rpires.application.entity.Corrida;

/**
 * @author Rodrigo Pires
 *
 */
@Service
public class LeitorCorrida implements ILeitorCorrida {

	private static final long serialVersionUID = 1776544567L;
	
	private Logger logger = Logger.getLogger(LeitorCorrida.class.getName());

	@Autowired
	private IParserFileToCorrida parser;
	
	public LeitorCorrida() {
		this.parser = new ParserFileToCorrida();
	}

	public List<Corrida> lerArquivo(InputStream inputStream) throws IOException {
		List<Corrida> lista = new ArrayList<>();
		String line;
		boolean firstLine = true;
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
			while ((line = reader.readLine()) != null) {
				if (firstLine) {
					firstLine = false;
					continue;
				}
				lista.add(criarCorrida(line));
			}
		} catch (IOException e) {
			logger.info(" ## ERRO CONVERTENDO LINHA EM OBJETO ##");
			e.printStackTrace();
			throw e;
		} finally {
			inputStream.close();
		}
		return lista;
	}

	private Corrida criarCorrida(String line) {
		return parser.parser(line);
	}
	
	@Override
	public InputStream lerRetornarStream(InputStream inputStream) throws IOException {
		List<Corrida> listaCorridas = lerArquivo(inputStream);
		Collections.sort(listaCorridas);
		
		List<Corrida> listaOrdenada = ordenarListaPorColocacao(listaCorridas);
		StringBuilder sb = new StringBuilder();
		listaOrdenada.forEach(corrida -> sb.append(corrida + "\n"));
		InputStream is = new ByteArrayInputStream(sb.toString().getBytes());
		return is;
	}

	private List<Corrida> ordenarListaPorColocacao(List<Corrida> listaCorridas) {
		Map<String, Corrida> map = new HashMap<>();
		
		for (Corrida corr : listaCorridas) {
			Corrida corrida = map.get(corr.getCodigoPiloto());
			if (corrida == null) {
				Instant duracaoAtual = corr.getTempoVolta().toInstant();
				corr.setTempoTotalProva(duracaoAtual.toEpochMilli());
				map.put(corr.getCodigoPiloto(), corr);
			} else {
				Instant duracaoAtual = Instant.ofEpochMilli(corrida.getTempoTotalProva());
				Instant novaDuracao = corr.getTempoVolta().toInstant();
				Instant novoTempoTotal = duracaoAtual.plusMillis(novaDuracao.toEpochMilli());
				corrida.setTempoTotalProva(novoTempoTotal.toEpochMilli());
				int voltas = corrida.getNumeroVoltas() + 1;
				corrida.setNumeroVoltas(voltas);
			}
		}
		
		List<Corrida> listaOrdenada = new ArrayList<Corrida>(map.values());
		Collections.sort(listaOrdenada, new Comparator<Corrida>() {

			@Override
			public int compare(Corrida o1, Corrida o2) {
				if (o2.getNumeroVoltas() > o1.getNumeroVoltas()  && 
						o1.getTempoTotalProva() > o2.getTempoTotalProva()) {
					return 1;
				}
				return -1;
			}
		});
		
		return listaOrdenada;
	}

}
