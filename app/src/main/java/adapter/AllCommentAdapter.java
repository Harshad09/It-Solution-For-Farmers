package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.olx.R;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

import java.util.List;



public class AllCommentAdapter extends RecyclerView.Adapter<AllCommentAdapter.AllCommentAdapterViewHolder>{

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    FirebaseStorage storage = FirebaseStorage.getInstance();//getting instance of the firebase storage
    private Context mcontext;
    private List<String> answerArray;

    public AllCommentAdapter(Context mcontext, List<String> answer) {
        this.mcontext = mcontext;
        this.answerArray = answer;
    }


    @NonNull
    @Override
    public AllCommentAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View rootView = LayoutInflater.from(mcontext).inflate(R.layout.comment_feed,parent,false);
        return new AllCommentAdapterViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull AllCommentAdapterViewHolder holder, int position) {
        if (answerArray.isEmpty()){
            holder.allComment.setText("No Comments Yet");
        }
        else {
            String answer = answerArray.get(position);
            holder.allComment.setText(answer);

        }


    }

    @Override
    public int getItemCount() {
        return answerArray.size();
    }

    public class AllCommentAdapterViewHolder extends RecyclerView.ViewHolder {
        public TextView allComment ;
        public AllCommentAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            allComment = itemView.findViewById(R.id.allComment);
        }
    }
}
