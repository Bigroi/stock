package com.bigroi.stock.controller.resource;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bigroi.stock.bean.db.CompanyAddress;
import com.bigroi.stock.bean.db.StockUser;
import com.bigroi.stock.bean.ui.AddressForUI;
import com.bigroi.stock.controller.BaseResourseController;
import com.bigroi.stock.json.GsonUtil;
import com.bigroi.stock.json.ResultBean;
import com.bigroi.stock.json.TableResponse;
import com.bigroi.stock.service.AddressService;
import com.bigroi.stock.service.LabelService;

@Controller
@RequestMapping(value = "/address/json", produces = "text/plain;charset=UTF-8")
public class AddressResourceController extends BaseResourseController {
	
	private static final String ADDRESS_CATEGORY = "address";
	private static final String DEFAULT_ADDRESS_LABEL = "default_address";
	private static final String NOT_DEFAULT_ADDRESS_LABEL = "not_default_address";
	private static final String AUTHORISATION_ERROR_LABEL = "not_authorized";
	private static final String DEFAULT_DELETE_ERROR_LABEL = "deffalt_delete_error";
	
	@Autowired
	private AddressService addressService;
	
	@Autowired
	private LabelService labelService;
	
	@RequestMapping(value = "/Get.spr")
	@ResponseBody
	public String getAddress(@RequestParam("id") long id, Authentication loggedInUser){
		long companyId;
		if (loggedInUser != null && loggedInUser.getPrincipal() instanceof StockUser){
			StockUser user = (StockUser) loggedInUser.getPrincipal();
			companyId = user.getCompanyId();
		} else {
			companyId = 0;
		}
		CompanyAddress address = addressService.getAddressById(id, companyId);
		return new ResultBean(1, address, null).toString();
	}
	
	@RequestMapping(value = "/List.spr")
	@ResponseBody
	@Secured(value = { "ROLE_USER", "ROLE_ADMIN" })
	public String getAddresses(Authentication loggedInUser){
		StockUser user = (StockUser) loggedInUser.getPrincipal();
		List<CompanyAddress> listAddresses = addressService.getCompanyAddresses(user.getCompanyId());
		List<AddressForUI> addressesForUI = listAddresses.stream()
				.map(address -> new AddressForUI(
						address,
						address.getId() == user.getCompany().getAddressId() ?
								labelService.getLabel(ADDRESS_CATEGORY, DEFAULT_ADDRESS_LABEL, getLanguage()) :
								labelService.getLabel(ADDRESS_CATEGORY, NOT_DEFAULT_ADDRESS_LABEL, getLanguage())
						))
				.collect(Collectors.toList());
		TableResponse<AddressForUI> table = new TableResponse<>(AddressForUI.class, addressesForUI);
		return new ResultBean(1, table, null).toString();
	}
	
	@RequestMapping(value = "/Form.spr", produces = "text/plain;charset=UTF-8")
	@ResponseBody
	@Secured(value = {"ROLE_USER","ROLE_ADMIN"})
	public String form(@RequestParam(value = "id", defaultValue = "-1") long id, Authentication loggedInUser){
		StockUser user = (StockUser)loggedInUser.getPrincipal();
		CompanyAddress companyAddress = addressService.getAddressById(id, user.getCompanyId());
		if (companyAddress == null){
			return new ResultBean(-1, AUTHORISATION_ERROR_LABEL).toString();
		} else {
			return new ResultBean(1, new AddressForUI(companyAddress), "").toString();
		}
	}
	
	@RequestMapping(value = "/Save.spr")
	@ResponseBody
	@Secured(value = { "ROLE_USER", "ROLE_ADMIN" })
	public String save(@RequestParam("json") String jsonAddress, Authentication loggedInUser){
		StockUser user =  (StockUser) loggedInUser.getPrincipal();
		CompanyAddress address = GsonUtil.getGson().fromJson(jsonAddress, CompanyAddress.class);
		
		if (addressService.hasAddress(address, user.getCompanyId())){
			return new ResultBean(-1, "label.address.already_exists").toString();
		}
		
		addressService.merge(address, user.getCompanyId());
		return new ResultBean(1, new AddressForUI(address,
				address.getId() == user.getCompany().getAddressId() ? 
				labelService.getLabel(ADDRESS_CATEGORY, DEFAULT_ADDRESS_LABEL, getLanguage()) :
				labelService.getLabel(ADDRESS_CATEGORY, NOT_DEFAULT_ADDRESS_LABEL, getLanguage())
			), null).toString();
	}
	
	@RequestMapping(value = "/Delete.spr")
	@ResponseBody
	@Secured(value = { "ROLE_USER", "ROLE_ADMIN" })
	public String delete(@RequestParam("id") long id, Authentication loggedInUser){
		StockUser user = (StockUser)loggedInUser.getPrincipal();
		if (user.getCompany().getAddressId() == id){
			return new ResultBean(-1, DEFAULT_DELETE_ERROR_LABEL).toString();
		}
		addressService.delete(id, user.getCompany().getId());
		return new ResultBean(1, null).toString();
	}
}
