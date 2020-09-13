package com.example.madlevel2task2

import android.content.ClipData
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.madlevel2task2.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val questions = arrayListOf<Question>()
    private val questionAdapter = QuestionAdapter(questions)

    private lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
    }

    private fun initViews() {
        binding.rvQuestions.layoutManager = LinearLayoutManager(this@MainActivity,RecyclerView.VERTICAL,false)
        binding.rvQuestions.adapter = questionAdapter
        questions.add(Question("false",false))
        questions.add(Question("true",true))
        questions.add(Question("true",true))
        questions.add(Question("false",false))
        questionAdapter.notifyDataSetChanged()
        CreateItemTouchHelper().attachToRecyclerView(rvQuestions)
        //binding.rvQuestions.addItemDecoration(DividerItemDecoration(this@MainActivity, DividerItemDecoration.VERTICAL))
    }

    private fun CreateItemTouchHelper():ItemTouchHelper{
        val callback = object:ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                if(direction == ItemTouchHelper.RIGHT){
                if(questions[position].answer){
                questions.removeAt(position)
                }else{
                    Snackbar.make(rvQuestions,"wrong answer(right)",Snackbar.LENGTH_SHORT).show()
                }}
                if(direction == ItemTouchHelper.LEFT){
                    if(!questions[position].answer){
                        questions.removeAt(position)
                    } else{
                        Snackbar.make(rvQuestions,"wrong answer(left)",Snackbar.LENGTH_SHORT).show()
                    }
                }
                questionAdapter.notifyDataSetChanged()

            }

        }

        return ItemTouchHelper(callback)
    }
}