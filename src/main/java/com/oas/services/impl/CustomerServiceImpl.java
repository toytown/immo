package com.oas.services.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.mail.internet.MimeMessage;

import org.apache.velocity.app.VelocityEngine;
import org.apache.wicket.PageParameters;
import org.apache.wicket.RequestCycle;
import org.apache.wicket.protocol.http.RequestUtils;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.velocity.VelocityEngineUtils;

import com.oas.dao.ICustomerDAO;
import com.oas.model.CustomerContactDetails;
import com.oas.model.Customer;
import com.oas.services.ICustomerService;
import com.oas.web.pages.CustomerActivationPage;
import com.oas.web.search.ContactRequest;

@Transactional
public class CustomerServiceImpl implements ICustomerService {

	private JavaMailSender mailSender;
	private VelocityEngine velocityEngine;
	private ICustomerDAO customerDAO;

	public VelocityEngine getVelocityEngine() {
		return velocityEngine;
	}

	public void setVelocityEngine(VelocityEngine velocityEngine) {
		this.velocityEngine = velocityEngine;
	}

	public JavaMailSender getMailSender() {
		return mailSender;
	}

	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	public ICustomerDAO getUserDAO() {
		return customerDAO;
	}

	public void setCustomerDAO(ICustomerDAO customerDAO) {
		this.customerDAO = customerDAO;
	}

	public Customer getCustomer(String userName, String password) {
		return customerDAO.getCustomer(userName, password);
	}

	@Transactional
	public boolean sendActivationEmail(Customer customer) {
		if (customer != null && customer.getContactDetails() != null && !customer.getContactDetails().isEmpty()) {
			PageParameters pm = new PageParameters();
			pm.put("userId", customer.getId().toString());
			pm.put("activationCode", customer.getActivationCode());
			
			CharSequence charSeq = RequestCycle.get().urlFor(CustomerActivationPage.class, pm);
			String url = RequestUtils.toAbsolutePath(charSeq.toString());
			sendEmail(customer, url);
			return true;
		}
		return false;
	}

	@Transactional
	public boolean sendPasswordEmail(String userName, String oldPassword) {
		if (userName != null && oldPassword != null) {
			Customer customer = getCustomer(userName, oldPassword);
			String newPassword = generateNewPassword(customer);
			customer.setPassword(newPassword);
			this.update(customer);
			return true;
		}
		return false;
	}
	
	public String generateNewPassword(Customer customer) {
		String oldPassword = customer.getPassword();
		String newPassword = customer.getUserName() + oldPassword .hashCode() * 13;
		return newPassword;
	}
	
	@Transactional
	public void save(Customer aUser) {
		aUser.setActivationCode(UUID.randomUUID().toString());
		customerDAO.save(aUser);
		sendActivationEmail(aUser);
	}

	@Transactional
	public Customer update(Customer aUser) {
		return customerDAO.update(aUser);
	}

	public Customer load(long userId) {
		return customerDAO.findById(userId);
	}

	private void sendEmail(final Customer customer, final String url) {
		MimeMessagePreparator preparator = new MimeMessagePreparator() {
			public void prepare(MimeMessage mimeMessage) throws Exception {
				MimeMessageHelper message = new MimeMessageHelper(mimeMessage);

				for (CustomerContactDetails contact : customer.getContactDetails()) {
					message.setTo(contact.getEmail());
				}

				message.setFrom("ptuladhar@gmx.net");
				Map model = new HashMap();
				model.put("user", customer);
				model.put("url", url);
				String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "registration-confirmation.vm", model);
				message.setText(text, true);
			}
		};
		this.mailSender.send(preparator);
	}

	@Transactional
	public Customer findCustomerById(Long userId) {
		return customerDAO.findById(userId);
	}

	public void sendContactMessage(final Customer customer, final ContactRequest contactRequest) {
		MimeMessagePreparator preparator = new MimeMessagePreparator() {
			public void prepare(MimeMessage mimeMessage) throws Exception {
				MimeMessageHelper message = new MimeMessageHelper(mimeMessage);

				for (CustomerContactDetails contact : customer.getContactDetails()) {
					message.setTo(contact.getEmail());
				}

				message.setFrom(contactRequest.getEmail());
				Map model = new HashMap();
				model.put("user", customer);
				String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "contact_information.vm", model);
				message.setText(text, true);
			}
		};
		this.mailSender.send(preparator);		
	}
}
