package com.study.yaodh.androidstudy.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.AnimationDrawable;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.study.yaodh.androidstudy.R;
import com.study.yaodh.androidstudy.databinding.ActivityTtsBinding;

import java.util.HashMap;
import java.util.Locale;

public class TTSActivity extends BaseActivity implements TextToSpeech.OnInitListener {
    public static final int CHECK_CODE = 15;
    ActivityTtsBinding binding;
    private TextToSpeech mTTS;

    @Override
    protected int getLayoutId() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_tts);
        return 0;
    }

    @Override
    protected void initContent() {
        super.initContent();

//        Intent checkIntent = new Intent();
//        checkIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
//        startActivityForResult(checkIntent, CHECK_CODE);
        mTTS = new TextToSpeech(this, this);
    }

    public void speechText(View view) {
        String input = binding.etInput.getText().toString();
        if(TextUtils.isEmpty(input)) {
            Toast.makeText(this, "empty input", Toast.LENGTH_SHORT).show();
            return;
        }
        HashMap<String, String> params = new HashMap<>();
        params.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "stringId");
        mTTS.speak(binding.etInput.getText().toString(), TextToSpeech.QUEUE_FLUSH, params);
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            int result = mTTS.setLanguage(Locale.US);
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
//                binding.speechBtn.setEnabled(false);
                Log.e(TAG, "onInit: Language not supported");
            } else {
//                binding.speechBtn.setEnabled(true);
            }
            mTTS.setOnUtteranceProgressListener(new UtteranceProgressListener() {
                @Override
                public void onStart(String s) {
                    Log.d(TAG, "onStart: " + s);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            binding.ivSpeech.setBackgroundResource(R.drawable.spinner);
                            AnimationDrawable animationDrawable = (AnimationDrawable) binding.ivSpeech.getBackground();
                            animationDrawable.start();

                            binding.speechText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.spinner, 0, 0, 0);
                            AnimationDrawable d = (AnimationDrawable) binding.speechText.getCompoundDrawables()[0];
                            d.start();
                        }
                    });
                }

                @Override
                public void onDone(String s) {
                    Log.d(TAG, "onDone: " + s);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            binding.ivSpeech.setBackgroundResource(R.drawable.ic_pronounce);
                        }
                    });
                }

                @Override
                public void onError(String s) {

                }
            });
        } else {
            Log.e(TAG, "onInit: Could not initialize TextToSpeech");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CHECK_CODE) {
            if (resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS) {
                Log.d(TAG, "onActivityResult: voice data pass");
            } else {
                Intent installIntent = new Intent();
                installIntent.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
                startActivity(installIntent);
                Log.d(TAG, "onActivityResult: install");
            }
        }
    }

    @Override
    protected void onDestroy() {
        if (mTTS != null) {
            mTTS.stop();
            mTTS.shutdown();
        }
        super.onDestroy();
    }
}
