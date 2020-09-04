package com.example.olx;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
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

import adapter.ContentAnswerAdapter;
import model.ContentAnswerModel;

public class MyContentAnswer extends Fragment {

    private RecyclerView contentAnsRecyclerView;
    private RecyclerView.Adapter mDataAdapter;
    private ContentAnswerModel contentAnswerModel = new ContentAnswerModel();

    private List<ContentAnswerModel> allAns = new ArrayList<>();

    private OnFragmentInteractionListener mListener;

    //firebase obj
    // Access a Cloud Firestore instance from your Activity
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_my_content_answer, container, false);

        contentAnsRecyclerView = view.findViewById(R.id.my_content_ans_recycler);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        contentAnsRecyclerView.setLayoutManager(layoutManager);
        mDataAdapter = new ContentAnswerAdapter(getContext(), allAns);
        contentAnsRecyclerView.setAdapter(mDataAdapter);

        //Fetching all the answes of the user itself
        DocumentReference userAnswer = db.collection("Users").document(ForumActivity.currentUser);
        userAnswer.collection("answer").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (task.isSuccessful()){
                    for (QueryDocumentSnapshot document : task.getResult()){
                        contentAnswerModel = document.toObject(ContentAnswerModel.class);

                        allAns.add(contentAnswerModel);
                        Log.d("contentans", document.getId() + " => " + document.getData());
                    }
//                    Log.d("size", (allAns.size()+""));
//                    Log.d("second",allAns.get(1).getAnswer());
                    mDataAdapter.notifyDataSetChanged();
                }

            }
        });


        // Inflate the layout for this fragment
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
