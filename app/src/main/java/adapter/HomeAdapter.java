package adapter;

import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.olx.AllComment;
import com.example.olx.ForumActivity;
import com.example.olx.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import model.HomeModel;
//import model.HomeModelAnswer;

import static com.example.olx.ForumActivity.currentUser;


public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeViewHolder>
{
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    FirebaseStorage storage = FirebaseStorage.getInstance();//getting instance of the firebase storage


    private Context mcontext;
    private List<HomeModel> entity;
//    private List<HomeModelAnswer> topVoteAns;
    private List<String> questionId;
    private List<String> userid;

    public HomeAdapter(Context mcontext, List<HomeModel> entity, List<String> questionId, List<String> userid) {
        this.mcontext = mcontext;
        this.entity = entity;
//        this.topVoteAns = topVoteAns;
        this.questionId = questionId;
        this.userid = userid;

    }


    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View rootView = LayoutInflater.from(mcontext).inflate(R.layout.feed_layout,parent,false);
        return new HomeViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull final HomeViewHolder holder, int position) {
        final HomeModel homeModel =  entity.get(position);
//        final HomeModelAnswer homeModelAnswer = topVoteAns.get(position);
        final String questionid = questionId.get(position);
        final String user = userid.get(position);



        holder.question.setText(homeModel.getQuestion());
        holder.upvotes.setText(homeModel.getUpvotes()+"");
        if (homeModel.getImageUrl().isEmpty()){
            holder.relatedImage.setVisibility(View.GONE);
        }
        else {
            Picasso.get().load(homeModel.getImageUrl()).placeholder(R.drawable.img).fit().into(holder.relatedImage);
        }

        if (homeModel.getUpvoters().contains(currentUser)){
            holder.upvoteBtn.setBackgroundTintList(ContextCompat.getColorStateList(mcontext, R.color.colorPrimary));
        }
        else{
            holder.upvoteBtn.setBackgroundTintList(ContextCompat.getColorStateList(mcontext, R.color.unlike));

        }



        holder.upvoteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (homeModel.getUpvoters().contains(currentUser)){
                    holder.upvoteBtn.setBackgroundTintList(ContextCompat.getColorStateList(mcontext,R.color.unlike));
                    db.collection("users").document(user).collection("question")
                            .document(questionid).update("upvotes", FieldValue.increment(-1));
                    db.collection("users").document(user).collection("question")
                            .document(questionid).update("upvoters", FieldValue.arrayRemove(currentUser));

                }
                else{
                    holder.upvoteBtn.setBackgroundTintList(ContextCompat.getColorStateList(mcontext, R.color.colorPrimary));
                    db.collection("users").document(user).collection("question")
                            .document(questionid).update("upvotes", FieldValue.increment(1));
                    db.collection("users").document(user).collection("question")
                            .document(questionid).update("upvoters", FieldValue.arrayUnion(currentUser));

                }


            }
        });

//        db.collection("test").document(currentUser).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                if (task.isSuccessful()){
//                    DocumentSnapshot doc = task.getResult();
//                    String usercurrent = doc.getString("name");
//                }
//            }
//        });

        holder.sendComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Editable comm = (Editable)holder.commentBar.getText();
//                Log.d("comment",comm.toString());
                String data = holder.commentBar.getText().toString();
//                Log.d("comment",c);
                if (data.isEmpty()){
                    Toast.makeText(mcontext, "Please Enter comment", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    db.collection("users").document(user).collection("question")
                            .document(questionid).update("answer", FieldValue.arrayUnion(data)).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(mcontext, "Your comment added successfully", Toast.LENGTH_SHORT).show();
                        }
                    });
                    db.collection("users").document(user).collection("question")
                            .document(questionid).update("user", FieldValue.arrayUnion(currentUser));

                }

            }
        });

        holder.comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mcontext, AllComment.class);
                intent.putStringArrayListExtra("answerArray", (ArrayList<String>) homeModel.getAnswer());
                intent.putExtra("question",homeModel.getQuestion());
                mcontext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return entity.size();
    }


    public class HomeViewHolder extends RecyclerView.ViewHolder {
        public TextView username,city,question,answer,upvotes;
        public Button upvoteBtn,comment;
        public EditText commentBar;
        public ImageView relatedImage;
        public ImageButton sendComment;
        public HomeViewHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.name);//taking name
            city = itemView.findViewById(R.id.other_info);//taking city
            question = itemView.findViewById(R.id.question);//taking question
            answer = itemView.findViewById(R.id.answer);//taking answer
            upvotes = itemView.findViewById(R.id.votes);//taking votes
            upvoteBtn = itemView.findViewById(R.id.upVote_btn); //upvote btn
            relatedImage = itemView.findViewById(R.id.required_img);
            sendComment = itemView.findViewById(R.id.sendComment);
            commentBar = itemView.findViewById(R.id.commentBar);
            comment = itemView.findViewById(R.id.comment);
        }
    }

}