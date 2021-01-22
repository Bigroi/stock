package com.stock.mapper;

import com.stock.entity.business.CompanyRecord;
import com.stock.entity.business.DealRecord;
import com.stock.entity.ui.Deal;
import com.stock.entity.ui.DealDetails;

import java.util.UUID;

public interface DealMapper {

    Deal mapDeal(DealRecord record, UUID companyId, String productName, String categoryName);

    DealDetails mapDealDetails(
            DealRecord record,
            UUID companyId,
            String productName,
            String categoryName,
            CompanyRecord partner,
            double partnerMark
    );
}
