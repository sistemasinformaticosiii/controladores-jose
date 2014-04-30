package com.practica.si3.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.practica.si3.dao.SubscriptionDao;
import com.practica.si3.domain.Subscription;


public class SubscriptionServiceImpl implements SubscriptionService {

	@Autowired
	SubscriptionDao subscriptiondao;

	@Override
	public void insertData(Subscription subscription) {
		subscriptiondao.insertData(subscription);
	}

	@Override
	public List<Subscription> getSubscriptionList() {
		return subscriptiondao.getSubscriptionList();
	}

	@Override
	public void deleteData(String id) {
		subscriptiondao.deleteData(id);
		
	}

	@Override
	public Subscription getSubscription(String id) {
		return subscriptiondao.getSubscription(id);
	}

	@Override
	public void updateData(Subscription subscription) {
		subscriptiondao.updateData(subscription);
		
	}

}
