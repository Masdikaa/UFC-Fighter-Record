package com.masdika.ufcfighterrecord.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.masdika.ufcfighterrecord.R
import com.masdika.ufcfighterrecord.databinding.ActivityAboutBinding

class AboutActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityAboutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityAboutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initializeUI()
        binding.instagramLayout.setOnClickListener(this)
        binding.linkedinLayout.setOnClickListener(this)
        binding.githubLayout.setOnClickListener(this)

    }

    private fun initializeUI() {
        Glide.with(this).load(resources.getString(R.string.my_avatar)).into(binding.myAvatar)
        binding.aboutMeTextview.text = resources.getString(R.string.about_me_text)
        binding.emailTextview.text = resources.getString(R.string.email_text)
        binding.instagramTextview.text = resources.getString(R.string.instagram_text)
        binding.linkedinTextview.text = resources.getString(R.string.linkedin_text)
        binding.githubTextview.text = resources.getString(R.string.github_text)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.instagram_layout -> {
                val url = resources.getString(R.string.instagram_link)
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(url)
                startActivity(intent)
            }

            R.id.linkedin_layout -> {
                val url = resources.getString(R.string.linkedin_link)
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(url)
                startActivity(intent)
            }

            R.id.github_layout -> {
                val url = resources.getString(R.string.github_link)
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(url)
                startActivity(intent)
            }
        }
    }
}