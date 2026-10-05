package vn.edu.ueh.thanhdnh.firebase_example;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class ArticleViewAdapter extends RecyclerView.Adapter<ArticleViewHolder> {
    private LayoutInflater mInflater;
    private List<Article> articles;

    public ArticleViewAdapter(Context context, List<Article> articles) {
        this.mInflater = LayoutInflater.from(context);
        this.articles = articles;
    }

    public void update(List<Article> articles) {
        this.articles = articles;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ArticleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View customView = mInflater.inflate(R.layout.article_item, parent, false);
        return new ArticleViewHolder(customView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleViewHolder holder, int position) {
        Article article = articles.get(position);

        holder.getTxtTitle().setText(article.getTitle());
        holder.getTxtContent().setText(article.getContent());
        holder.getTxtView().setText("Views: " + article.getView());

        String imageUrl = article.getImg_cover();
        Picasso.get()
                .load(imageUrl == null || imageUrl.trim().isEmpty() ? null : imageUrl.trim())
                .placeholder(android.R.drawable.ic_menu_gallery)
                .error(android.R.drawable.ic_menu_gallery)
                .resize(300, 300)
                .centerCrop()
                .into(holder.getImgCover());
    }

    @Override
    public int getItemCount() {
        return articles != null ? articles.size() : 0;
    }

    public List<Article> getArticles() {
        return articles;
    }
}