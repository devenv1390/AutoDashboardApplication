package com.qky.autodashboardapplication.util;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.util.Log;

import java.util.HashMap;
import java.util.Locale;

public class TTSUtil {
    private final TextToSpeech textToSpeech;
    private boolean initialized = false;
    private float defaultSpeechRate = 1.0f;  // 默认语速
    private float defaultPitch = 1.0f;       // 默认音调
    private Locale defaultLocale = Locale.CHINESE; // 默认语言

    public interface TTSListener {
        void onInitSuccess();

        void onInitFailure();

        void onSpeechStart();

        void onSpeechDone();

        void onSpeechError(String errorMessage);
    }

    public TTSUtil(Context context, final TTSListener listener) {
        textToSpeech = new TextToSpeech(context, status -> {
            if (status == TextToSpeech.SUCCESS) {
                initialized = true;
                if (listener != null) {
                    listener.onInitSuccess();
                }
            } else {
                if (listener != null) {
                    listener.onInitFailure();
                }
            }
        });
    }

    // 设置默认语速
    public void setDefaultSpeechRate(float speechRate) {
        defaultSpeechRate = speechRate;
    }

    // 设置默认音调
    public void setDefaultPitch(float pitch) {
        defaultPitch = pitch;
    }

    // 设置默认语言
    public void setDefaultLocale(Locale locale) {
        defaultLocale = locale;
    }

    // 文本转语音
    public void speak(String text) {
        // 唯一标识符
        String utteranceId = "utteranceId";
        speak(text, utteranceId);
    }

    public void speak(String text, String utteranceId) {
        speak(text, utteranceId, defaultLocale, defaultSpeechRate, defaultPitch);
    }

    public void speak(String text, String utteranceId, Locale locale, float speechRate, float pitch) {
        if (initialized) {
            textToSpeech.setLanguage(locale);
            textToSpeech.setSpeechRate(speechRate);
            textToSpeech.setPitch(pitch);

            HashMap<String, String> params = new HashMap<>();
            params.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, utteranceId);

            textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, params);
            textToSpeech.setOnUtteranceProgressListener(new UtteranceProgressListener() {
                @Override
                public void onStart(String utteranceId) {
                    Log.d("TTSUtil", "TTS TTSUtil onStart: " + utteranceId);
                }

                @Override
                public void onDone(String utteranceId) {
                    Log.d("TTSUtil", "TTS TTSUtil onDone: " + utteranceId);
                }

                @Override
                public void onError(String utteranceId) {
                    Log.d("TTSUtil", "TTS TTSUtil onError: " + utteranceId);
                }
            });
        }
    }

    // 释放资源
    public void release() {
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
    }
}