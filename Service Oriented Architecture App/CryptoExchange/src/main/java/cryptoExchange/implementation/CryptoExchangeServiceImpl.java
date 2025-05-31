package cryptoExchange.implementation;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import api.dtos.CryptoExchangeDto;
import api.services.CryptoExchangeService;
import cryptoExchange.model.CryptoExchangeModel;
import cryptoExchange.repository.CryptoExchangeRepository;
import util.exceptions.NoDataFoundException;

@RestController
public class CryptoExchangeServiceImpl implements CryptoExchangeService {
	
	@Autowired
	private CryptoExchangeRepository repo;
	
	@Autowired
	private Environment environment;

	@Override
	public ResponseEntity<?> getExchange(String from, String to) {
		CryptoExchangeModel model = repo.findByFromAndTo(from, to);
		if(model == null) {
			throw new NoDataFoundException("Requested exchange pair [" + from 
					+ " into " + to + "] could not be found");
		}
		return ResponseEntity.ok(convertFromModelToDto(model));
	}
	
	public CryptoExchangeDto convertFromModelToDto(CryptoExchangeModel model) {
		CryptoExchangeDto dto = 
				new CryptoExchangeDto
				(model.getFrom(), model.getTo(), model.getExchangeValue());
		dto.setInstancePort(environment.getProperty("local.server.port"));
		return dto;
	}
}
