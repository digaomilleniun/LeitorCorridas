/**
 * 
 */
package br.com.rpires.application.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.List;

import br.com.rpires.application.entity.Corrida;

/**
 * @author Rodrigo Pires
 *
 */
public interface ILeitorCorrida extends Serializable {

	public List<Corrida> lerArquivo(InputStream inputStream) throws IOException;
	
	public InputStream lerRetornarStream(InputStream inputStream) throws IOException;
}
