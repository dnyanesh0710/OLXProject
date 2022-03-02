package com.zensar.olx.advertises.comparators;

import java.util.Comparator;

import com.zensar.olx.advertises.bean.NewAdvertisementPostResponse;

public class CategoryComparator implements Comparator<NewAdvertisementPostResponse> {

	@Override
	public int compare(NewAdvertisementPostResponse o1, NewAdvertisementPostResponse o2) {
		return o1.getCategory().compareTo(o2.getCategory());
	}

}
