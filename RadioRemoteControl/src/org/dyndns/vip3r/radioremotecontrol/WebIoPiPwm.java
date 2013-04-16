package org.dyndns.vip3r.radioremotecontrol;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

public class WebIoPiPwm {

	private String url;

	public WebIoPiPwm(String url) {
		this.url = url;
	}

	private void webIoPost(String completeUrl) {
		try {
			HttpClient client = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(completeUrl);
			HttpResponse response = client.execute(httpPost);
			HttpEntity entity = response.getEntity();
			// Log.d("CV", entitiy);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String webIoGet(String completeUrl) {
		try {
			HttpClient client = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet(completeUrl);
			HttpResponse response = client.execute(httpGet);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				InputStream stream = entity.getContent();
				String value = convertStreamToString(stream);
				return value;
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private static String convertStreamToString(InputStream is) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();

		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}

	public String pwmCount(String device) {
		String completeUrl = url + "/devices/" + device + "/pwm/count";
		return webIoGet(completeUrl);
	}

	public String pwmResolution(String device) {
		String completeUrl = url + "/devices/" + device + "/pwm/resolution";
		return webIoGet(completeUrl);
	}

	public String pwmMaximum(String device) {
		String completeUrl = url + "/devices/" + device + "/pwm/maximum";
		return webIoGet(completeUrl);
	}

	public void pwmWrite(String device, int channel, int value) {
		String completeUrl = url + "/devices/" + device + "/pwm/" + Integer.toString(channel)
				+ "/integer/" + Integer.toString(value);
		webIoPost(completeUrl);
	}

	public void pwmWriteFloat(String device, int channel, float value) {
		String completeUrl = url + "/devices/" + device + "/pwm/" + Integer.toString(channel)
				+ "/float/" + Float.toString(value);
		webIoPost(completeUrl);
	}

	public void pwmWriteFAngle(String device, int channel, float value) {
		String completeUrl = url + "/devices/" + device + "/pwm/" + Integer.toString(channel)
				+ "/angle/" + Float.toString(value);
		webIoPost(completeUrl);
	}

	public String pwmRead(String device, int channel) {
		String completeUrl = url + "/devices/" + device + "/pwm/" + Integer.toString(channel)
				+ "/integer";
		return webIoGet(completeUrl);
	}

	public String pwmReadFloat(String device, int channel) {
		String completeUrl = url + "/devices/" + device + "/pwm/" + Integer.toString(channel)
				+ "/float";
		return webIoGet(completeUrl);
	}

	public String pwmReadAngle(String device, int channel) {
		String completeUrl = url + "/devices/" + device + "/pwm/" + Integer.toString(channel)
				+ "/angle";
		return webIoGet(completeUrl);
	}

	public String pwmReadAll(String device) {
		String completeUrl = url + "/devices/" + device + "/pwm/*/integer";
		return webIoGet(completeUrl);
	}

	public String pwmReadAllFloat(String device) {
		String completeUrl = url + "/devices/" + device + "/pwm/*/float";
		return webIoGet(completeUrl);
	}

	public String pwmReadAllAngle(String device) {
		String completeUrl = url + "/devices/" + device + "/pwm/*/angle";
		return webIoGet(completeUrl);
	}

	public String pwmWildcard(String device) {
		String completeUrl = url + "/devices/" + device + "/pwm/*/";
		return webIoGet(completeUrl);
	}
}
