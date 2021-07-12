/*
 * Copyright 2021-2021 KOR Financial - All Rights Reserved.
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 * Proprietary and confidential.
 */

package com.example.pactconsumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ZooPrinter {

	private static final Logger logger = LoggerFactory.getLogger(ZooPrinter.class);

	private ZooApiClient zooApiClient;

	@Scheduled(fixedDelay=5000)
	public void print() {
		logger.info("Animals retrieved: " + zooApiClient.listAnimals());
	}

	@Autowired
	public void setZooApiClient(ZooApiClient zooApiClient) {
		this.zooApiClient = zooApiClient;
	}
}
