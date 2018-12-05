package com.bigroi.stock.controller.rendering;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
	@Mock
	private HttpSession session;
	@Mock
	private HttpServletRequest request;
	@Mock
	private Authentication loginUser;
	
	@Test
	public void indexVerifyUserTest(){
		// given
		String PATH = randomString();
		// mock
		Mockito.when(loginUser.getPrincipal()).thenReturn(new StockUser());
		Mockito.when(request.getContextPath()).thenReturn(PATH);
		// when
		ModelAndView modelAndViewActual = commonRenderingController.index("ru_ru", request, loginUser);
		// then
		Assert.assertNotNull(modelAndViewActual);
		Mockito.verify(loginUser, Mockito.times(1)).getPrincipal();
		Mockito.verify(request, Mockito.times(1)).getContextPath();
	}
	
	@Test
	public void indexNotVerifyUserTest(){
		// given
		final String RETURN_PAGE = "index";
		// mock
		Mockito.when(loginUser.getPrincipal()).thenReturn(new Object());
		// whenW
		ModelAndView modelAndViewActual = commonRenderingController.index("ru_ru", request, loginUser);
		// then
		Assert.assertEquals(RETURN_PAGE, modelAndViewActual.getViewName());
		Mockito.verify(loginUser, Mockito.times(1)).getPrincipal();
	}
	
	@Test
	public void resetPasswordTest(){
		// given
		String CODE = randomString();
		String EMAIL = randomString();
		String MESSAGE = randomString();
		final String RETURN_PAGE = "resetPassw";
		// mock
		Mockito.when(userService.changePassword(EMAIL, CODE)).thenReturn(true);
		Mockito.when(labelService.getLabel(Mockito.anyString(), Mockito.anyString(), Mockito.anyObject())).thenReturn(MESSAGE);
		// when
		ModelAndView modelAndViewActual = commonRenderingController.resetPassword(CODE, EMAIL);
		String actualMessage = modelAndViewActual.getModel().get("message").toString();
		// then
		Assert.assertEquals(RETURN_PAGE, modelAndViewActual.getViewName());
		Assert.assertEquals(MESSAGE, actualMessage);
		Mockito.verify(userService, Mockito.times(1)).changePassword(EMAIL, CODE);
		//Mockito.verify(labelService, Mockito.times(1)).getLabel(Mockito.any(), Mockito.any(), Mockito.any());
	}
	
	@Test
	public void NotResetPasswordTest(){
		// given
		String CODE = randomString();
		String EMAIL = randomString();
		String MESSAGE = randomString();
		final String RETURN_PAGE = "resetPassw";
		// mock
		Mockito.when(userService.changePassword(EMAIL, CODE)).thenReturn(false);
		Mockito.when(labelService.getLabel(Mockito.anyString(), Mockito.anyString(), Mockito.anyObject())).thenReturn(MESSAGE);
		// when
		ModelAndView modelAndViewActual = commonRenderingController.resetPassword(CODE, EMAIL);
		String actualMessage = modelAndViewActual.getModel().get("message").toString();
		// then
		Assert.assertEquals(RETURN_PAGE, modelAndViewActual.getViewName());
		Assert.assertEquals(MESSAGE, actualMessage);
		Mockito.verify(userService, Mockito.times(1)).changePassword(EMAIL, CODE);
		//Mockito.verify(labelService, Mockito.times(1)).getLabel(Mockito.any(), Mockito.any(), Mockito.any());
	}
	
	
}

	
	
	


