package com.stock.trading;

import com.stock.client.email.EmailClient;
import com.stock.dao.*;
import com.stock.entity.Language;
import com.stock.entity.PartnerChoice;
import com.stock.entity.business.*;
import com.stock.trading.entity.LotTradeRecord;
import com.stock.trading.entity.TenderTradeRecord;
import org.junit.Test;
import org.mockito.Mockito;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.Stream;

public class TradeSessionTest {

    private static final Random random = new Random();

//    @Test
    public void tradeProductPositive() {
        //given
        var seller = new UserRecord();
        seller.setUserName("test-seller@email.com");
        seller.setLanguage(Language.EN);
        seller.setCompanyId(UUID.randomUUID());
        var buyer = new UserRecord();
        buyer.setUserName("test-buyer@email.com");
        buyer.setLanguage(Language.EN);
        buyer.setCompanyId(UUID.randomUUID());

        var product = new ProductRecord();
        product.setId(UUID.randomUUID());
        product.setName("test-product");

        var category = new ProductCategoryRecord();
        category.setCategoryName("test-category");
        category.setId(UUID.randomUUID());
        category.setProductId(product.getId());

        var tender = createRandomObject(TenderTradeRecord.class);
        tender.setCompanyId(buyer.getCompanyId());
        tender.setPrice(100);
        tender.setMaxVolume(100);
        tender.setProductId(product.getId());
        tender.setCategoryId(category.getId());
        tender.setDistance(30000);

        var lot = createRandomObject(LotTradeRecord.class);
        lot.setCompanyId(seller.getCompanyId());
        lot.setPrice(50);
        lot.setMaxVolume(50);
        lot.setCategoryId(category.getId());
        lot.setDistance(30000);

        var dealDao = Mockito.mock(DealDao.class);

        var lotDao = Mockito.mock(LotDao.class);
        Mockito.when(lotDao.getByProductId(product.getId())).thenReturn(Collections.singletonList(lot));

        var tenderDao = Mockito.mock(TenderDao.class);
        Mockito.when(tenderDao.getByProductId(product.getId())).thenReturn(Collections.singletonList(tender));

        var labelDao = Mockito.mock(LabelDao.class);
        Mockito.when(labelDao.getByName("label." + product.getName() + ".name"))
                .thenReturn(Map.of(Language.EN, "test-product-EN"));

        var bidBlackListDao = Mockito.mock(BidBlackListDao.class);
        Mockito.when(bidBlackListDao.getAll()).thenReturn(Collections.emptyList());

        var userDao = Mockito.mock(UserDao.class);
        Mockito.when(userDao.getAll()).thenReturn(Arrays.asList(seller, buyer));

        var emailClient = Mockito.mock(EmailClient.class);

        //
        var session = new TradeSession(dealDao, lotDao, tenderDao, labelDao, bidBlackListDao, userDao, emailClient);

        //when
        session.tradeProduct(product);

        //then
        var deal = createDealForCheck(
                Math.min(lot.getMaxVolume(), tender.getMaxVolume()),
                lot,
                tender,
                (lot.getPrice() + tender.getPrice()) / 2,
                lot.getCategoryId()
        );
        Mockito.verify(dealDao, Mockito.times(1)).create(Collections.singletonList(deal));

        var lotForUpdate = createLotForCheck(lot.getId(), deal.getVolume());
        Mockito.verify(lotDao, Mockito.times(1))
                .updateStatusAndAlert(Collections.singletonList(lotForUpdate));

        var tenderForUpdate = createTenderForCheck(tender.getId(), deal.getVolume());
        Mockito.verify(tenderDao, Mockito.times(1))
                .updateStatusAndAlert(Collections.singletonList(tenderForUpdate));
    }

    private LotTradeRecord createLotForCheck(UUID id, int volume) {
        var lot = new LotTradeRecord();
        lot.setId(id);
        lot.setMaxVolume(volume);
        lot.setAlert(true);
        return lot;
    }

    private TenderTradeRecord createTenderForCheck(UUID id, int volume) {
        var tender = new TenderTradeRecord();
        tender.setId(id);
        tender.setMaxVolume(volume);
        tender.setAlert(true);
        return tender;
    }

    private DealRecord createDealForCheck(
            int volume,
            LotRecord lot,
            TenderRecord tender,
            double price,
            UUID categoryId
    ) {
        var deal = new DealRecord();
        deal.setVolume(volume);
        deal.setSellerCompanyId(lot.getCompanyId());
        deal.setBuyerCompanyId(tender.getCompanyId());
        deal.setPrice(price);
        deal.setCategoryId(categoryId);

        return deal;
    }

    private <T> T createRandomObject(Class<T> clazz) {
        try {
            var obj = clazz.getConstructor().newInstance();
            Stream.of(LotTradeRecord.class.getMethods())
                    .filter(m -> m.getName().startsWith("set"))
                    .forEach(m -> {
                        try {
                            if (m.getParameterTypes()[0] == String.class) {
                                m.invoke(obj, "random string");
                            } else if (m.getParameterTypes()[0] == int.class) {
                                m.invoke(obj, random.nextInt());
                            } else if (m.getParameterTypes()[0] == double.class) {
                                m.invoke(obj, random.nextDouble());
                            } else if (m.getParameterTypes()[0] == UUID.class) {
                                m.invoke(obj, UUID.randomUUID());
                            } else if (m.getParameterTypes()[0] == Date.class) {
                                m.invoke(obj, new Date(random.nextLong()));
                            } else if (m.getParameterTypes()[0] == boolean.class) {
                                m.invoke(obj, random.nextBoolean());
                            } else if (m.getParameterTypes()[0] == PartnerChoice.class) {
                                m.invoke(obj, PartnerChoice.values()[random.nextInt(PartnerChoice.values().length)]);
                            } else {
                                var subClass = m.getParameterTypes()[0];
                                var subObj = createRandomObject(subClass);
                                m.invoke(obj, subObj);
                            }
                        } catch (IllegalAccessException | InvocationTargetException e) {
                            throw new RuntimeException(e);
                        }
                    });
            return obj;
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }
}