package com.bigroi.stock.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.bigroi.stock.bean.db.Email;
import com.bigroi.stock.dao.DaoException;
import com.bigroi.stock.dao.EmailDao;
import com.bigroi.stock.messager.MailManager;
import com.bigroi.stock.messager.MailManagerException;
import com.bigroi.stock.service.ServiceException;
import com.bigroi.stock.util.BaseTest;

@RunWith(MockitoJUnitRunner.class)
public class MessageServiceImplTest extends BaseTest {

	@InjectMocks
	private MessageServiceImpl messageServicel;
	@Mock
	private EmailDao emailDao;
	@Mock
	private MailManager mailManager;
	
	//@Test
	public void sendAllEmailsTest() throws DaoException, MailManagerException, ServiceException{
		// given
		final long EMAIL_ID = random.nextLong();
		// mock
		Email email = createObject(Email.class);
		email.setId(EMAIL_ID);
		List<Email> emailList = new ArrayList<>();//ImmutableList.of(email);
		emailList.add(email);
		
		Mockito.when(emailDao.getAll()).thenReturn(emailList);
		Mockito.doNothing().when(mailManager).send(email);
	    Mockito.when(emailDao.deleteById(EMAIL_ID)).thenReturn(true);
	    Mockito.stub(!emailList.isEmpty()).toReturn(true);
		// when
		messageServicel.sendAllEmails();
		// then
		Mockito.verify(emailDao, Mockito.times(1)).getAll();
		Mockito.verify(mailManager, Mockito.times(1)).send(email);
		Mockito.verify(emailDao, Mockito.times(1)).deleteById(EMAIL_ID);
	}
	
	@Test
	public void addTest() throws DaoException, ServiceException{
		// given
		Email email = createObject(Email.class);
		// mock
		Mockito.doAnswer(a -> {
								((Email)a.getArguments()[0]).setId(random.nextLong()); 
								return null;
								})
			.when(emailDao).add(email);
		//when
		messageServicel.add(email);
		//then
		Mockito.verify(emailDao, Mockito.times(1)).add(email);
	}
}
