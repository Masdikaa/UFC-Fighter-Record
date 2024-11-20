package com.masdika.ufcfighterrecord

import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.masdika.ufcfighterrecord.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val fighterList = ArrayList<Fighter>()

    private val fromBottom: Animation by lazy { AnimationUtils.loadAnimation(this, R.anim.from_bottom_anim) }
    private val toBottom: Animation by lazy { AnimationUtils.loadAnimation(this, R.anim.to_bottom_anim) }

    private var isClicked = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.recyclerViewLayout.setHasFixedSize(true)
        fighterList.addAll(getListFighter())
        showRecyclerFighterList()

        binding.changeLayoutButton.setOnClickListener {
            onAddOnButtonClicked()
        }

        binding.gridLayoutButton.setOnClickListener {
            binding.recyclerViewLayout.layoutManager = GridLayoutManager(this, 2)
            onAddOnButtonClicked()
        }

        binding.listLayoutButton.setOnClickListener {
            binding.recyclerViewLayout.layoutManager = LinearLayoutManager(this)
            onAddOnButtonClicked()
        }

    }

    private fun onAddOnButtonClicked() {
        setVisibility(isClicked)
        setAnimation(isClicked)
        isClicked = !isClicked
    }

    private fun setVisibility(isClicked: Boolean) {
        if (!isClicked) {
            binding.gridLayoutButton.visibility = View.VISIBLE
            binding.listLayoutButton.visibility = View.VISIBLE
        } else {
            binding.listLayoutButton.visibility = View.GONE
            binding.listLayoutButton.visibility = View.GONE
        }
    }

    private fun setAnimation(isClicked: Boolean) {
        if (!isClicked) {
            binding.gridLayoutButton.startAnimation(fromBottom)
            binding.listLayoutButton.startAnimation(fromBottom)
        } else {
            binding.gridLayoutButton.startAnimation(toBottom)
            binding.listLayoutButton.startAnimation(toBottom)
        }
    }

    private fun showRecyclerFighterList() {
        binding.recyclerViewLayout.layoutManager = LinearLayoutManager(this)
        val fighterAdapter = FighterAdapter(fighterList)
        binding.recyclerViewLayout.adapter = fighterAdapter

        fighterAdapter.setOnItemClickCallback(object : FighterAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Fighter) {
                Toast.makeText(this@MainActivity, data.name, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun getListFighter(): ArrayList<Fighter> {
        val fighterImage = resources.getStringArray(R.array.fighter_image)
        val fighterName = resources.getStringArray(R.array.fighter_name)
        val fighterDivision = resources.getStringArray(R.array.fighter_division)
        val fighterAbout = resources.getStringArray(R.array.fighter_about)
        val fighterWin = resources.getIntArray(R.array.fighter_win)
        val fighterLoss = resources.getIntArray(R.array.fighter_loss)
        val fighterDraw = resources.getIntArray(R.array.fighter_draw)
        val fighterStrikePercentage = resources.getIntArray(R.array.fighter_striking)
        val fighterTakeDownPercentage = resources.getIntArray(R.array.fighter_take_down)
        val fighterWinByKnockOut = resources.getIntArray(R.array.knock_out_win)
        val fighterWinBySubmission = resources.getIntArray(R.array.submission_win)
        val listFighter = ArrayList<Fighter>()
        for (i in fighterName.indices) {
            val fighter = Fighter(
                fighterImage[i],
                fighterName[i],
                fighterDivision[i],
                fighterAbout[i],
                fighterWin[i],
                fighterLoss[i],
                fighterDraw[i],
                fighterStrikePercentage[i],
                fighterTakeDownPercentage[i],
                fighterWinByKnockOut[i],
                fighterWinBySubmission[i]
            )
            listFighter.add(fighter)
        }
        return listFighter
    }
}