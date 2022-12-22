package com.moduretick.step;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.moduretick.domain.FaturaCartaoCredito;
import com.moduretick.domain.Transacao;
import com.moduretick.reader.FaturaCartaoCreditoReader;
import com.moduretick.writer.TotalTransacoesFooterCallback;

@Configuration
public class FaturaCartaoCreditoStepConfig {
	
	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	@Bean
	public Step faturaCartaoCreditoStep(
			ItemStreamReader<Transacao> lerTransacoesReader,
			ItemProcessor<FaturaCartaoCredito, FaturaCartaoCredito> carregaDadosClienteProcessor,
			ItemWriter<FaturaCartaoCredito> escreverFaturaCartaoCreditoWriter,
			TotalTransacoesFooterCallback listener ) {
		return stepBuilderFactory
				.get("faturaCartaoCreditoStep")
				.<FaturaCartaoCredito, FaturaCartaoCredito> chunk(1)
				.reader(new FaturaCartaoCreditoReader(lerTransacoesReader))
				.processor(carregaDadosClienteProcessor)
				.writer(escreverFaturaCartaoCreditoWriter)
				.listener(listener)
				.build();
		
	}

}
