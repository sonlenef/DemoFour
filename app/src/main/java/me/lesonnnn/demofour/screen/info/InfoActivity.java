package me.lesonnnn.demofour.screen.info;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import me.lesonnnn.demofour.R;
import me.lesonnnn.demofour.model.Image;
import me.lesonnnn.demofour.model.Item;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class InfoActivity extends AppCompatActivity {

    private ProgressDialog mDialog;
    private Item mItem;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        Intent intent = getIntent();
        if (intent != null) {
            String urlString = intent.getStringExtra("urlString");
            initData(urlString);
        }
    }

    private void initView() {
        TextView title = findViewById(R.id.txtTitle);
        title.setText(mItem.getName());

        ViewPager pager = findViewById(R.id.pager);
        PagerAdapter pagerAdapter =
                new ScreenSlidePagerAdapter(getSupportFragmentManager(), mItem.getImages());
        pager.setAdapter(pagerAdapter);
    }

    @SuppressLint("StaticFieldLeak")
    private void initData(String urlString) {
        try {
            URL url = new URL(urlString);
            new AsyncTask<URL, String, String>() {
                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                    mDialog = new ProgressDialog(InfoActivity.this);
                    mDialog.setMessage("Please Wait....");
                    mDialog.setCancelable(false);
                    mDialog.show();
                }

                @Override
                protected String doInBackground(URL... urls) {
                    try {
                        HttpURLConnection connection = (HttpURLConnection) urls[0].openConnection();
                        InputStream is = connection.getInputStream();
                        BufferedReader br = new BufferedReader(new InputStreamReader(is));
                        StringBuilder sb = new StringBuilder();
                        String line;
                        while ((line = br.readLine()) != null) {
                            sb.append(line);
                        }
                        line = sb.toString();
                        System.out.println(line);
                        connection.disconnect();
                        is.close();
                        sb.delete(0, sb.length());
                        return line;
                    } catch (Exception e) {
                        return null;
                    }
                }

                @Override
                protected void onProgressUpdate(String... values) {
                    super.onProgressUpdate(values);
                }

                @Override
                protected void onPostExecute(String s) {
                    super.onPostExecute(s);
                    mDialog.dismiss();
                    if (s != null) {
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            mItem = parseJsonToObjectItem(jsonObject);
                            initView();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(InfoActivity.this, "Không có data!!!", Toast.LENGTH_SHORT)
                                .show();
                        finish();
                    }
                }
            }.execute(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            Toast.makeText(this, "URL không hợp lệ!!!", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private Item parseJsonToObjectItem(JSONObject jsonObjectItem) {
        Item item = new Item();
        try {
            item = new Item.ItemBuild().setName(jsonObjectItem.getString(Item.ItemEntry.NAME))
                    .setImages(parseJsonToArrayImage(
                            jsonObjectItem.getJSONArray(Item.ItemEntry.IMAGES)))
                    .build();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return item;
    }

    private List<Image> parseJsonToArrayImage(JSONArray jsonArrayImage) {
        List<Image> images = new ArrayList<>();
        for (int n = 0; n < jsonArrayImage.length(); n++) {
            try {
                JSONObject object = jsonArrayImage.getJSONObject(n);
                images.add(parseJsonToObjectImage(object));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return images;
    }

    private Image parseJsonToObjectImage(JSONObject jsonObjectImage) {
        Image image = new Image();
        try {
            image = new Image.ImageBuild().setLink(jsonObjectImage.getString(Image.ItemEntry.LINK))
                    .setDescription(jsonObjectImage.getString(Image.ItemEntry.DESCRIPTION))
                    .build();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return image;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        private List<Image> mImages;

        ScreenSlidePagerAdapter(FragmentManager fm, List<Image> images) {
            super(fm);
            mImages = images;
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            ScreenSlidePageFragment slidePageFragment = new ScreenSlidePageFragment();
            slidePageFragment.setImage(mImages.get(position));
            return slidePageFragment;
        }

        @Override
        public int getCount() {
            return mImages.size();
        }
    }
}
