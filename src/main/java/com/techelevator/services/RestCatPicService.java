package com.techelevator.services;

import com.techelevator.util.BasicLogger;
import org.springframework.stereotype.Component;

import com.techelevator.model.CatPic;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

@Component
public class RestCatPicService implements CatPicService {

	public static String API_BASE_URL = "https://cat-data.netlify.app/api/pictures/random";
	private RestTemplate restTemplate = new RestTemplate();
	@Override
	public CatPic getPic() {
		CatPic catPic = null;
		try {
			catPic = restTemplate.getForObject(API_BASE_URL, CatPic.class);
		}  catch (RestClientResponseException e) {
			BasicLogger.log(e.getRawStatusCode() + " : " + e.getStatusText());
		} catch (ResourceAccessException e) {
			BasicLogger.log(e.getMessage());
		}
		return catPic;
	}

}	
