package com.bigroi.stock.controller.resource;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bigroi.stock.bean.db.Address;
import com.bigroi.stock.bean.db.StockUser;
import com.bigroi.stock.bean.ui.AddressForUI;
import com.bigroi.stock.json.ResultBean;
import com.bigroi.stock.json.TableException;
import com.bigroi.stock.json.TableResponse;
import com.bigroi.stock.service.ServiceException;
import com.bigroi.stock.service.ServiceFactory;

@Controller
@RequestMapping("/account/json")
public class AddressResourceController extends BaseResourseController {
	
	@RequestMapping(value = "/AddressesList.spr")
	@ResponseBody
	public String getAddresses() throws ServiceException, TableException{
		StockUser user = (StockUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		List<Address> listAddresses = ServiceFactory.getAddressService().getCompanyAddresses(user.getCompanyId());
		List<AddressForUI> addressesForUI = listAddresses.stream().map(AddressForUI::new).collect(Collectors.toList());
		TableResponse<AddressForUI> table = new TableResponse<>(AddressForUI.class, addressesForUI);
		return new ResultBean(1, table, null).toString();
	}
	
	/*@RequestMapping("/EditAddress.spr")
	@ResponseBody
	@Secured(value = {"ROLE_USER","ROLE_ADMIN"})
	public String form(@RequestParam(value = "id", defaultValue = "-1") long id) throws ServiceException{
		Address address = ServiceFactory.getAddressService().getAddressById(id);
		
		return new ResultBean(1, address, null).toString();
		
	}*/

}
