package com.blueradix.android.jetpackpersistence.monster;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.blueradix.android.jetpackpersistence.databinding.MonsterFragmentBinding;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MonsterFragment extends Fragment {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private MonsterViewModel mViewModel;
    private MonsterFragmentBinding binding;

    public static MonsterFragment newInstance() {
        return new MonsterFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = MonsterFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(MonsterViewModel.class);
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
                //timestamp is used to get the current date of the system, so we can differentiate all Demons inserted,
                //every time we press the add 1 hardcoded button
                Timestamp timestamp = new Timestamp(new Date().getTime());
                mViewModel.insert(new Monster("Demon " + DATE_FORMAT.format(timestamp), "Test", "", 2,0,0));

            }
        });

        binding.findMonsterByIdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(binding.monsterIdEditText.getText())) {
                    Toast.makeText(getContext(), "Please input an ID", Toast.LENGTH_SHORT).show();
                } else {
                    String id = binding.monsterIdEditText.getText().toString();
                    Monster monster = mViewModel.findById(Integer.parseInt(id));
                    if (monster != null) {
                        binding.selectedMonsterTextView.setText("Monster id: " + monster.getId() + ", name: " + monster.getName() + ", description: " + monster.getDescription());
                        Log.i("XYZ", "Monster in MonsterFragment, " + monster);

                    } else {
                        Toast.makeText(getContext(), "Monster ID not registered", Toast.LENGTH_LONG).show();
                        Log.i("XYZ", "monster is null in MonsterFragment");
                    }
                }
            }
        });

        binding.deleteMonsterByIdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(binding.monsterIdEditText.getText())) {
                    Toast.makeText(getContext(), "Please input an ID", Toast.LENGTH_SHORT).show();
                } else {
                    String id = binding.monsterIdEditText.getText().toString();
                    Monster monster = mViewModel.findById(Integer.parseInt(id));
                    if (monster != null) {
                        mViewModel.delete(monster);
                    }
                }
            }
        });
    }
}