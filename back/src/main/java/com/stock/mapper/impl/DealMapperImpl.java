package com.stock.mapper.impl;

import com.stock.entity.DealStatus;
import com.stock.entity.business.CompanyRecord;
import com.stock.entity.business.DealRecord;
import com.stock.entity.ui.Deal;
import com.stock.entity.ui.DealDetails;
import com.stock.mapper.DealMapper;

import java.util.UUID;

public class DealMapperImpl implements DealMapper {

    @Override
    public Deal mapDeal(DealRecord record, UUID companyId, String productName, String categoryName) {
        var status = record.getSellerCompanyId().equals(companyId)
                ? DealStatus.calculateStatus(record.getSellerChoice(), record.getBuyerChoice())
                : DealStatus.calculateStatus(record.getBuyerChoice(), record.getSellerChoice());

        return new Deal(record.getId(), productName, categoryName, record.getCreationDate(), status);
    }

    @Override
    public DealDetails mapDealDetails(
            DealRecord record,
            UUID companyId,
            String productName,
            String categoryName,
            CompanyRecord partner,
            double partnerMark
    ) {
        if (record.getSellerCompanyId().equals(companyId)) {
            return new DealDetails(
                    record.getId(),
                    productName,
                    categoryName,
                    record.getCreationDate(),
                    DealStatus.calculateStatus(record.getSellerChoice(), record.getBuyerChoice()),
                    record.getPrice(),
                    record.getVolume(),
                    partner.getName(),
                    partnerMark,
                    record.getBuyerDescription(),
                    record.getPackaging(),
                    record.getProcessing(),
                    partner.getPhone(),
                    partner.getRegNumber(),
                    null,
                    record.getBuyerAddress().getAddress(),
                    record.getBuyerAddress().getLatitude(),
                    record.getBuyerAddress().getLongitude(),
                    record.getSellerAddress().getLatitude(),
                    record.getSellerAddress().getLongitude()
            );
        } else {
            return new DealDetails(
                    record.getId(),
                    productName,
                    categoryName,
                    record.getCreationDate(),
                    DealStatus.calculateStatus(record.getBuyerChoice(), record.getSellerChoice()),
                    record.getPrice(),
                    record.getVolume(),
                    partner.getName(),
                    partnerMark,
                    record.getSellerDescription(),
                    null,
                    null,
                    partner.getPhone(),
                    partner.getRegNumber(),
                    record.getPhoto(),
                    record.getSellerAddress().getAddress(),
                    record.getSellerAddress().getLatitude(),
                    record.getSellerAddress().getLongitude(),
                    record.getBuyerAddress().getLatitude(),
                    record.getBuyerAddress().getLongitude()
            );
        }
    }
}
