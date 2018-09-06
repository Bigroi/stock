package com.bigroi.stock.controller.rendering;

import javax.servlet.http.HttpServletRequest;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.core.Authentication;
import org.springframework.web.servlet.ModelAndView;

import com.bigroi.stock.bean.db.StockUser;
import com.bigroi.stock.controller.BaseRenderingController;
import com.bigroi.stock.service.LabelService;
import com.bigroi.stock.service.UserService;
import com.bigroi.stock.util.BaseTest;

@RunWith(MockitoJUnitRunner.class)
public class CommonRenderingControllerTest extends BaseTest {
	
	@InjectMocks
	private CommonRenderingController commonRenderingController;
	@Mock
	private UserService userService;
	@Mock
	private LabelService labelService;
	
	@Test
	public void indexVerifyUserTest(){
		// given
		Authentication LOGIN_USER = Mockito.mock(Authentication.class);
		HttpServletRequest REQUEST = Mockito.mock(HttpServletRequest.class);
		String PATH = randomString();
		// mock
		Mockito.when(LOGIN_USER.getPrincipal()).thenReturn(new StockUser());
		Mockito.when(REQUEST.getContextPath()).thenReturn(PATH);
		// when
		ModelAndView modelAndViewActual = commonRenderingController.index(REQUEST, LOGIN_USER);
		// then
		Assert.assertNotNull(modelAndViewActual);
		Mockito.verify(LOGIN_USER, Mockito.times(1)).getPrincipal();
		Mockito.verify(REQUEST, Mockito.times(1)).getContextPath();
	}
	
	@Test
	public void indexNotVerifyUserTest(){
		// given
		Authentication LOGIN_USER = Mockito.mock(Authentication.class);
		HttpServletRequest REQUEST = Mockito.mock(HttpServletRequest.class);
		String PATH = randomString();
		CommonRenderingController com = new CommonRenderingController();
		ModelAndView modelAndView = new ModelAndView();
		// mock
		Mockito.when(LOGIN_USER.getPrincipal()).thenReturn(new Object());
		Mockito.when(REQUEST.getContextPath()).thenReturn(PATH);
		// when
		ModelAndView modelAndViewActual = commonRenderingController.index(REQUEST, LOGIN_USER);
		
		
		// then
		//Assert.assertNotNull(modelAndViewActual);
		//Mockito.verify(LOGIN_USER, Mockito.times(1)).getPrincipal();
		//Mockito.verify(REQUEST, Mockito.times(1)).getContextPath();
	}

	
	}
	


