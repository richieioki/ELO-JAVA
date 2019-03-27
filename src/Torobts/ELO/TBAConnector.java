package Torobts.ELO;

import java.io.IOException;

import org.json.JSONArray;
import okhttp3.*;

public class TBAConnector {
	private OkHttpClient client = new OkHttpClient();
	
	public JSONArray run(String url) throws IOException {
		Request request = new Request.Builder() 
				.url(url)
				.addHeader("X-TBA-Auth-Key", "LvUE8eCp0y16S55LeSxm44uNUQqgsqdgIHkEraPD3UySNx92S4e9XLR2cm07l2FQ")
				.build();
			
		Response response = client.newCall(request).execute();
		if(response.code() == 200) {
			String jsonraw = response.body().string();
			JSONArray json = new JSONArray(jsonraw);			
			return json;
		} else {
			System.err.print("NETWORK ERROR " + response.code());
			return new JSONArray();
		}		
	}
	
	public TBAConnector() {			
			
	}
}
