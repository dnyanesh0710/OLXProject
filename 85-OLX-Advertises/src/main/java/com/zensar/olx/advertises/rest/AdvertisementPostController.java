package com.zensar.olx.advertises.rest;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.zensar.olx.advertises.bean.AdvertisementPost;
import com.zensar.olx.advertises.bean.AdvertisementStatus;
import com.zensar.olx.advertises.bean.Category;
import com.zensar.olx.advertises.bean.NewAdvertisementPostRequest;
import com.zensar.olx.advertises.bean.NewAdvertisementPostResponse;
import com.zensar.olx.advertises.bean.OLXUser;
import com.zensar.olx.advertises.comparators.AdvertisementIdComparator;
import com.zensar.olx.advertises.comparators.CategoryComparator;
import com.zensar.olx.advertises.comparators.CreatedDateComparator;
import com.zensar.olx.advertises.comparators.ModifiedDateComparator;
import com.zensar.olx.advertises.service.AdvertisementPostService;

@RestController
public class AdvertisementPostController {

	@Autowired
	AdvertisementPostService service;

	@PostMapping("/advertise/{un}")
	public NewAdvertisementPostResponse add(@RequestBody NewAdvertisementPostRequest request,
			@PathVariable(name = "un") String userName) {

		AdvertisementPost post = new AdvertisementPost();
		post.setTitle(request.getTitle());
		post.setPrice(request.getPrice());
		post.setDescription(request.getDescription());

		int categoryId = request.getCategoryId();

		RestTemplate restTemplate = new RestTemplate();
		Category category = new Category();
		String url = "http://localhost:9052/advertise/getCategory/" + categoryId;
		category = restTemplate.getForObject(url, Category.class);
		post.setCategory(category);

		url = "http://localhost:9051/user/find/" + userName;
		OLXUser olxUser = restTemplate.getForObject(url, OLXUser.class);
		post.setOlxUser(olxUser);

		AdvertisementStatus advertisementStatus = new AdvertisementStatus(1, "OPEN");
		post.setAdvertisementStatus(advertisementStatus);

		AdvertisementPost advertisementPost = this.service.addadvertisementPost(post);

		NewAdvertisementPostResponse response = new NewAdvertisementPostResponse();
		response.setId(advertisementPost.getId());
		response.setTitle(advertisementPost.getTitle());
		response.setPrice(advertisementPost.getPrice());
		response.setCategory(advertisementPost.getCategory().getName());
		response.setDescription(advertisementPost.getDescription());
		response.setUserName(advertisementPost.getOlxUser().getUserName());
		response.setCreatedDate(advertisementPost.getCreatedDate());
		response.setModifiedDate(advertisementPost.getModifiedDate());
		response.setStatus(advertisementPost.getAdvertisementStatus().getStatus());

		return response;
	}

	@PutMapping("/advertise/{aid}/{userName}")
	public NewAdvertisementPostResponse f2(@RequestBody NewAdvertisementPostRequest request,
			@PathVariable(name = "aid") int id, @PathVariable(name = "userName") String userName) {
		AdvertisementPost post = this.service.getAdvertisementPostById(id);
		post.setTitle(request.getTitle());
		post.setDescription(request.getDescription());
		post.setPrice(request.getPrice());

		RestTemplate restTemplate = new RestTemplate();

		int categoryId = request.getCategoryId();
		Category category = new Category();
		String url = "http://localhost:9052/advertise/getCategory/" + categoryId;
		category = restTemplate.getForObject(url, Category.class);
		post.setCategory(category);

		url = "http://localhost:9051/user/find/" + userName;
		OLXUser olxUser = restTemplate.getForObject(url, OLXUser.class);
		post.setOlxUser(olxUser);

		url = "http://localhost:9052/advertise/status/" + request.getStatusId();
		AdvertisementStatus advertisementStatus = restTemplate.getForObject(url, AdvertisementStatus.class);
		post.setAdvertisementStatus(advertisementStatus);

		AdvertisementPost advertisementPost = this.service.updateAdvertisement(post);

		NewAdvertisementPostResponse postResponse = new NewAdvertisementPostResponse();
		postResponse.setId(advertisementPost.getId());
		postResponse.setTitle(advertisementPost.getTitle());
		postResponse.setDescription(advertisementPost.getDescription());
		postResponse.setPrice(advertisementPost.getPrice());
		postResponse.setUserName(advertisementPost.getOlxUser().getUserName());
		postResponse.setCategory(advertisementPost.getCategory().getName());
		postResponse.setCreatedDate(advertisementPost.getCreatedDate());
		postResponse.setModifiedDate(advertisementPost.getModifiedDate());
		postResponse.setStatus(advertisementPost.getAdvertisementStatus().getStatus());

		return postResponse;
	}

