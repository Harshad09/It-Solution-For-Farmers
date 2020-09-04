package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.olx.R;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;

import java.util.List;

import model.SearchedAnswerModel;

import static com.example.olx.ForumActivity.currentUser;

public class SearchedAnswerAdapter extends RecyclerView.Adapter<SearchedAnswerAdapter.SearchedAnswerAdapterViewHolder> {

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    FirebaseStorage storage = FirebaseStorage.getInstance();//getting instance of the firebase storage


    private Context mcontext;
    private List<SearchedAnswerModel> entity;
    private List<String> answerId;
    private List<String> userid;

    public SearchedAnswerAdapter(Context mcontext, List<SearchedAnswerModel> entity,List<String> answerId, List<String> userid) {
        this.mcontext = mcontext;
        this.entity = entity;
        this.answerId = answerId;
        this.userid = userid;

    }

    @NonNull
    @Override
    public SearchedAnswerAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View rootView = LayoutInflater.from(mcontext).inflate(R.layout.searched_answer_feed,parent,false);
        return new SearchedAnswerAdapterViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull final SearchedAnswerAdapterViewHolder holder, int position) {

        final SearchedAnswerModel searchedAnswerModel = entity.get(position);
        final String answerid = answerId.get(position);
        final String user = userid.get(position);


        holder.username.setText("placeholder");
        holder.city.setText("city");
        holder.answer.setText(searchedAnswerModel.getAnswer());
        holder.upvotes.setText(searchedAnswerModel.getUpvotes()+"");
        Picasso.get().load(searchedAnswerModel.getImageurl()).placeholder(R.drawable.img).fit().into(holder.relatedImage);

        if (searchedAnswerModel.getUpvoters().contains(currentUser)){
            holder.upvoteBtn.setBackgroundTintList(ContextCompat.getColorStateList(mcontext, R.color.colorPrimary));
        }
        else{
            holder.upvoteBtn.setBackgroundTintList(ContextCompat.getColorStateList(mcontext, R.color.unlike));

        }



        holder.upvoteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (searchedAnswerModel.getUpvoters().contains(currentUser)){
                    holder.upvoteBtn.setBackgroundTintList(ContextCompat.getColorStateList(mcontext,R.color.unlike));
                    db.collection("Users").document(user).collection("answer")
                            .document(answerid).update("upvotes", FieldValue.increment(-1));
                    db.collection("Users").document(user).collection("answer")
                            .document(answerid).update("upvoters", FieldValue.arrayRemove(currentUser));

                }
                else{
                    holder.upvoteBtn.setBackgroundTintList(ContextCompat.getColorStateList(mcontext, R.color.colorPrimary));
                    db.collection("Users").document(user).collection("answer")
                            .document(answerid).update("upvotes", FieldValue.increment(1));
                    db.collection("Users").document(user).collection("answer")
                            .document(answerid).update("upvoters", FieldValue.arrayUnion(currentUser));

                }


            }
        });


    }

    @Override
    public int getItemCount() {
        return entity.size();
    }

    public class SearchedAnswerAdapterViewHolder extends RecyclerView.ViewHolder {
        public TextView username,city,answer,upvotes;
        public Button upvoteBtn;
        public ImageView relatedImage;
        public SearchedAnswerAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.name);//taking name
            city = itemView.findViewById(R.id.other_info);//taking city
            answer = itemView.findViewById(R.id.answer);//taking answer
            upvotes = itemView.findViewById(R.id.votes);//taking votes
            upvoteBtn = itemView.findViewById(R.id.upVote_btn); //upvote btn
            relatedImage = itemView.findViewById(R.id.required_img);
        }
    }
}
