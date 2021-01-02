package com.stock.service;

import com.stock.entity.DealStatus;
import com.stock.entity.PartnerChoice;
import com.stock.entity.ui.Deal;
import com.stock.entity.ui.DealDetails;
import com.stock.entity.ui.UserComment;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DealService {

    List<Deal> getByCompanyId(UUID companyId);

    Optional<DealDetails> getById(UUID id, UUID companyId);

    Optional<DealStatus> setState(UUID id, UUID companyId, PartnerChoice choice);

    boolean createComment(UUID dealId, UUID companyId, UserComment comment);

    boolean updateComment(UUID dealId, UUID companyId, UserComment comment);

    Optional<UserComment> getComment(UUID dealId, UUID companyId);
}
