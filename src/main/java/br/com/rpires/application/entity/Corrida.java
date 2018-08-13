/**
 * 
 */
package br.com.rpires.application.entity;

import java.io.Serializable;
import java.util.Date;

import br.com.rpires.application.utils.DateUtils;

/**
 * @author Rodrigo Pires
 *
 */
public class Corrida implements Serializable, Comparable<Corrida> {

	private static final long serialVersionUID = 12434346665L;
	
	private DateUtils dateUtils;

	private Date hora;
	
	private String codigoPiloto;
	
	private String piloto;
	
	private int numeroVoltas;
	
	private Date tempoVolta;
	
	private double velocidadeMedia;
	
	private long tempoTotalProva;
	
	public Corrida() {
		this.dateUtils = new DateUtils();
	}
	
	public Date getHora() {
		return hora;
	}

	public void setHora(Date hora) {
		this.hora = hora;
	}

	public String getPiloto() {
		return piloto;
	}

	public void setPiloto(String piloto) {
		this.piloto = piloto;
	}

	public int getNumeroVoltas() {
		return numeroVoltas;
	}

	public void setNumeroVoltas(int numeroVoltas) {
		this.numeroVoltas = numeroVoltas;
	}

	public Date getTempoVolta() {
		return tempoVolta;
	}

	public void setTempoVolta(Date tempoVolta) {
		this.tempoVolta = tempoVolta;
	}

	public double getVelocidadeMedia() {
		return velocidadeMedia;
	}

	public void setVelocidadeMedia(double velocidadeMedia) {
		this.velocidadeMedia = velocidadeMedia;
	}

	public String getCodigoPiloto() {
		return codigoPiloto;
	}

	public void setCodigoPiloto(String codigoPiloto) {
		this.codigoPiloto = codigoPiloto;
	}
	
	public long getTempoTotalProva() {
		return tempoTotalProva;
	}

	public void setTempoTotalProva(long tempoTotalProva) {
		this.tempoTotalProva = tempoTotalProva;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigoPiloto == null) ? 0 : codigoPiloto.hashCode());
		result = prime * result + ((hora == null) ? 0 : hora.hashCode());
		result = prime * result + numeroVoltas;
		result = prime * result + ((piloto == null) ? 0 : piloto.hashCode());
		result = prime * result + ((tempoVolta == null) ? 0 : tempoVolta.hashCode());
		long temp;
		temp = Double.doubleToLongBits(velocidadeMedia);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Corrida other = (Corrida) obj;
		if (codigoPiloto == null) {
			if (other.codigoPiloto != null)
				return false;
		} else if (!codigoPiloto.equals(other.codigoPiloto))
			return false;
		if (hora == null) {
			if (other.hora != null)
				return false;
		} else if (!hora.equals(other.hora))
			return false;
		if (numeroVoltas != other.numeroVoltas)
			return false;
		if (piloto == null) {
			if (other.piloto != null)
				return false;
		} else if (!piloto.equals(other.piloto))
			return false;
		if (tempoVolta == null) {
			if (other.tempoVolta != null)
				return false;
		} else if (!tempoVolta.equals(other.tempoVolta))
			return false;
		if (Double.doubleToLongBits(velocidadeMedia) != Double.doubleToLongBits(other.velocidadeMedia))
			return false;
		return true;
	}
	
	@Override
	public int compareTo(Corrida p) {
		if (this.numeroVoltas > p.getNumeroVoltas()) {
			return 1;
		}
		return -1;
	}

	@Override
	public String toString() {
		return "Código Piloto=" + codigoPiloto + ", Nome Piloto=" + piloto + ", Qtde Voltas Completadas=" + numeroVoltas + ", "
				+ "Tempo Total de Prova=" + dateUtils.converMillisecondToHour(tempoTotalProva);
	}
	
}
