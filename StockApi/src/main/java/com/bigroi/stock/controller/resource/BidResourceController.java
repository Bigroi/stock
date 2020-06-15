package com.bigroi.stock.controller.resource;

import com.bigroi.stock.bean.common.BidStatus;
import com.bigroi.stock.bean.db.Bid;
import com.bigroi.stock.bean.db.CompanyAddress;
import com.bigroi.stock.bean.db.StockUser;
import com.bigroi.stock.controller.BaseResourceController;
import com.bigroi.stock.json.GsonUtil;
import com.bigroi.stock.json.ResultBean;
import com.bigroi.stock.json.TableResponse;
import com.bigroi.stock.service.AddressService;
import com.bigroi.stock.service.BidService;
import com.bigroi.stock.service.LabelService;
import com.bigroi.stock.util.DateUtil;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public abstract class BidResourceController<B extends Bid, U> extends BaseResourceController {

    private static final String LABEL_PREFIX = "label.";

    protected final AddressService addressService;
    protected final LabelService labelService;

    public BidResourceController(AddressService addressService, LabelService labelService) {
        this.addressService = addressService;
        this.labelService = labelService;
    }

    protected abstract BidService<B> getBidService();

    protected abstract Function<B, U> getObjectForUIConstructor();

    protected abstract Class<B> getBidClass();

    protected abstract Class<U> getForUIClass();

    protected abstract String getPropertyFileName();

    protected abstract boolean isEmptyCategoryAllowed();

    protected ResultBean bidForm(long id) {
        var bid = getBidService().getById(id, getUserCompanyId());
        if (bid.getId() == -1) {
            var user = (StockUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            bid.setAddressId(user.getCompany().getCompanyAddress().getId());
            bid.setCompanyAddress(user.getCompany().getCompanyAddress());
        }
        return new ResultBean(1, bid, null);
    }

    protected ResultBean bidList() {
        var bids = getBidService().getByCompanyId(getUserCompanyId());
        var objectForUI = bids.stream()
                .map(b -> {
                    b.getProduct().setName(labelService.getLabel(b.getProduct().getName(), "name", getLanguage()));
                    return getObjectForUIConstructor().apply(b);
                })
                .collect(Collectors.toList());
        var clazz = getForUIClass();
        var table = new TableResponse<>(clazz, objectForUI);
        return new ResultBean(1, table, null);
    }

    protected ResultBean saveBid(String json) {
        B bid;
        try {
            bid = GsonUtil.getGson().fromJson(json, getBidClass());
        } catch (Exception e) {
            return new ResultBean(-1, LABEL_PREFIX + getPropertyFileName() + ".too_long_number");
        }
        if (bid.getId() < 0) {
            bid.setStatus(BidStatus.INACTIVE);
        } else {
            var oldLot = getBidService().getById(bid.getId(), getUserCompanyId());
            bid.setStatus(oldLot.getStatus());
        }
        return save(bid);

    }

    protected ResultBean testSaveBid(String json) {
        var bid = GsonUtil.getGson().fromJson(json, getBidClass());
        bid.setDescription(getSessionId());
        bid.setStatus(BidStatus.ACTIVE);
        bid.setExparationDate(DateUtil.shiftMonths(new Date(), 1));
        return save(bid);
    }

    protected ResultBean saveAndActivateBid(String json) {
        B bid;
        try {
            bid = GsonUtil.getGson().fromJson(json, getBidClass());
        } catch (Exception e) {
            return new ResultBean(-1, LABEL_PREFIX + getPropertyFileName() + ".too_long_number");
        }
        bid.setStatus(BidStatus.ACTIVE);
        return save(bid);
    }

    private ResultBean save(B bid) {
        var errors = activationCheck(bid, getUserCompanyId());
        if (!errors.isEmpty()) {
            var str = errors.toString().substring(1, errors.toString().length() - 1);
            return new ResultBean(-1, str);
        }

        if (bid.getCategoryId() == -1) {
            bid.setCategoryId(null);
        }
        getBidService().merge(bid, getUserCompanyId());
        bid.getProduct().setName(labelService.getLabel(bid.getProduct().getName(), "name", getLanguage()));
        return new ResultBean(1, getObjectForUIConstructor().apply(bid), "");
    }

    protected ResultBean startTradingBid(long id) {
        var errors = activationCheck(id, getUserCompanyId());
        if (!errors.isEmpty()) {
            var str = errors.toString().substring(1, errors.toString().length() - 1);
            return new ResultBean(-1, str);
        }
        getBidService().activate(id, getUserCompanyId());
        if (LocalDateTime.now().getHour() < 9 && LocalDateTime.now().getHour() > 21) {
            return new ResultBean(1, "label.bid.activated-morning");
        } else {
            return new ResultBean(1, "label.bid.activated-evening");
        }
    }

    protected ResultBean stopTradingBid(long id) {
        getBidService().deactivate(id, getUserCompanyId());
        return new ResultBean(1, "label.bid.deactivated");
    }

    protected ResultBean deleteBid(long id) {
        getBidService().delete(id, getUserCompanyId());
        return new ResultBean(1, null);
    }

    protected long getUserCompanyId() {
        var loggedInUser = SecurityContextHolder.getContext().getAuthentication();
        if (loggedInUser != null && loggedInUser.getPrincipal() instanceof StockUser) {
            StockUser user = (StockUser) loggedInUser.getPrincipal();
            return user.getCompanyId();
        } else {
            return 0;
        }
    }

    private List<String> activationCheck(long id, long companyId) {
        var bid = getBidService().getById(id, getUserCompanyId());
        return activationCheck(bid, companyId);
    }

    private List<String> activationCheck(B bid, long companyId) {
        var errors = new ArrayList<String>();

        var addressIds = addressService
                .getCompanyAddresses(companyId)
                .stream().map(CompanyAddress::getId)
                .collect(Collectors.toList());
        if (!addressIds.contains(bid.getAddressId())) {
            errors.add(LABEL_PREFIX + getPropertyFileName() + ".address_error");
        }
        if (bid.getPrice() < 0.1) {
            errors.add(LABEL_PREFIX + getPropertyFileName() + ".price_error");
        }
        if (bid.getProductId() < 0) {
            errors.add(LABEL_PREFIX + getPropertyFileName() + ".product_error");
        }
        if (bid.getMinVolume() < 1) {
            errors.add(LABEL_PREFIX + getPropertyFileName() + ".minVolume_error");
        }
        if (bid.getMaxVolume() <= bid.getMinVolume()) {
            errors.add(LABEL_PREFIX + getPropertyFileName() + ".maxVolume_error");
        }
        if (bid.getCategoryId() == -1 && !isEmptyCategoryAllowed()) {
            errors.add(LABEL_PREFIX + getPropertyFileName() + ".category_error");
        }

        var calendar = Calendar.getInstance();
        calendar.set(Calendar.MILLISECOND, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        if (bid.getExparationDate().getTime() < calendar.getTimeInMillis()) {
            errors.add(LABEL_PREFIX + getPropertyFileName() + ".expDate_error");
        }
        return errors;
    }

}
