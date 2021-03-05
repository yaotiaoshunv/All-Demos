package com.lizw.customviewdemo.activity;

        import androidx.appcompat.app.AppCompatActivity;

        import android.os.Bundle;
        import android.view.View;
        import android.widget.TextView;

        import com.lizw.customviewdemo.R;
        import com.lizw.lettersidebar.LetterSideBar;

public class LetterActivity extends AppCompatActivity {
    private TextView mTvLetter;
    private LetterSideBar mLetterSideBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_letter);

        mTvLetter = findViewById(R.id.tv_letter);
        mLetterSideBar = findViewById(R.id.letter_side_bar);
        mLetterSideBar.setOnTouchListener(new LetterSideBar.OnTouchLetterSideBarListener() {
            @Override
            public void touch(char letter, boolean isTouching) {
                if (isTouching) {
                    mTvLetter.setVisibility(View.VISIBLE);
                    mTvLetter.setText(letter + "");
                    mTvLetter.removeCallbacks(mRunnable);
                } else {
                    mTvLetter.postDelayed(mRunnable, 2000L);
                }
            }
        });
    }

    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            mTvLetter.setVisibility(View.GONE);
        }
    };
}