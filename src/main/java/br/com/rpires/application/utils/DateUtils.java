/**
 * 
 */
package br.com.rpires.application.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;

import org.springframework.stereotype.Component;

/**
 * @author Rodrigo Pires
 *
 */
@Component
public class DateUtils {
	
	private Logger logger = Logger.getLogger(DateUtils.class.getName());

	public Date getDateFromString(String hora, String formato) {
		SimpleDateFormat sdf = new SimpleDateFormat(formato);
		Date h = null;
		try {
			h = sdf.parse(hora);
		} catch (ParseException e) {
			logger.info(" ## ERRO CONVERTENDO DATA ##");
			throw new RuntimeException(e);
		}
		return h;
	}
	
	public String formatter(Date data, String formato) {
		SimpleDateFormat sdf = new SimpleDateFormat(formato);
		return sdf.format(data);
	}
	
	public String converMillisecondToHour(long hora) {
		long segundos = hora / 1000;
		long minutos = segundos / 60;
		segundos = segundos % 60;
		long horas = minutos / 60;
		minutos = minutos % 60;
		String tempo = String.format ("%02d:%02d:%02d", horas, minutos, segundos); 
		return tempo;
	}
}
