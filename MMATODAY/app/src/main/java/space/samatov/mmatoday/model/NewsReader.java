package space.samatov.mmatoday.model;


import android.os.AsyncTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class NewsReader {

    public static final String mArticleUrl="http://www.bloodyelbow.com/ufc-news";


    public ArrayList<Article> mNewsFeed=new ArrayList<>();

    public ArrayList<NewsFeedListener> mListeners=new ArrayList<>();

    public void addListener(NewsFeedListener listener){
        mListeners.add(listener);
    }
    public  void getNewsFeed(){
        NewsFeedReader reader=new NewsFeedReader();
        reader.execute();
    }

    private class NewsFeedReader extends AsyncTask<Void,Void,ArrayList<Article>>{


        @Override
        protected ArrayList<Article> doInBackground(Void... voids) {
            ArrayList<Article> articles=new ArrayList<>();
            try {
                Document document= Jsoup.connect(mArticleUrl).get();
                Elements articleList=document.select("div.m-block__body ");
                Elements images=document.select("div.m-block__image noscript>img");

                for(int i=0;i<articleList.size();i++){
                    Element element=articleList.get(i);
                    Article article=new Article();
                    Elements title=element.select("h3");
                    article.setmHeadline(title.text());
                    Elements author=element.select("div.m-block__body__byline>a");
                    article.setmAuthor(author.text());
                    Elements blurb=element.select("p.m-block__body__blurb");
                    article.setmDescription(blurb.text());
                    Elements url=element.select("div.m-block__body-meta a");
                    article.setUrl(url.attr("abs:href"));
                    Elements date=element.select("div.m-block__body__byline");
                    article.setmDate(date.get(0).ownText());
                    article.setmImageUrl(images.get(i).attr("abs:src"));

                    Document articleDetail=Jsoup.connect(article.getUrl()).get();

                    Elements content=articleDetail.select("div.c-entry-content>p");
                    ArrayList<String> paragraphs=new ArrayList<>();
                    for (Element cont:content)
                    paragraphs.add(cont.text());

                    article.setContent(paragraphs);
                    articles.add(article);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }


            return articles;
        }

        @Override
        protected void onPostExecute(ArrayList<Article> articles) {
            mNewsFeed=articles;
            notifyListeners();
            super.onPostExecute(articles);
        }

    }

    public void notifyListeners(){
        for(NewsFeedListener listener:mListeners){
            listener.OnNewsFeedReceived();
        }
    }


    public interface  NewsFeedListener{
        public void OnNewsFeedReceived();
    }
}
