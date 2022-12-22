package com.moduretick.step;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.support.ClassifierCompositeItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.moduretick.domain.FaturaCartaoCredito;
import com.moduretick.domain.Pessoa;

@Configuration
public class FaturaCartaoCreditoStepConfig {
	
	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	@Bean
	public Step faturaCartaoCreditoStep(
			ItemReader<FaturaCartaoCredito> lerTransacoesReader,
			ItemProcessor<FaturaCartaoCredito, FaturaCartaoCredito> carregaDadosClienteProcessor,
			ItemWriter<FaturaCartaoCredito> escreverFaturaCartaoCreditoWriter) {
		return stepBuilderFactory
				.get("faturaCartaoCreditoStep")
				.<FaturaCartaoCredito, FaturaCartaoCredito> chunk(1)
				.reader(lerTransacoesReader)
				.processor(carregaDadosClienteProcessor)
				.writer(escreverFaturaCartaoCreditoWriter)
				.build();
		
	}

}
