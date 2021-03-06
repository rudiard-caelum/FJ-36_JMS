package br.com.caelum.jms;

import java.util.Scanner;

import javax.jms.ConnectionFactory;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.Topic;
import javax.naming.InitialContext;

public class RegistraGeradorNoTopico {
	public static void main(String[] args) throws Exception {
		InitialContext initialContext = new InitialContext();
		ConnectionFactory factory = (ConnectionFactory) initialContext
				.lookup("jms/RemoteConnectionFactory");
		Topic topico = (Topic) initialContext.lookup("jms/TOPICO.LIVRARIA");
		try (JMSContext context = factory.createContext("jms", "jms2")) {
			// context.setClientID("GeradorEbook");
			// JMSConsumer consumer = context.createDurableConsumer(topico,
			// "AssinaturaEbook", "formato='ebook'", false);
			JMSConsumer consumer = context.createSharedDurableConsumer(topico,
					"AssinaturaEbook", "formato='ebook'");
			consumer.setMessageListener(new TratadorDeMensagem());
			context.start();
			Scanner teclado = new Scanner(System.in);
			System.out.println("Gerador esperando as mensagens do tópico ...");
			System.out.println("Aperte enter para fechar a conexão");
			teclado.nextLine();
			teclado.close();
			context.stop();
		}

	}
}
