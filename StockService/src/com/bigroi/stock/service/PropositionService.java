package com.bigroi.stock.service;

import java.util.List;
import com.bigroi.stock.bean.db.Proposition;
import org.springframework.stereotype.Service;

@Service
public interface PropositionService {
	
	List<Proposition> getListProposition() throws ServiceException;
	
	void delete(long id, long companyId) throws ServiceException;
	
}
