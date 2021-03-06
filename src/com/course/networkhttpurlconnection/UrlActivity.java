package com.course.networkhttpurlconnection;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class UrlActivity extends Activity {
	private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_url);
        mTextView = (TextView) findViewById(R.id.textView1);
        
        final Button loadButton = (Button) findViewById(R.id.button1);
        loadButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				new HttpGetTask().execute();
			}
		});
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.url, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private class HttpGetTask extends AsyncTask<Void, Void, String> {
    	private static final String TAG = "HttpGetTask";
    	private static final String USER_NAME = "aporter";
    	private static final String URL = "http://api.geonames.org/earthquakesJSON?north=44.1&south=-9.9&east=-22.4&west=55.2&username="
				+ USER_NAME;
		@Override
		protected String doInBackground(Void... params) {
			String data = "";
			HttpURLConnection httpUrlConnection = null;
			
			try {
				httpUrlConnection = (HttpURLConnection) new URL(URL).openConnection();
				InputStream in = new BufferedInputStream(
						httpUrlConnection.getInputStream());
				data = readStream(in);
			} catch (MalformedURLException exception) {
				Log.e(TAG, "MalformedURLException");
			} catch (IOException exception) {
				Log.e(TAG, "IOException");
			} finally {
				if(null != httpUrlConnection) {
					httpUrlConnection.disconnect();
				}
			}
			return data;
		}
		@Override
		protected void onCancelled() {
			// TODO Auto-generated method stub
			super.onCancelled();
		}
		@Override
		protected void onCancelled(String result) {
			// TODO Auto-generated method stub
			super.onCancelled(result);
		}
		@Override
		protected void onPostExecute(String result) {
			mTextView.setText(result);
		}
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}
		@Override
		protected void onProgressUpdate(Void... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);
		}
    	
    	private String readStream(InputStream in) {
    		BufferedReader reader = null;
    		StringBuffer data = new StringBuffer("");
    		try {
    			reader = new BufferedReader(new InputStreamReader(in));
    			String line = "";
    			while((line = reader.readLine()) != null) {
    				data.append(line);
    			}
    		} catch (IOException e) {
    			Log.e(TAG, "IOException");
    		} finally {
    			if(reader != null) {
    				try {
    					reader.close();
    				} catch (IOException e) {
    					e.printStackTrace();
    				}
    			}
    		}
    		return data.toString();
    	}
    }
}
