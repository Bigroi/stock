package com.bigroi.stock.messager.message;


import com.bigroi.stock.bean.InviteUser;
import com.bigroi.stock.messager.MessagerFactory;


public class InviteMessage extends BaseMessage<InviteUser>{

	protected InviteMessage(String fileName) throws MessageException {
		super(fileName);
	}

	@Override
	protected String getEmail() throws MessageException {
			return getDataObject().getInviteEmail();
	}
	
	protected String getText() throws MessageException {
		String url = MessagerFactory.getMailManager().getServerAdress();
		InviteUser user = getDataObject();
		url += "account/Join.spr?code="+user.getGeneratedKey();
		return super.getText()
				.replaceAll("@link", url);
	}
}
