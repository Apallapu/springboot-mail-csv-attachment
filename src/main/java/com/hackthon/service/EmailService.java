package com.hackthon.service;

import com.hackthon.config.EmailConfiguration;
import com.hackthon.model.Customer;
import com.opencsv.CSVWriter;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.activation.DataSource;
import javax.mail.internet.MimeMessage;
import javax.mail.util.ByteArrayDataSource;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

@Service
public class EmailService {
    @Autowired
    private EmailConfiguration emailConfiguration;


    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSenderImpl = new JavaMailSenderImpl();
        mailSenderImpl.setHost(emailConfiguration.getHost());
        mailSenderImpl.setPort(emailConfiguration.getPort());
        mailSenderImpl.setUsername(emailConfiguration.getUsername());
        mailSenderImpl.setPassword(emailConfiguration.getPassword());
        return mailSenderImpl;
    }

    public void sendSimpleMessage(String to, String from, String subject, String text, String customerReferenceId) throws Exception {
        MimeMessage msg = getJavaMailSender().createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(msg, true);

        helper.setFrom(from);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(text);


		StringWriter writer = new StringWriter();
		ColumnPositionMappingStrategy mappingStrategy =
				new ColumnPositionMappingStrategy();
		mappingStrategy.setType(Customer.class);

		String[] columns = new String[]
				{ "customerName", "customerReferenceId", "email", "status" };
		mappingStrategy.setColumnMapping(columns);

		StatefulBeanToCsvBuilder<Customer> builder = new StatefulBeanToCsvBuilder(writer);
		StatefulBeanToCsv beanWriter = builder
				.withMappingStrategy(mappingStrategy)
				.withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
				.build();

		List<Customer> rows=new ArrayList<>();
		Customer customer=new Customer();
		customer.setCustomerName("ankamma");
		customer.setCustomerReferenceId(customerReferenceId);
		customer.setEmail("test@gmil.com");
		customer.setStatus("ACTIVE");
		Customer customer2=new Customer();
		customer2.setCustomerName("SUMAN");
		customer2.setCustomerReferenceId(customerReferenceId);
		customer2.setEmail("SUMAN@gmil.com");
		customer2.setStatus("ACTIVE");
		rows.add(customer);
		rows.add(customer2);
		beanWriter.write(rows);


		String content = writer.getBuffer().toString();

        DataSource dataSource = new ByteArrayDataSource(content, "text/csv");
        helper.addAttachment(customerReferenceId + ".csv", dataSource);
        getJavaMailSender().send(msg);
    }
}
