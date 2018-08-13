/**
 * 
 */
package br.com.rpires.application.service;

import java.io.Serializable;

import br.com.rpires.application.entity.Corrida;

/**
 * @author Rodrigo Pires
 *
 */
public interface IParserFileToCorrida extends Serializable {

	public Corrida parser(String line);
}
