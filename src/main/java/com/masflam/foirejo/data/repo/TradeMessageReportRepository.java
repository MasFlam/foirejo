package com.masflam.foirejo.data.repo;

import javax.inject.Singleton;

import com.masflam.foirejo.data.entity.TradeMessage;
import com.masflam.foirejo.data.entity.TradeMessageReport;
import com.masflam.foirejo.data.entity.User;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

@Singleton
public class TradeMessageReportRepository implements PanacheRepositoryBase<TradeMessageReport, TradeMessageReport.Id> {
	public TradeMessageReport findById(User reporter, TradeMessage message) {
		var id = new TradeMessageReport.Id();
		id.setReporter(reporter);
		id.setMessage(message);
		return findById(id);
	}
}
