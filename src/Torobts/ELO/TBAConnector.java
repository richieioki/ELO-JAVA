package Torobts.ELO;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class TBAConnector {
	OkHttpClient client = new OkHttpClient();
	
	public String run(String url) throws IOException {
		Request request = new Request.Builder() 
				.url("https://www.thebluealliance.com/api/v3")
				.build();
			
		Response response = client.newCall(request).execute();
		System.out.println(response.toString());
		return response.toString();
	}
	
	public TBAConnector() {	
		
		
	
	}
}
