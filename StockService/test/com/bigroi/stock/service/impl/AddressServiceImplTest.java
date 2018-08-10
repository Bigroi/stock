package com.bigroi.stock.service.impl;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.bigroi.stock.bean.db.CompanyAddress;
import com.bigroi.stock.dao.AddressDao;
import com.bigroi.stock.util.BaseTest;
import com.google.common.collect.ImmutableList;

@RunWith(MockitoJUnitRunner.class)
public class AddressServiceImplTest extends BaseTest {

	@InjectMocks
	private AddressServiceImpl addressService;
	@Mock
	private AddressDao addressDao;
	
	@Test
	public void getCompanyAddressesTest()  {
		// given
		final long COMPANY_ID = random.nextLong();
		//mock
		CompanyAddress company = new CompanyAddress();
		company.setCompanyId(COMPANY_ID);
		List<CompanyAddress> expectedList = ImmutableList.of(company);
		Mockito.when(addressDao.getAddressesForCompany(COMPANY_ID)).thenReturn(expectedList);
		// when
		List<CompanyAddress> actualList = addressService.getCompanyAddresses(COMPANY_ID);
		// then
		Assert.assertEquals(expectedList, actualList);
		Mockito.verify(addressDao, Mockito.times(1)).getAddressesForCompany(COMPANY_ID);
	}

	@Test
	public void getAddressByIdTest() {
		// given
		final long ADDRESS_ID = random.nextLong();
		final long COMPANY_ID = random.nextLong();

		CompanyAddress expectedAddress = new CompanyAddress();
		expectedAddress.setCompanyId(COMPANY_ID);
		//mock
		Mockito.when(addressDao.getAddressById(ADDRESS_ID, COMPANY_ID)).thenReturn(expectedAddress);
		// when
		CompanyAddress actualAddress = addressService.getAddressById(ADDRESS_ID, COMPANY_ID);
		CompanyAddress actualNewAddress = addressService.getAddressById(-1, COMPANY_ID);
		// then
		Assert.assertEquals(expectedAddress, actualAddress);
		Assert.assertEquals(-1, (int) actualNewAddress.getLatitude());
		Assert.assertEquals(-1, (int) actualNewAddress.getLongitude());
		Assert.assertEquals(-1, actualNewAddress.getId());
		Mockito.verify(addressDao, Mockito.times(1)).getAddressById(ADDRESS_ID, COMPANY_ID);
	}
	
	@Test
	public void mergeAddTest() {
		// given
		final long COMPANY_ID = random.nextLong();
		CompanyAddress newAddress = createObject(CompanyAddress.class);
		newAddress.setId(-1);
		//mock
		Mockito.doAnswer(a -> {
								((CompanyAddress)a.getArguments()[0]).setId(random.nextLong()); 
								return null;
								})
			.when(addressDao).addAddress(newAddress);
		// when
		addressService.merge(newAddress, COMPANY_ID);
		// then
		Assert.assertEquals(COMPANY_ID, newAddress.getCompanyId());
		Assert.assertNotEquals(-1, newAddress.getId());
		Mockito.verify(addressDao, Mockito.times(1)).addAddress(newAddress);
	}
	
	@Test
	public void mergeUpdateTest() {
		// given
		final long COMPANY_ID = random.nextLong();
		CompanyAddress updateAddress = new CompanyAddress();
		// when
		addressService.merge(updateAddress, COMPANY_ID);
		// then
		Assert.assertEquals(COMPANY_ID, updateAddress.getCompanyId());	
		Mockito.verify(addressDao, Mockito.times(1)).updateAddress(updateAddress);
	}
	
	@Test
	public void deleteTest() {
		// given
		final long ADDRESS_ID = random.nextLong();
		final long COMPANY_ID = random.nextLong();
		//mock
		Mockito.when(addressDao.deleteAddress(ADDRESS_ID, COMPANY_ID)).thenReturn(true);
		// when
		addressService.delete(ADDRESS_ID, COMPANY_ID);
		// then
		Mockito.verify(addressDao, Mockito.times(1)).deleteAddress(ADDRESS_ID, COMPANY_ID);
	}
	
	@Test
	public void hasAddressTest() {
		// given
		final long COMPANY_ID = random.nextLong();
		CompanyAddress hasAddress = new CompanyAddress();
		//mock
		Mockito.when(addressDao.hasAddress(hasAddress, COMPANY_ID)).thenReturn(true);
		// when
		addressService.hasAddress(hasAddress, COMPANY_ID);
		// then
		Mockito.verify(addressDao, Mockito.times(1)).hasAddress(hasAddress, COMPANY_ID);
	}
}
