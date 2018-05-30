package com.bigroi.stock.controller.resource;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bigroi.stock.bean.db.Address;
import com.bigroi.stock.bean.db.StockUser;
import com.bigroi.stock.bean.ui.AddressForUI;
import com.bigroi.stock.json.GsonUtil;
import com.bigroi.stock.json.ResultBean;
import com.bigroi.stock.json.TableException;
import com.bigroi.stock.json.TableResponse;
import com.bigroi.stock.service.ServiceException;
import com.bigroi.stock.service.ServiceFactory;

@Controller
@RequestMapping(value = "/account/json", produces = "text/plain;charset=UTF-8")
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
	
	@RequestMapping(value = "/EditAddress.spr", produces = "text/plain;charset=UTF-8")
	@ResponseBody
	@Secured(value = {"ROLE_USER","ROLE_ADMIN"})
	public String form(@RequestParam("id") long id, Authentication loggedInUser) 
					throws ServiceException {
		StockUser user = (StockUser)loggedInUser.getPrincipal();
		Address address = ServiceFactory.getAddressService().getAddressById(id);
		if (user.getCompanyId() != user.getCompanyId()){
			return new ResultBean(-1, "label.address.not_authorized").toString();
		} else {
			return new ResultBean(1, new AddressForUI(address), "").toString();
		}
	}
	
	@RequestMapping(value = "/SaveAddress.spr")
	@ResponseBody
	@Secured(value = { "ROLE_USER", "ROLE_ADMIN" })
	public String save(@RequestParam("json") String jsonAddress) throws ServiceException{
		StockUser user =  (StockUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Address address = GsonUtil.getGson().fromJson(jsonAddress, Address.class);
		ServiceFactory.getAddressService().merge(address, user.getCompanyId());
		return new ResultBean(1, new AddressForUI(address), "label.address.save_success").toString();
	}
}
