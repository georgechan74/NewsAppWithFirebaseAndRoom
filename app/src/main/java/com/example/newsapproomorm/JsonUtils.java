package com.example.newsapproomorm;

import com.example.newsapproomorm.data.NewsItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {
    public static List<NewsItem> parseNews(String jObject) {
        List<NewsItem> newsList = new ArrayList<NewsItem>();

        try {
            JSONObject jsonObject = new JSONObject(jObject);
            JSONArray articles = jsonObject.getJSONArray("articles");

            for (int i = 0; i < articles.length(); i++) {
                JSONObject article = articles.getJSONObject(i);
                newsList.add(new NewsItem(
                        article.getString("author"),
                        article.getString("title"),
                        article.getString("description"),
                        article.getString("url"),
                        article.getString("publishedAt"),
                        article.getString("urlToImage")));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return newsList;
    }
}
