package vn.edu.ueh.thanhdnh.firebase_example;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
  FirebaseFirestore db;
  Button btAdd, btShow;
  EditText etId, etTitle, etContent, etImgCover;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    EdgeToEdge.enable(this);
    setContentView(R.layout.activity_main);
    ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
      Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
      v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
      return insets;
    });

    FirebaseApp.initializeApp(this);
    db = FirebaseFirestore.getInstance();

    btAdd = findViewById(R.id.btAdd);
    btShow = findViewById(R.id.btShow);
    etId = findViewById(R.id.etId);
    etTitle = findViewById(R.id.etTitle);
    etContent = findViewById(R.id.etContent);
    etImgCover = findViewById(R.id.etImgCover);

    btAdd.setOnClickListener(this);
    btShow.setOnClickListener(this);
  }

  @Override
  public void onClick(View view) {
    if (view.getId() == R.id.btAdd) {
      String id = etId.getText().toString().trim();
      String title = etTitle.getText().toString().trim();
      String content = etContent.getText().toString().trim();
      String imgCover = etImgCover.getText().toString().trim();

      if (id.isEmpty()) {
        Toast.makeText(this, "Vui lòng nhập ID", Toast.LENGTH_SHORT).show();
        return;
      }
      if (id.contains("/")) {
        Toast.makeText(this, "ID không được chứa dấu /", Toast.LENGTH_SHORT).show();
        return;
      }
      if (title.isEmpty()) {
        Toast.makeText(this, "Vui lòng nhập tiêu đề", Toast.LENGTH_SHORT).show();
        return;
      }

      // Dùng chính ID nhập vào làm ID của document trên Firestore
      DocumentReference ref = db.collection("articles").document(id);

      // Kiểm tra ID đã tồn tại chưa (tránh ghi đè làm mất lượt view)
      ref.get().addOnSuccessListener(snapshot -> {
        if (snapshot.exists()) {
          Toast.makeText(this, "ID đã tồn tại, hãy nhập ID khác", Toast.LENGTH_SHORT).show();
          return;
        }

        Article newArticle = new Article(id, title, content, imgCover, 0); // view ban đầu = 0
        ref.set(newArticle)
                .addOnSuccessListener(unused -> {
                  Toast.makeText(this, "Thêm bài viết thành công!", Toast.LENGTH_SHORT).show();
                  etId.setText("");
                  etTitle.setText("");
                  etContent.setText("");
                  etImgCover.setText("");
                })
                .addOnFailureListener(e ->
                        Toast.makeText(this, "Lỗi: " + e.getMessage(), Toast.LENGTH_SHORT).show());
      }).addOnFailureListener(e ->
              Toast.makeText(this, "Lỗi: " + e.getMessage(), Toast.LENGTH_SHORT).show());

    } else if (view.getId() == R.id.btShow) {
      startActivity(new Intent(getBaseContext(), ShowDataActivity.class));
    }
  }
}