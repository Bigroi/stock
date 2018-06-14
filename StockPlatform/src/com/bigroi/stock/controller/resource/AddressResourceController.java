package com.bigroi.stock.controller.resource;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.bigroi.stock.controller.BaseResourseController;
import com.bigroi.stock.json.GsonUtil;
import com.bigroi.stock.json.ResultBean;
import com.bigroi.stock.json.TableException;
import com.bigroi.stock.json.TableResponse;
import com.bigroi.stock.service.AddressService;
import com.bigroi.stock.service.ServiceException;

@Controller
@RequestMapping(value = "/account/json", produces = "text/plain;charset=UTF-8")
public class AddressResourceController extends BaseResourseController {
	
	@Autowired
	private AddressService addressService;
	
	@RequestMapping(value = "/AddressesList.spr")
	@ResponseBody
	public String getAddresses() throws ServiceException, TableException{
		StockUser user = (StockUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		List<Address> listAddresses = addressService.getCompanyAddresses(user.getCompanyId());
		List<AddressForUI> addressesForUI = listAddresses.stream().map(AddressForUI::new).collect(Collectors.toList());
		TableResponse<AddressForUI> table = new TableResponse<>(AddressForUI.class, addressesForUI);
		return new ResultBean(1, table, null).toString();
	}
	
	@RequestMapping(value = "/FormAddress.spr", produces = "text/plain;charset=UTF-8")
	@ResponseBody
	@Secured(value = {"ROLE_USER","ROLE_ADMIN"})
	public String form(@RequestParam("id") long id, Authentication loggedInUser) 
					throws ServiceException {
		StockUser user = (StockUser)loggedInUser.getPrincipal();
		Address address = addressService.getAddressById(id);
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
		addressService.merge(address, user.getCompanyId());
		return new ResultBean(1, new AddressForUI(address), "label.address.save_success").toString();
	}
}
