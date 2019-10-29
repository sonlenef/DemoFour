package me.lesonnnn.demofour.screen.info;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import com.bumptech.glide.Glide;
import me.lesonnnn.demofour.R;
import me.lesonnnn.demofour.model.Image;

public class ScreenSlidePageFragment extends Fragment {

    private Image mImage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_screen_slide_page, container, false);

        ImageView imageView = view.findViewById(R.id.image);
        TextView textView = view.findViewById(R.id.description);

        Glide.with(this).load(mImage.getLink()).centerCrop().into(imageView);
        textView.setText(mImage.getDescription());

        return view;
    }

    public void setImage(Image image) {
        mImage = image;
    }
}
