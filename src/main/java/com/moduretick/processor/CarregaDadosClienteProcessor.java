package com.moduretick.processor;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.validator.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.moduretick.domain.Cliente;
import com.moduretick.domain.FaturaCartaoCredito;

@Component
public class CarregaDadosClienteProcessor implements ItemProcessor<FaturaCartaoCredito, FaturaCartaoCredito> {
	private RestTemplate restTemplate= new RestTemplate();

	@Override
	public FaturaCartaoCredito process(FaturaCartaoCredito faturaCartaoCredito) throws Exception {
		System.out.println("Cod " + faturaCartaoCredito.getCliente().getId());
		String uri = String.format("http://my-json-server.typicode.com/giuliana-bezerra/demo/profile/%d", 
				faturaCartaoCredito.getCliente().getId());
		
		ResponseEntity<Cliente> response = restTemplate.getForEntity(uri, Cliente.class);
		
		if (response.getStatusCode() != HttpStatus.OK)
			throw new ValidationException("Cliente Nao Encontrado!");
		
		faturaCartaoCredito.setCliente(response.getBody());
		return faturaCartaoCredito;
		
	}
	
}
