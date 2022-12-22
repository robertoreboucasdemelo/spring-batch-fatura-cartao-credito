package com.moduretick.reader;

import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.ItemStreamReader;

import com.moduretick.domain.FaturaCartaoCredito;
import com.moduretick.domain.Transacao;

public class FaturaCartaoCreditoReader implements ItemStreamReader<FaturaCartaoCredito> {
	
	private ItemStreamReader<Transacao> delegate;
	private Transacao transacaoAtual;
	
	@Override
	public FaturaCartaoCredito read() throws Exception {
		if (transacaoAtual == null) 
			transacaoAtual = delegate.read();
		
		FaturaCartaoCredito faturaCartaoCredito = null;
		Transacao transacao = transacaoAtual;
		transacaoAtual = null;
		
		if (transacao != null) {
			faturaCartaoCredito = new FaturaCartaoCredito();
			faturaCartaoCredito.setCartaoCredito(transacao.getCartaoCredito());
			faturaCartaoCredito.setCliente(transacao.getCartaoCredito().getCliente());
			faturaCartaoCredito.getTransacoes().add(transacao);
			
			while (isTransacaoRelacionada(transacao))
				faturaCartaoCredito.getTransacoes().add(transacaoAtual);
		}
		
		return faturaCartaoCredito;
	}

	private boolean isTransacaoRelacionada(Transacao transacao) throws Exception {
		return peek() != null && 
				transacao.getCartaoCredito().getNumeroCartaoCredito() == transacaoAtual.getCartaoCredito().getNumeroCartaoCredito();
	}

	private Transacao peek() throws Exception {
		transacaoAtual = delegate.read();
		return transacaoAtual;
	}

	public FaturaCartaoCreditoReader(ItemStreamReader<Transacao> delegate) {
		this.delegate = delegate;
	}

	@Override
	public void open(ExecutionContext executionContext) throws ItemStreamException {
		delegate.open(executionContext);
	}

	@Override
	public void update(ExecutionContext executionContext) throws ItemStreamException {
		delegate.update(executionContext);
	}

	@Override
	public void close() throws ItemStreamException {
		delegate.close();
	}

}
