package com.bigroi.stock.messager;

import java.util.List;

import com.bigroi.stock.bean.Email;
import com.bigroi.stock.dao.DaoException;
import com.bigroi.stock.dao.DaoFactory;

public class SendEmail implements Runnable {

	@Override
	public void run() {
		try {
			List<Email> emails;
			do {
				emails = DaoFactory.getEmailDao().getAll();
				for (Email email : emails) {
					MessagerFactory.getMailManager().send(email.getToEmail(), email.getEmailSubject(), email.getEmailText());
					DaoFactory.getEmailDao().deleteById(email.getId());
				}
			} while (!emails.isEmpty());
			
		} catch (DaoException | MailManagerException e) {
			MessagerFactory.getMailManager().sendToAdmin(e);
			e.printStackTrace();
		}

	}

}
