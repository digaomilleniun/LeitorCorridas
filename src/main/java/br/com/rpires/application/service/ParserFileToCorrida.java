/**
 * 
 */
package br.com.rpires.application.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.rpires.application.entity.Corrida;
import br.com.rpires.application.utils.DateUtils;

/**
 * @author Rodrigo Pires
 *
 */
@Service
public class ParserFileToCorrida implements IParserFileToCorrida {
	
	private static final long serialVersionUID = 14575757571111L;
	
	@Autowired
	private DateUtils dateUtils;

	public Corrida parser(String line)  {
		System.out.println(line);
		line = line.replace(",", ".").replaceAll("\t", " ").replaceAll(" ", "|");
		String[] colunas = line.split("\\|");
		String hora = getPorPosicao(colunas, 0);  //colunas[0];
		String codigoPiloto = getPorPosicao(colunas, 1); //colunas[6];
		String piloto = getPorPosicao(colunas, 3); //colunas[8];
		String nrVoltasSTR = getPorPosicao(colunas, 4); //colunas[35];
		String tempoVoltaSTR = getPorPosicao(colunas, 5); //colunas[37];
		String velocidadeSTR = getPorPosicao(colunas, 6); //colunas[61];
		
		Corrida corrida = new Corrida();
		corrida.setCodigoPiloto(codigoPiloto);
		corrida.setPiloto(piloto);
		corrida.setNumeroVoltas(Integer.parseInt(nrVoltasSTR));
		corrida.setVelocidadeMedia(Double.parseDouble(velocidadeSTR));
		corrida.setHora(getHora(hora, "HH:mm:ss.SSS"));
		corrida.setTempoVolta(getHora(tempoVoltaSTR, "mm:ss.SSS"));
		return corrida;
	}
	
	public Date getHora(String hora, String formato) {
		return dateUtils.getDateFromString(hora, formato);
	}
	
	public String getPorPosicao(String[] colunas, int posicao) {
		int encontrado = 0;
		for (String coluna : colunas) {
			if (!"".equals(coluna)) {
				if (encontrado == posicao) {
					return coluna;
				}
				encontrado ++;
			}
		}
		return null;
	}
	
}
