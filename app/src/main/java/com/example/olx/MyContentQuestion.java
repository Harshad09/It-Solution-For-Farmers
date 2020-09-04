package com.example.olx;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import adapter.ContentQuestionAdapter;
import model.ContentQuestionModel;


public class MyContentQuestion extends Fragment {
    private MyContentQuestion.OnFragmentInteractionListener mListener;

    private RecyclerView contentQueRecyclerView;
    private RecyclerView.Adapter mDataAdapter;
    private ContentQuestionModel contentQuestionModel = new ContentQuestionModel();

    private List<ContentQuestionModel> allQue = new ArrayList<>();

    //firebase obj
    // Access a Cloud Firestore instance from your Activity
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_content_question, container, false);

        contentQueRecyclerView = view.findViewById(R.id.my_content_que_recycler);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        contentQueRecyclerView.setLayoutManager(layoutManager);
        mDataAdapter = new ContentQuestionAdapter(allQue, getActivity());

        contentQueRecyclerView.setAdapter(mDataAdapter);

        //Fetching all the questions of the user itself
        DocumentReference userQuestion = db.collection("Users").document(ForumActivity.currentUser);
        userQuestion.collection("question").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (task.isSuccessful()){
                    for (QueryDocumentSnapshot document : task.getResult()){
                        contentQuestionModel = document.toObject(ContentQuestionModel.class);
                        allQue.add(contentQuestionModel);
                    }
                    mDataAdapter.notifyDataSetChanged();
                }
            }
        });


        return view;
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
