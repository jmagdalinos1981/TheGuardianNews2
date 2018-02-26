package com.johnmagdalinos.android.newsworld.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.johnmagdalinos.android.newsworld.R;

/**
 * Displays a url in a WebView
 */

public class WebViewFragment extends Fragment {
    /** Member variables */
    private WebViewCallback mCallback;

    /** Keys for reading and saving the Section Title */
    private static final String KEY_URL = "url";

    public interface WebViewCallback {
        public void onWebViewClosed();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mCallback = (WebViewCallback) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement " +
                    "WebViewCallback");
        }
    }

    /** Class constructor */
    public static WebViewFragment newInstance(String url) {
        WebViewFragment fragment = new WebViewFragment();
        Bundle args = new Bundle();
        args.putString(KEY_URL, url);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_webview, container, false);

        String url = getArguments().getString(KEY_URL);

        WebView webView = rootView.findViewById(R.id.webview);
        webView.loadUrl(url);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mCallback.onWebViewClosed();
    }
}
