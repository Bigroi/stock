//package com.bigroi.stock.service.impl;
//
//import java.util.List;
//
//import org.junit.Assert;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.runners.MockitoJUnitRunner;
//
//import com.bigroi.stock.bean.db.Proposition;
//import com.bigroi.stock.dao.PropositionDao;
//import com.bigroi.stock.util.BaseTest;
//import com.google.common.collect.ImmutableList;
//
//@RunWith(MockitoJUnitRunner.class)
//public class PropositionServiceImplTest extends BaseTest {
//
//	@InjectMocks
//	private PropositionServiceImpl propService;
//	@Mock
//	private PropositionDao propostionDao;
//
//	@Test
//	public void getListPropositionTest(){
//		// given
//		Proposition prop = createObject(Proposition.class);
//		List<Proposition> listProp = ImmutableList.of(prop);
//		// mock
//		Mockito.when(propostionDao.getListPropositions()).thenReturn(listProp);
//		// when
//		List<Proposition> actualProp = propService.getListProposition();
//		// then
//		Assert.assertEquals(listProp, actualProp);
//		Mockito.verify(propostionDao, Mockito.timeout(1)).getListPropositions();
//	}
//
//	@Test
//	public void deleteTest(){
//		// given
//		final long PROPOSTION_ID = random.nextLong();
//		final long COMAPNY_ID = random.nextLong();
//		// mock
//		Mockito.when(propostionDao.deleteProposition(PROPOSTION_ID, PROPOSTION_ID)).thenReturn(true);
//		// when
//		propService.delete(PROPOSTION_ID, COMAPNY_ID);
//		// then
//		Mockito.verify(propostionDao, Mockito.timeout(1)).deleteProposition(PROPOSTION_ID, COMAPNY_ID);
//	}
//
//	@Test
//	public void getListHystoryPropositionTest(){
//		// given
//		final long COMAPNY_ID = random.nextLong();
//
//		Proposition prop = createObject(Proposition.class);
//		List<Proposition> listProp = ImmutableList.of(prop);
//		// mock
//		Mockito.when(propostionDao.getListPropositionsByStatusAndUserId(COMAPNY_ID)).thenReturn(listProp);
//		// when
//		List<Proposition> actualProp = propService.getListHystoryProposition(COMAPNY_ID);
//		// then
//		Assert.assertEquals(listProp, actualProp);
//		Mockito.verify(propostionDao, Mockito.timeout(1)).getListPropositionsByStatusAndUserId(COMAPNY_ID);
//	}
//
//	@Test
//	public void getListPropositionsByTransTest(){
//		// given
//		Proposition prop = createObject(Proposition.class);
//		List<Proposition> listProp = ImmutableList.of(prop);
//		// mock
//		Mockito.when(propostionDao.getListPropositionsTrans()).thenReturn(listProp);
//		// when
//		List<Proposition> actualProp = propService.getListPropositionsByTrans();
//		// then
//		Assert.assertEquals(listProp, actualProp);
//		Mockito.verify(propostionDao, Mockito.timeout(1)).getListPropositionsTrans();
//	}
//
//}
