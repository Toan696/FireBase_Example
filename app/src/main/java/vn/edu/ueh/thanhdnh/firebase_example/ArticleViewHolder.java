package vn.edu.ueh.thanhdnh.firebase_example;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

public class ArticleViewHolder extends RecyclerView.ViewHolder {
    private TextView txtTitle, txtContent, txtView;
    private ImageView imgCover;

    public ArticleViewHolder(@NonNull View itemView, ArticleViewAdapter adapter) {
        super(itemView);

        txtTitle = itemView.findViewById(R.id.txt_title);
        txtContent = itemView.findViewById(R.id.txt_content);
        txtView = itemView.findViewById(R.id.txt_view);
        imgCover = itemView.findViewById(R.id.img_cover);

        // Click vào 1 bài viết
        itemView.setOnClickListener(v -> {
            int position = getAdapterPosition();
            if (position == RecyclerView.NO_POSITION || adapter == null) return;

            Article clicked = adapter.getArticles().get(position);
            Context context = v.getContext();

            // 1. Tăng view +1 trên Firestore
            if (clicked.getId() != null) {
                FirebaseFirestore.getInstance()
                        .collection("articles")
                        .document(clicked.getId())
                        .update("view", FieldValue.increment(1));
            }

            // 2. Sang màn hình chi tiết (cộng view cục bộ để hiển thị số mới ngay)
            clicked.setView(clicked.getView() + 1);
            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra("article_item", clicked);
            context.startActivity(intent);
        });
    }

    public TextView getTxtTitle() { return txtTitle; }
    public TextView getTxtContent() { return txtContent; }
    public TextView getTxtView() { return txtView; }
    public ImageView getImgCover() { return imgCover; }
}