package com.ibrahimmakashi.socialappv2


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.ibrahimmakashi.socialappv2.daos.PostDao


class CreatePostActivity : AppCompatActivity() {

    private lateinit var postDao: PostDao

    private lateinit var postButton : ImageView
    private lateinit var postInput : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_post)



        postButton = findViewById(R.id.postButton)
        postInput = findViewById(R.id.postInput)

        postDao = PostDao()


        postButton.setOnClickListener {
            val input = postInput.text.toString()
            if (input.isNotBlank()){
                postDao.addPost(input)
                finish()
            }
        }

    }

}