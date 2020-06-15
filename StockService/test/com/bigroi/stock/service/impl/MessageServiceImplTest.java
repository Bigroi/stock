//package com.bigroi.stock.service.impl;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.runners.MockitoJUnitRunner;
//
//import com.bigroi.stock.bean.db.Email;
//import com.bigroi.stock.dao.EmailDao;
//import com.bigroi.stock.messager.MailManager;
//import com.bigroi.stock.util.BaseTest;
//
//@RunWith(MockitoJUnitRunner.class)
//public class MessageServiceImplTest extends BaseTest {
//
//	@InjectMocks
//	private MessageServiceImpl messageServicel;
//	@Mock
//	private EmailDao emailDao;
//	@Mock
//	private MailManager mailManager;
//
//	//@Test
//	public void sendAllEmailsTest(){
//		// given
//		final int EMAIL_COUNT = 5;
//
//		List<Email> emailList = new ArrayList<>();
//		for(int i = 0; i < EMAIL_COUNT; i++){
//			emailList.add(createObject(Email.class));
//		}
//
//		// mock
//		Mockito.when(emailDao.getAll()).thenReturn(emailList);
//		Mockito.doNothing().when(mailManager).send(Mockito.any());
//	    Mockito.when(emailDao.deleteById(Mockito.anyLong())).thenReturn(true);
//		// when
//		messageServicel.sendAllEmails();
//		// then
//		Mockito.verify(emailDao, Mockito.times(1)).getAll();
//		Mockito.verify(mailManager, Mockito.times(EMAIL_COUNT)).send(Mockito.any());
//		Mockito.verify(emailDao, Mockito.times(EMAIL_COUNT)).deleteById(Mockito.anyLong());
//	}
//
//	@Test
//	public void addTest(){
//		// given
//		Email email = createObject(Email.class);
//		// mock
//		Mockito.doAnswer(a -> {
//								((Email)a.getArguments()[0]).setId(random.nextLong());
//								return null;
//								})
//			.when(emailDao).add(email);
//		//when
//		messageServicel.add(email);
//		//then
//		Mockito.verify(emailDao, Mockito.times(1)).add(email);
//	}
//}