	// 11th URL
	@GetMapping("/user/advertise/{userName}")
	public List<NewAdvertisementPostResponse> f3(@PathVariable(name = "userName") String userName) {

		List<AdvertisementPost> allAdvertisementPosts = this.service.getAllAdvertisments();
		List<NewAdvertisementPostResponse> responseList = new LinkedList<>();

		for (AdvertisementPost advertisementPost : allAdvertisementPosts) {
			NewAdvertisementPostResponse response = new NewAdvertisementPostResponse();
			RestTemplate restTemplate = new RestTemplate();

			Category category = advertisementPost.getCategory();
			String url = "http://localhost:9052/advertise/getCategory/" + category.getId();
			category = restTemplate.getForObject(url, Category.class);
			response.setCategory(category.getName());
			response.setDescription(category.getDescription());
			response.setId(advertisementPost.getId());
			response.setTitle(advertisementPost.getTitle());
			response.setPrice(advertisementPost.getPrice());
			response.setCreatedDate(advertisementPost.getCreatedDate());
			response.setModifiedDate(advertisementPost.getModifiedDate());

			OLXUser olxUser = advertisementPost.getOlxUser();
			url = "http://localhost:9051/user/" + olxUser.getOlxUserId();
			olxUser = restTemplate.getForObject(url, OLXUser.class);
			response.setUserName(olxUser.getUserName());

			AdvertisementStatus advertisementStatus = advertisementPost.getAdvertisementStatus();
			// System.out.println(advertisementStatus.getId());
			url = "http://localhost:9052/advertise/status/" + advertisementStatus.getId();
			advertisementStatus = restTemplate.getForObject(url, AdvertisementStatus.class);
			// System.out.println(advertisementStatus);
			response.setStatus(advertisementStatus.getStatus());

			if (response.getUserName().equals(userName))
				responseList.add(response);
		}

		return responseList;
	}

	/*
	 * @GetMapping("/user/advertise/{un}/{adId}") public
	 * NewAdvertisementPostResponse getSingleAdvertisement(@PathVariable(name="un")
	 * String userName,
	 * 
	 * @PathVariable(name="adId") int adId) {
	 * 
	 * AdvertisementPost post=this.service.getAdvertisementPostById(adId);
	 * 
	 * NewAdvertisementPostResponse response=new NewAdvertisementPostResponse();
	 * RestTemplate restTemplate=new RestTemplate();
	 * 
	 * Category category=post.getCategory(); String
	 * url="http://localhost:9052/advertise/getCategory/"+category.getId();
	 * category=restTemplate.getForObject(url, Category.class);
	 * response.setCategory(category.getName());
	 * response.setDescription(category.getDescription());
	 * response.setId(post.getId()); response.setTitle(post.getTitle());
	 * response.setPrice(post.getPrice());
	 * response.setCreatedDate(post.getCreatedDate());
	 * response.setModifiedDate(post.getModifiedDate());
	 * 
	 * OLXUser olxUser=post.getOlxUser();
	 * url="http://localhost:9051/user/"+olxUser.getOlxUserId();
	 * olxUser=restTemplate.getForObject(url, OLXUser.class);
	 * response.setUserName(olxUser.getUserName());
	 * 
	 * AdvertisementStatus advertisementStatus=post.getAdvertisementStatus();
	 * //System.out.println(advertisementStatus.getId());
	 * url="http://localhost:9052/advertise/status/"+advertisementStatus.getId();
	 * advertisementStatus=restTemplate.getForObject(url,
	 * AdvertisementStatus.class); //System.out.println(advertisementStatus);
	 * response.setStatus(advertisementStatus.getStatus());
	 * 
	 * return response; }
	 */

	// 12th URL
	@DeleteMapping("/user/advertise/{advertiseId}")
	public boolean deleteAdvertisement(@PathVariable(name = "advertiseId") int id) {
		return this.service.deleteAdvertisementPost(new AdvertisementPost(id));
	}

	// 13th URL
	@GetMapping("/advertise/search/{filter}")
	public List<NewAdvertisementPostResponse> filterAdvertisements(@PathVariable(name = "filter") String filter) {
		List<AdvertisementPost> allAdvertisementPosts = this.service.getAllAdvertisments();
		List<NewAdvertisementPostResponse> responseList = new LinkedList<>();

		for (AdvertisementPost advertisementPost : allAdvertisementPosts) {
			NewAdvertisementPostResponse response = new NewAdvertisementPostResponse();
			RestTemplate restTemplate = new RestTemplate();

			Category category = advertisementPost.getCategory();
			String url = "http://localhost:9052/advertise/getCategory/" + category.getId();
			category = restTemplate.getForObject(url, Category.class);
			response.setCategory(category.getName());
			response.setDescription(category.getDescription());
			response.setId(advertisementPost.getId());
			response.setTitle(advertisementPost.getTitle());
			response.setPrice(advertisementPost.getPrice());
			response.setCreatedDate(advertisementPost.getCreatedDate());
			response.setModifiedDate(advertisementPost.getModifiedDate());

			OLXUser olxUser = advertisementPost.getOlxUser();
			url = "http://localhost:9051/user/" + olxUser.getOlxUserId();
			olxUser = restTemplate.getForObject(url, OLXUser.class);
			response.setUserName(olxUser.getUserName());

			AdvertisementStatus advertisementStatus = advertisementPost.getAdvertisementStatus();
			// System.out.println(advertisementStatus.getId());
			url = "http://localhost:9052/advertise/status/" + advertisementStatus.getId();
			advertisementStatus = restTemplate.getForObject(url, AdvertisementStatus.class);
			response.setStatus(advertisementStatus.getStatus());

			responseList.add(response);
		}

		switch (filter) {
		case "category":
			responseList.sort(new CategoryComparator());
			break;
		case "createdDate":
			responseList.sort(new CreatedDateComparator());
			break;
		case "modifiedDate":
			responseList.sort(new ModifiedDateComparator());
			break;
		case "startIndex":
			responseList.sort(new AdvertisementIdComparator());
			break;

		default:
			break;
		}
		return responseList;
	}

}
