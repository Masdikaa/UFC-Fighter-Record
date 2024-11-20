package com.masdika.ufcfighterrecord.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.masdika.ufcfighterrecord.Fighter
import com.masdika.ufcfighterrecord.FighterAdapter
import com.masdika.ufcfighterrecord.R
import com.masdika.ufcfighterrecord.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMainBinding
    private val fighterList = ArrayList<Fighter>()
    private lateinit var fighterAdapter: FighterAdapter

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

        fighterAdapter = FighterAdapter(fighterList, isGridLayout = false)
        fighterAdapter.setOnItemClickCallback(object : FighterAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Fighter) {
                val intentToDetail = Intent(this@MainActivity, DetailActivity::class.java)
                intentToDetail.putExtra("DATA", data)
                startActivity(intentToDetail)
            }
        })

        binding.recyclerViewLayout.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            setHasFixedSize(true)
            adapter = fighterAdapter
        }

        binding.recyclerViewLayout.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                when (isClicked) {
                    false -> {
                        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                            binding.changeLayoutButton.visibility = View.VISIBLE
                            binding.changeLayoutButton.startAnimation(AnimationUtils.loadAnimation(this@MainActivity, R.anim.fade_in))
                        } else {
                            binding.changeLayoutButton.visibility = View.GONE
                            binding.changeLayoutButton.startAnimation(AnimationUtils.loadAnimation(this@MainActivity, R.anim.fade_out))
                        }
                    }

                    true -> {
                        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                            binding.gridLayoutButton.visibility = View.VISIBLE
                            binding.listLayoutButton.visibility = View.VISIBLE
                            binding.changeLayoutButton.visibility = View.VISIBLE
                            binding.gridLayoutButton.startAnimation(AnimationUtils.loadAnimation(this@MainActivity, R.anim.from_bottom_anim))
                            binding.listLayoutButton.startAnimation(AnimationUtils.loadAnimation(this@MainActivity, R.anim.from_bottom_anim))
                            binding.changeLayoutButton.startAnimation(AnimationUtils.loadAnimation(this@MainActivity, R.anim.fade_in))
                        } else {
                            // Scrolled
                            binding.gridLayoutButton.visibility = View.GONE
                            binding.listLayoutButton.visibility = View.GONE
                            binding.changeLayoutButton.visibility = View.GONE
                            binding.gridLayoutButton.startAnimation(AnimationUtils.loadAnimation(this@MainActivity, R.anim.to_bottom_anim))
                            binding.listLayoutButton.startAnimation(AnimationUtils.loadAnimation(this@MainActivity, R.anim.to_bottom_anim))
                            binding.changeLayoutButton.startAnimation(AnimationUtils.loadAnimation(this@MainActivity, R.anim.fade_out))
                        }
                    }
                }
            }
        })

        fighterList.addAll(getListFighter())

        binding.changeLayoutButton.setOnClickListener(this)
        binding.gridLayoutButton.setOnClickListener(this)
        binding.listLayoutButton.setOnClickListener(this)

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
        val fighterTitle = resources.getStringArray(R.array.fighter_title)
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
                fighterWinBySubmission[i],
                fighterTitle[i]
            )
            listFighter.add(fighter)
        }
        return listFighter
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.change_layout_button -> {
                onAddOnButtonClicked()
            }

            R.id.grid_layout_button -> {
                binding.recyclerViewLayout.layoutManager = GridLayoutManager(this, 2)
                fighterAdapter.setLayoutMode(true)
                onAddOnButtonClicked()
            }

            R.id.list_layout_button -> {
                binding.recyclerViewLayout.layoutManager = LinearLayoutManager(this)
                fighterAdapter.setLayoutMode(false)
                onAddOnButtonClicked()
            }
        }
    }
}