package Torobts.ELO;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONObject;

import okhttp3.*;

public class TBAConnector {
	OkHttpClient client = new OkHttpClient();
	
	public String run(String url) throws IOException {
		Request request = new Request.Builder() 
				.url(url)
				.addHeader("X-TBA-Auth-Key", "LvUE8eCp0y16S55LeSxm44uNUQqgsqdgIHkEraPD3UySNx92S4e9XLR2cm07l2FQ")
				.build();
			
		Response response = client.newCall(request).execute();
		String jsonraw = response.body().string();
		System.out.println(jsonraw);
		//jsonraw = jsonraw.substring(1, jsonraw.length() - 1);
		//jsonraw = jsonraw.trim();
		JSONArray json = new JSONArray(jsonraw);
		JSONObject object;
		
		//JSONArray Cities = json.getJSONArray("city");
		/*for(int i = 0; i < json.length(); i++) {
			object = (JSONObject) json.get(i);
			if(object.get("name").toString().contains("Regional")) {
				//System.out.println(object.get("city"));
			}		*/	
		//}	
		return response.toString();
	}
	
	public TBAConnector() {			
			
	}
}
