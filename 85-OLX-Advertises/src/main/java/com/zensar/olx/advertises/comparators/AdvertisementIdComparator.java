package com.zensar.olx.advertises.comparators;

import java.util.Comparator;

import com.zensar.olx.advertises.bean.NewAdvertisementPostResponse;

public class AdvertisementIdComparator implements Comparator<NewAdvertisementPostResponse> {

	@Override
	public int compare(NewAdvertisementPostResponse o1, NewAdvertisementPostResponse o2) {
		if(o1.getId()<o2.getId())
			return -1;
		else if(o1.getId()>o2.getId())
			return 1;
		return 0;
	}

}
