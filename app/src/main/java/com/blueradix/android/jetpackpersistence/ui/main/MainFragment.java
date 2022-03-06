package com.blueradix.android.jetpackpersistence.ui.main;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.blueradix.android.jetpackpersistence.R;
import com.blueradix.android.jetpackpersistence.databinding.MainFragmentBinding;
import com.blueradix.android.jetpackpersistence.storage.monster.entity.Monster;

import java.util.List;

public class MainFragment extends Fragment {

    private MainViewModel mViewModel;
    private MainFragmentBinding binding;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = MainFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        // TODO: Use the ViewModel

        final Observer<List<Monster>> allMonstersObserver = new Observer<List<Monster>>() {
            @Override
            public void onChanged(List<Monster> monsters) {
                binding.totalOfMonstersTextView.setText("Total monsters registered: " + monsters.size());
            }
        };
        mViewModel.getAllMonsters().observe(getViewLifecycleOwner(), allMonstersObserver);

        binding.testAddMonsterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewModel.insert(new Monster("Demon", "Test", "", 2,0,0));

            }
        });

        binding.findMonsterByIdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(binding.monsterIdEditText.getText())) {
                    Toast.makeText(getContext(), "Please input an ID", Toast.LENGTH_SHORT).show();
                } else {
                    String id = binding.monsterIdEditText.getText().toString();
                    Monster monster = mViewModel.findById(Integer.valueOf(id));
                    if (monster != null) {
                        binding.selectedMonsterTextView.setText("Monster id: " + monster.getId() + ", name: " + monster.getName() + ", description: " + monster.getDescription());
                        Log.i("XYZ", "Monster in MainFragment, " + monster.toString());

                    } else {
                        Toast.makeText(getContext(), "Monster ID not registered", Toast.LENGTH_LONG).show();
                        Log.i("XYZ", "monster is null in MainFragment");
                    }
                }
            }
        });

        binding.deleteMonsterById.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(binding.monsterIdEditText.getText())) {
                    Toast.makeText(getContext(), "Please input an ID", Toast.LENGTH_SHORT).show();
                } else {
                    String id = binding.monsterIdEditText.getText().toString();
                    Monster monster = mViewModel.findById(Integer.valueOf(id));
                    if (monster != null) {
                        mViewModel.delete(monster);
                    }
                }
            }
        });
    }
}