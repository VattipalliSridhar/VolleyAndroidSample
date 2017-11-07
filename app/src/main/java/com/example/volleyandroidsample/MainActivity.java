package com.example.volleyandroidsample;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    String url ="https://sridhar.000webhostapp.com/abc.json";
    TextView mTextView1,mTextView2;
    ImageView url_img;
    String img_url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mTextView1 = (TextView) findViewById(R.id.text1);
        mTextView2 = (TextView) findViewById(R.id.text2);
        url_img=(ImageView)findViewById(R.id.url_img);

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonArrayRequest jsonArrayRequest = getJsonArrayRequest();
        queue.add(jsonArrayRequest);

        ImageRequest imageRequest = getImageRequest();
        queue.add(imageRequest);
        JsonObjectRequest jsonObjectRequest = getJsonObjectRequest();
        //queue.add(jsonObjectRequest);
    }

    private JsonArrayRequest getJsonArrayRequest()
    {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                url,
                new Response.Listener<JSONArray>()
                {
                    @Override
                    public void onResponse(JSONArray response)
                    {
                        try {
                        for (int i = 0; i < response.length(); i++) {


                                JSONObject person = (JSONObject) response
                                        .get(i);
                                String name = person.getString("appname");
                                String email = person.getString("appurl");
                            img_url=person.getString("appurl");

                                Log.e("msg",""+name);
                                mTextView1.setText(""+name);

                                mTextView2.setText(""+email);


                        }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        handleVolleyError(error);
                    }
                });
        return jsonArrayRequest;
    }

    private JsonObjectRequest getJsonObjectRequest() {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response)
                    {

                        try {
                            String appnames=response.getString("appname");
                            Log.e("msg",""+appnames);
                            mTextView1.setText(""+appnames);
                            String appurls=response.getString("appurl");
                            mTextView2.setText(""+appurls);
                            //String appimgg=response.getString("appimg");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        Toast.makeText(MainActivity.this,response.toString(),Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                       //handleVolleyError(error);
                    }
                });

        return jsonObjectRequest;
    }
    private ImageRequest getImageRequest()
    {
        String image_url = "https://bcdn.newshunt.com/cmd/resize/400x400_60/fetchdata13/images/ae/95/4e/ae954ea014f6d14ea80d2fd287717c79.jpg";

        ImageRequest imageRequest = new ImageRequest(
                image_url,
                new Response.Listener<Bitmap>()
                {
                    @Override
                    public void onResponse(Bitmap response)
                    {
                        url_img.setImageBitmap(response);
                    }
                },
                100, 100,
                ImageView.ScaleType.CENTER_CROP,
                Bitmap.Config.ARGB_8888,
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        handleVolleyError(error);
                    }
                });
        return imageRequest;
    }

    private void handleVolleyError(VolleyError error)
    {
        if (error instanceof AuthFailureError || error instanceof TimeoutError)
        {
            Toast.makeText(MainActivity.this,"AuthFailureError/TimeoutError",Toast.LENGTH_LONG).show();
        }
        else if (error instanceof NoConnectionError)
        {
            Toast.makeText(MainActivity.this,"NoConnectionError",Toast.LENGTH_LONG).show();
        }
        else if (error instanceof NetworkError)
        {
            Toast.makeText(MainActivity.this,"NetworkError",Toast.LENGTH_LONG).show();
        }
        else if (error instanceof ServerError)
        {
            Toast.makeText(MainActivity.this,"ServerError",Toast.LENGTH_LONG).show();
        }
        else if (error instanceof ParseError)
        {
            Toast.makeText(MainActivity.this,"ParseError",Toast.LENGTH_LONG).show();
        }
    }

}
